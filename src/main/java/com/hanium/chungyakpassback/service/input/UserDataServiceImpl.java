package com.hanium.chungyakpassback.service.input;

import com.hanium.chungyakpassback.dto.input.*;
import com.hanium.chungyakpassback.entity.standard.AddressLevel1;
import com.hanium.chungyakpassback.entity.standard.AddressLevel2;
import com.hanium.chungyakpassback.enumtype.ErrorCode;
import com.hanium.chungyakpassback.enumtype.Relation;
import com.hanium.chungyakpassback.enumtype.Yn;
import com.hanium.chungyakpassback.entity.input.*;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.input.*;
import com.hanium.chungyakpassback.repository.standard.AddressLevel1Repository;
import com.hanium.chungyakpassback.repository.standard.AddressLevel2Repository;
import com.hanium.chungyakpassback.repository.standard.RelationRepository;
import com.hanium.chungyakpassback.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserDataServiceImpl implements UserDataService{
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;
    private final HouseMemberRepository houseMemberRepository;
    private final HouseMemberRelationRepository houseMemberRelationRepository;
    private final UserBankbookRepository userBankbookRepository;
    private final HouseMemberPropertyRepository houseMemberPropertyRepository;
    private final HouseMemberChungyakRepository houseMemberChungyakRepository;
    private final HouseMemberChungyakRestrictionRepository houseMemberChungyakRestrictionRepository;

    private final AddressLevel1Repository addressLevel1Repository;
    private final AddressLevel2Repository addressLevel2Repository;
    private final RelationRepository relationRepository;


    @Transactional(rollbackFor = Exception.class)
    public UserBankbookResponseDto userBankbook(UserBankbookDto userBankbookDto){
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();

        if (userBankbookRepository.findByUser(user).orElse(null) != null)
            throw new CustomException(ErrorCode.DUPLICATE_BANKBOOK);

        return new UserBankbookResponseDto(userBankbookRepository.save(userBankbookDto.toEntity(user)));
    }

    @Transactional(rollbackFor = Exception.class)
    public UserBankbookResponseDto updateUserBankbook(Long id, UserBankbookDto userBankbookDto) {
        UserBankbook userBankbook = userBankbookRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BANKBOOK));

        return new UserBankbookResponseDto(userBankbookRepository.save(userBankbook.updateUserBankbook(userBankbookDto)));
    }

    @Transactional(rollbackFor = Exception.class)
    public HttpStatus deleteUserBankbook(Long id){
        UserBankbook userBankbook = userBankbookRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BANKBOOK));

        userBankbookRepository.delete(userBankbook);
        return HttpStatus.OK;
    }


    @Transactional(rollbackFor = Exception.class)
    public HouseResponseDto house(HouseDto houseDto){
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();

        if ((houseDto.getSpouseHouseYn().equals(Yn.y) && user.getSpouseHouse() != null) || (houseDto.getSpouseHouseYn().equals(Yn.n) && user.getHouse() != null))
            throw new CustomException(ErrorCode.DUPLICATE_HOUSE);

        if (houseDto.getSpouseHouseYn().equals(Yn.y) && user.getSpouseHouseMember() != null) //배우자가 존재하는데 배우자분리세대를 생성하려고 한다면
            throw new CustomException(ErrorCode.BAD_REQUEST_SPOUSE_AND_SPOUSE_HOUSE);

        AddressLevel1 addressLevel1 = addressLevel1Repository.findByAddressLevel1(houseDto.getAddressLevel1()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS_LEVEL1));
        AddressLevel2 addressLevel2 = addressLevel2Repository.findByAddressLevel1AndAddressLevel2(addressLevel1, houseDto.getAddressLevel2()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS_LEVEL2));
        House house = houseDto.toEntity(addressLevel1, addressLevel2);
        houseRepository.save(house);

        if (houseDto.getSpouseHouseYn().equals(Yn.y))
            user.setSpouseHouse(house);
        else user.setHouse(house);
        userRepository.save(user);

        return new HouseResponseDto(house);
    }

    @Transactional(rollbackFor = Exception.class)
    public HouseResponseDto updateHouse(Long id, HouseUpdateDto houseUpdateDto){
        House house = houseRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE));

        AddressLevel1 addressLevel1 = addressLevel1Repository.findByAddressLevel1(houseUpdateDto.getAddressLevel1()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS_LEVEL1));
        AddressLevel2 addressLevel2 = addressLevel2Repository.findByAddressLevel1AndAddressLevel2(addressLevel1, houseUpdateDto.getAddressLevel2()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS_LEVEL2));
        house = house.update(addressLevel1, addressLevel2, houseUpdateDto);
        return new HouseResponseDto(houseRepository.save(house));
    }

    @Transactional(rollbackFor = Exception.class)
    public HttpStatus deleteHouse(Long id){
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        House house = houseRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE));

        for (HouseMember houseMember : houseMemberRepository.findAllByHouse(house)){
            houseMemberRelationRepository.delete(houseMemberRelationRepository.findByOpponent(houseMember).get());
            houseMemberPropertyRepository.deleteAllByHouseMember(houseMember);
            for (HouseMemberChungyak houseMemberChungyak : houseMemberChungyakRepository.findAllByHouseMember(houseMember)){
                houseMemberChungyakRestrictionRepository.deleteByHouseMemberChungyak(houseMemberChungyak);
                houseMemberChungyakRepository.delete(houseMemberChungyak);}
            if (houseMember.equals(user.getHouseMember()))
                user.setHouseMember(null);
            else if(houseMember.equals(user.getSpouseHouseMember()))
                user.setSpouseHouseMember(null);
            houseMemberRepository.delete(houseMember);
        }
        if (house.equals(user.getHouse()))
            user.setHouse(null);
        else if (house.equals(user.getSpouseHouse()))
            user.setSpouseHouse(null);
        userRepository.save(user);

        houseRepository.delete(house);

        return HttpStatus.OK;
    }



    @Transactional(rollbackFor = Exception.class)
    public HouseMemberResponseDto houseMember(HouseMemberDto houseMemberDto){
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        House house = houseRepository.findById(houseMemberDto.getHouseId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE));

        com.hanium.chungyakpassback.entity.standard.Relation relation = relationRepository.findByRelation(houseMemberDto.getRelation()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RELATION));
        if (relation.getOnlyOneYn().equals(Yn.y) && houseMemberRelationRepository.findByUserAndRelation(user, relation).isPresent())
            throw new CustomException(ErrorCode.DUPLICATE_RELATION);

        if (relation.getRelation().equals(Relation.본인) && house.equals(user.getSpouseHouse())) //배우자분리세대에 본인을 등록하려고 한다면
            throw new CustomException(ErrorCode.BAD_REQUEST_USER_AND_USER_HOUSE);

        if (relation.getRelation().equals(Relation.배우자) && house.equals(user.getHouse()) && user.getSpouseHouse() != null) //본인세대에 배우자를 등록하는데 배우자분리세대가 null 이 아니라면
            throw new CustomException(ErrorCode.BAD_REQUEST_SPOUSE_AND_SPOUSE_HOUSE);


        HouseMember houseMember = houseMemberDto.toEntity(house);
        houseMemberRepository.save(houseMember);

        HouseMemberRelation houseMemberRelation = HouseMemberRelation.builder()
                .user(user).opponent(houseMember).relation(relation)
                .build();
        houseMemberRelationRepository.save(houseMemberRelation);

        if (relation.getRelation().equals(Relation.본인))
            user.setHouseMember(houseMember);
        else if (relation.getRelation().equals(Relation.배우자))
            user.setSpouseHouseMember(houseMember);
        userRepository.save(user);

        return new HouseMemberResponseDto(houseMember, houseMemberRelation);
    }

    @Transactional(rollbackFor = Exception.class)
    public HouseMemberResponseDto updateHouseMember(Long id, HouseMemberUpdateDto houseMemberupdateDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = houseMemberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER));

        HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
        com.hanium.chungyakpassback.entity.standard.Relation presentRelation = houseMemberRelation.getRelation();
        com.hanium.chungyakpassback.entity.standard.Relation changedRelation = relationRepository.findByRelation(houseMemberupdateDto.getRelation()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RELATION));
        if (!presentRelation.equals(changedRelation)) {
            if (changedRelation.getOnlyOneYn().equals(Yn.y) && houseMemberRelationRepository.findByUserAndRelation(user, changedRelation).isPresent())
                throw new CustomException(ErrorCode.DUPLICATE_RELATION);

            houseMemberRelation.setRelation(changedRelation);
            houseMemberRelationRepository.save(houseMemberRelation);

            if (presentRelation.getRelation().equals(Relation.본인))
                user.setHouseMember(null);
            else if (presentRelation.getRelation().equals(Relation.배우자))
                user.setSpouseHouseMember(null);
            if (changedRelation.getRelation().equals(Relation.본인)){ //배우자분리세대에 본인을 등록하려고 한다면
                if (houseMember.getHouse().equals(user.getSpouseHouse()))
                    throw new CustomException(ErrorCode.BAD_REQUEST_USER_AND_USER_HOUSE);
                user.setHouseMember(houseMember);}
            else if (changedRelation.getRelation().equals(Relation.배우자)){ //본인세대에 배우자를 등록하는데 배우자분리세대가 null 이 아니라면
                if (houseMember.getHouse().equals(user.getHouse()) && user.getSpouseHouse() != null)
                    throw new CustomException(ErrorCode.BAD_REQUEST_SPOUSE_AND_SPOUSE_HOUSE);
                user.setSpouseHouseMember(houseMember);}
            userRepository.save(user);
        }

        houseMember.updateHouseMember(houseMemberupdateDto);
        houseMemberRepository.save(houseMember);

        return new HouseMemberResponseDto(houseMember, houseMemberRelation);
    }

    @Transactional(rollbackFor = Exception.class)
    public HttpStatus deleteHouseMember(Long id){
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        House house = user.getHouse();
        HouseMember houseMember = houseMemberRepository.findById(id).orElseThrow(() -> new CustomException((ErrorCode.NOT_FOUND_HOUSE_MEMBER)));

        houseMemberRelationRepository.delete(houseMemberRelationRepository.findByOpponent(houseMember).get());
        houseMemberPropertyRepository.deleteAllByHouseMember(houseMember);
        for (HouseMemberChungyak houseMemberChungyak : houseMemberChungyakRepository.findAllByHouseMember(houseMember)){
            houseMemberChungyakRestrictionRepository.deleteByHouseMemberChungyak(houseMemberChungyak);
            houseMemberChungyakRepository.delete(houseMemberChungyak);
        }
        if (house.getHouseHolder().equals(houseMember)){
            house.setHouseHolder(null);
            houseRepository.save(house);}
        if (houseMember.equals(user.getHouseMember()))
            user.setHouseMember(null);
        else if(houseMember.equals(user.getSpouseHouseMember()))
            user.setSpouseHouseMember(null);
        userRepository.save(user);
        houseMemberRepository.delete(houseMember);

        return HttpStatus.OK;
    }


    @Transactional(rollbackFor = Exception.class)
    public HouseHolderDto houseHolder(Long id, HouseHolderDto houseHolderDto){
        House house = houseRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE));

        HouseMember houseMember = houseHolderDto.getHouseMemberId() == null ?
                null : houseMemberRepository.findById(houseHolderDto.getHouseMemberId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER));

        house.setHouseHolder(houseMember);
        houseRepository.save(house);

        return houseHolderDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<HouseMemberPropertyResponseDto> houseMemberProperty(HouseMemberPropertyDto houseMemberPropertyDto){
        ArrayList<HouseMemberPropertyResponseDto> houseMemberPropertyResponseDtos = new ArrayList<>();
        for (HouseMemberPropertyDto houseMemberPropertyDtoSingle : houseMemberPropertyDto.getHouseMemberPropertyDtos()) {
            HouseMember houseMember = houseMemberRepository.findById(houseMemberPropertyDtoSingle.getHouseMemberId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER));
            HouseMemberProperty houseMemberProperty = houseMemberPropertyDtoSingle.toEntity(houseMember);
            houseMemberPropertyRepository.save(houseMemberProperty);
            houseMemberPropertyResponseDtos.add(new HouseMemberPropertyResponseDto(houseMemberProperty));
        }
        return houseMemberPropertyResponseDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public HouseMemberPropertyResponseDto updateHouseMemberProperty(Long id, HouseMemberPropertyUpdateDto houseMemberPropertyUpdateDto){
        HouseMemberProperty houseMemberProperty = houseMemberPropertyRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_PROPERTY));
        HouseMember houseMember = houseMemberRepository.findById(houseMemberPropertyUpdateDto.getHouseMemberId()).orElseThrow(() -> new CustomException((ErrorCode.NOT_FOUND_HOUSE_MEMBER)));

        houseMemberProperty.updateHouseMemberProperty(houseMember, houseMemberPropertyUpdateDto);
        houseMemberPropertyRepository.save(houseMemberProperty);

        return new HouseMemberPropertyResponseDto(houseMemberProperty);
    }

    @Transactional(rollbackFor = Exception.class)
    public HttpStatus deleteHouseMemberProperty(Long id){
        HouseMemberProperty houseMemberProperty = houseMemberPropertyRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_PROPERTY));

        houseMemberPropertyRepository.delete(houseMemberProperty);

        return HttpStatus.OK;
    }

    @Transactional(rollbackFor = Exception.class)
    public HouseMemberChungyakResponseDto houseMemberChungyak(HouseMemberChungyakDto houseMemberChungyakDto){
        HouseMember houseMember = houseMemberRepository.findById(houseMemberChungyakDto.getHouseMemberId()).orElseThrow(() -> new CustomException((ErrorCode.NOT_FOUND_HOUSE_MEMBER)));

        HouseMemberChungyak houseMemberChungyak = houseMemberChungyakDto.toEntity(houseMember);
        houseMemberChungyakRepository.save(houseMemberChungyak);

        return new HouseMemberChungyakResponseDto(houseMemberChungyak);
    }

    @Transactional(rollbackFor = Exception.class)
    public HouseMemberChungyakResponseDto updateHouseMemberChungyak(Long id, HouseMemberChungyakUpdateDto houseMemberChungyakUpdateDto){
        HouseMemberChungyak houseMemberChungyak = houseMemberChungyakRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_CHUNGYAK));
        HouseMember houseMember = houseMemberRepository.findById(houseMemberChungyakUpdateDto.getHouseMemberId()).orElseThrow(() -> new CustomException((ErrorCode.NOT_FOUND_HOUSE_MEMBER)));

        houseMemberChungyak.updateHouseMemberChungyak(houseMember, houseMemberChungyakUpdateDto);
        houseMemberChungyakRepository.save(houseMemberChungyak);

        return new HouseMemberChungyakResponseDto(houseMemberChungyak);
    }

    @Transactional(rollbackFor = Exception.class)
    public HttpStatus deleteHouseMemberChungyak(Long id){
        HouseMemberChungyak houseMemberChungyak = houseMemberChungyakRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_CHUNGYAK));

        houseMemberChungyakRestrictionRepository.deleteByHouseMemberChungyak(houseMemberChungyak);
        houseMemberChungyakRepository.delete(houseMemberChungyak);

        return HttpStatus.OK;
    }

    @Transactional(rollbackFor = Exception.class)
    public HouseMemberChungyakRestrictionResponseDto houseMemberChungyakRestriction(HouseMemberChungyakRestrictionDto houseMemberChungyakRestrictionDto){
        HouseMemberChungyak houseMemberChungyak = houseMemberChungyakRepository.findById(houseMemberChungyakRestrictionDto.getHouseMemberChungyakId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_CHUNGYAK));

        HouseMemberChungyakRestriction houseMemberChungyakRestriction = houseMemberChungyakRestrictionDto.toEntity(houseMemberChungyak);
        houseMemberChungyakRestrictionRepository.save(houseMemberChungyakRestriction);

        return new HouseMemberChungyakRestrictionResponseDto(houseMemberChungyakRestriction);
    }

    @Transactional(rollbackFor = Exception.class)
    public HouseMemberChungyakRestrictionResponseDto updateHouseMemberChungyakRestriction(Long id, HouseMemberChungyakRestrictionUpdateDto houseMemberChungyakRestrictionUpdateDto){
        HouseMemberChungyakRestriction houseMemberChungyakRestriction = houseMemberChungyakRestrictionRepository.findById(id).orElseThrow(() -> new CustomException((ErrorCode.NOT_FOUND_HOUSE_MEMBER_CHUNGYAK_RESTRICTION)));
        HouseMemberChungyak houseMemberChungyak = houseMemberChungyakRepository.findById(houseMemberChungyakRestrictionUpdateDto.getHouseMemberChungyakId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_CHUNGYAK));

        houseMemberChungyakRestriction.updateHouseMemberChungyakRestriction(houseMemberChungyak, houseMemberChungyakRestrictionUpdateDto);
        houseMemberChungyakRestrictionRepository.save(houseMemberChungyakRestriction);

        return new HouseMemberChungyakRestrictionResponseDto(houseMemberChungyakRestriction);
    }

    @Transactional(rollbackFor = Exception.class)
    public HttpStatus deleteHouseMemberChungyakRestriction(Long id){
        HouseMemberChungyakRestriction houseMemberChungyakRestriction = houseMemberChungyakRestrictionRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_CHUNGYAK_RESTRICTION));

        houseMemberChungyakRestrictionRepository.delete(houseMemberChungyakRestriction);

        return HttpStatus.OK;
    }

}



//    @Transactional(rollbackFor = Exception.class)
//    public HouseDto patchHouse(User user, HouseDto houseDto){
//        Optional<House> optionalHouse = Optional.ofNullable(houseDto.getSpouseHouseYn().equals(Yn.y) ? user.getSpouseHouse() : user.getHouse());
//        if (optionalHouse.isPresent()){
//            House house = optionalHouse.get();
//            if (StringUtils.isNotBlank(houseDto.getAddressLevel1().toString()))
//                house.setAddressLevel1(addressLevel1Repository.findByAddressLevel1(houseDto.getAddressLevel1()));
//            if (StringUtils.isNotBlank(houseDto.getAddressLevel2().toString()))
//                house.setAddressLevel2(addressLevel2Repository.findByAddressLevel2(houseDto.getAddressLevel2()));
//            if (StringUtils.isNotBlank(houseDto.getAddressDetail()))
//                house.setAddressDetail(houseDto.getAddressDetail());
//            if (StringUtils.isNotBlank(houseDto.getZipcode()))
//                house.setZipcode(houseDto.getZipcode());
//            houseRepository.save(house);
//        }
//        return houseDto;
//    }


//    public HouseMemberRelation houseMemberRelation(User user, HouseMember houseMember, Relation relation){
//        HouseMemberRelation houseMemberRelation = HouseMemberRelation.builder()
//                .user(user)
//                .opponent(houseMember)
//                .relation(relation)
//                .build();
//        houseMemberRelationRepository.save(houseMemberRelation);
//
//        if ((relation.equals(Relation.본인)) || (relation.equals(Relation.배우자))){
//            if (relation.equals(Relation.본인))
//                user.setHouseMember(houseMember);
//            else
//                user.setSpouseHouseMember(houseMember);
//            userRepository.save(user);
//        }
//
//        return  houseMemberRelation;
//    }