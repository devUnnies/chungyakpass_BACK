package com.hanium.chungyakpassback.service.verification;

import com.hanium.chungyakpassback.dto.verification.VerificationOfGeneralMinyeongDto;
import com.hanium.chungyakpassback.dto.verification.VerificationOfGeneralMinyeongResponseDto;
import com.hanium.chungyakpassback.dto.verification.VerificationOfGeneralMinyeongUpdateDto;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.apt.AptInfoTarget;
import com.hanium.chungyakpassback.entity.input.*;
import com.hanium.chungyakpassback.entity.verification.VerificationOfGeneralMinyeong;
import com.hanium.chungyakpassback.entity.standard.AddressLevel1;
import com.hanium.chungyakpassback.entity.standard.PriorityDeposit;
import com.hanium.chungyakpassback.entity.standard.PriorityJoinPeriod;
import com.hanium.chungyakpassback.enumtype.*;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.apt.AptInfoTargetRepository;
import com.hanium.chungyakpassback.repository.input.*;
import com.hanium.chungyakpassback.repository.verification.VerificationOfGeneralMinyeongRepository;
import com.hanium.chungyakpassback.repository.standard.*;
import com.hanium.chungyakpassback.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class VerificationOfGeneralMinyeongServiceImpl implements VerificationOfGeneralMinyeongService {

    final HouseMemberRepository houseMemberRepository;
    final HouseRepository houseRepository;
    final UserBankbookRepository userBankbookRepository;
    final AddressLevel1Repository addressLevel1Repository;
    final UserRepository userRepository;
    final AptInfoRepository aptInfoRepository;
    final HouseMemberChungyakRepository houseMemberChungyakRepository;
    final AptInfoTargetRepository aptInfoTargetRepository;
    final HouseMemberPropertyRepository houseMemberPropertyRepository;
    final HouseMemberRelationRepository houseMemberRelationRepository;
    final BankbookRepository bankbookRepository;
    final PriorityDepositRepository priorityDepositRepository;
    final PrioritySubscriptionPeriodRepository prioritySubscriptionPeriodRepository;
    final PriorityJoinPeriodRepository priorityJoinPeriodRepository;
    final HouseMemberChungyakRestrictionRepository houseMemberChungyakRestrictionRepository;
    final VerificationOfGeneralMinyeongRepository verificationOfGeneralMinyeongRepository;

    @Override //??????????????????
    public List<VerificationOfGeneralMinyeongResponseDto> readGeneralMinyeongVerifications() {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();

        List<VerificationOfGeneralMinyeongResponseDto> verificationOfGeneralMinyeongResponseDtos = new ArrayList<>();
        for (VerificationOfGeneralMinyeong verificationOfGeneralMinyeong : verificationOfGeneralMinyeongRepository.findAllByUser(user)) {
            VerificationOfGeneralMinyeongResponseDto verificationOfGeneralMinyeongResponseDto = new VerificationOfGeneralMinyeongResponseDto(verificationOfGeneralMinyeong);
            verificationOfGeneralMinyeongResponseDtos.add(verificationOfGeneralMinyeongResponseDto);
        }

        return verificationOfGeneralMinyeongResponseDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VerificationOfGeneralMinyeongResponseDto createGeneralMinyeongVerification(VerificationOfGeneralMinyeongDto verificationOfGeneralMinyeongDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(verificationOfGeneralMinyeongDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(verificationOfGeneralMinyeongDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        boolean meetLivingInSurroundAreaTf = meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = meetBankbookType(user, aptInfo, aptInfoTarget);
        Integer americanAge = calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean householderTf = isHouseholder(user);
        boolean isRestrictedAreaTf = isRestrictedArea(aptInfo);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = meetAllHouseMemberNotWinningIn5years(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = meetAllHouseMemberRewinningRestriction(user);
        boolean meetHouseHavingLessThan2AptTf = meetHouseHavingLessThan2Apt(user);
        boolean meetBankbookJoinPeriodTf = meetBankbookJoinPeriod(user, aptInfo);
        boolean meetDepositTf = meetDeposit(user, aptInfoTarget);
        boolean isPriorityApt = isPriorityApt(aptInfo, aptInfoTarget);

        VerificationOfGeneralMinyeong verificationOfGeneralMinyeong = new VerificationOfGeneralMinyeong(user, americanAge, meetLivingInSurroundAreaTf, accountTf, householderTf, meetAllHouseMemberNotWinningIn5yearsTf, meetAllHouseMemberRewinningRestrictionTf, meetHouseHavingLessThan2AptTf, meetBankbookJoinPeriodTf, meetDepositTf, isRestrictedAreaTf, isPriorityApt, aptInfo, aptInfoTarget);
        verificationOfGeneralMinyeongRepository.save(verificationOfGeneralMinyeong);
        return new VerificationOfGeneralMinyeongResponseDto(verificationOfGeneralMinyeong);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VerificationOfGeneralMinyeongResponseDto updateGeneralMinyeongVerification(Long id, VerificationOfGeneralMinyeongUpdateDto verificationOfGeneralMinyeongUpdateDto) {
        VerificationOfGeneralMinyeong verificationOfGeneralMinyeong = verificationOfGeneralMinyeongRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VERIFICATION_RECORD_ID));
        verificationOfGeneralMinyeong.setSibilingSupportYn(verificationOfGeneralMinyeongUpdateDto.getSibilingSupportYn());
        verificationOfGeneralMinyeong.setRanking(verificationOfGeneralMinyeongUpdateDto.getRanking());
        verificationOfGeneralMinyeongRepository.save(verificationOfGeneralMinyeong);
        return new VerificationOfGeneralMinyeongResponseDto(verificationOfGeneralMinyeong);
    }

    public int houseTypeConverter(AptInfoTarget aptInfoTarget) { // . ???????????? ????????? ????????? ?????? ????????? ????????? int ????????? ?????????
        String housingTypeChange = aptInfoTarget.getHousingType().substring(0, aptInfoTarget.getHousingType().indexOf("."));

        return Integer.parseInt(housingTypeChange);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int calcAmericanAge(LocalDate birthday) {
        if (birthday == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY); //????????? ???????????? ?????? ?????? ???????????? ?????????.
        }

        LocalDate now = LocalDate.now();
        int americanAge = now.minusYears(birthday.getYear()).getYear();

        if (birthday.plusYears(americanAge).isAfter(now)) // ????????? ???????????? ????????? ??????
            americanAge = americanAge - 1;

        return americanAge;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetBankbookType(User user, AptInfo aptInfo, AptInfoTarget aptInfoTarget) {
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        if (optUserBankbook.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_BANKBOOK);
        } else {
            Optional<com.hanium.chungyakpassback.entity.standard.Bankbook> stdBankbook = bankbookRepository.findByBankbook(optUserBankbook.get().getBankbook());
            int housingTypeChange = houseTypeConverter(aptInfoTarget); // ??????????????? ????????? ??????
            if (stdBankbook.get().getPrivateHousingSupplyIsPossible().equals(Yn.y)) {
                if (stdBankbook.get().getBankbook().equals(Bankbook.????????????)) {
                    if (housingTypeChange <= stdBankbook.get().getRestrictionSaleArea()) {
                        return true;
                    } else if (housingTypeChange > stdBankbook.get().getRestrictionSaleArea()) { // ??????????????????, ????????? 85??????????????? ????????? ?????? false
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isHouseholder(User user) {
        if (user.getHouse().getHouseHolder() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_HOLDER); //????????? ????????? ??????????????? ?????? ????????? ??????.
        } else if (user.getHouse().getHouseHolder().getId().equals(user.getHouseMember().getId())) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetLivingInSurroundArea(User user, AptInfo aptInfo) {//????????? ??????????????? ??????????????? ???????????? ??????????????? ?????????
        if (user.getHouseMember() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER); // ???????????????->????????? ????????? ????????? user??? ??????_??????1??? ???????????? ????????? ????????? user??? ?????????????????? ??????????????? ??????.
        }

        AddressLevel1 userAddressLevel1 = Optional.ofNullable(user.getHouseMember().getHouse().getAddressLevel1()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS_LEVEL1));
        AddressLevel1 aptAddressLevel1 = addressLevel1Repository.findByAddressLevel1(aptInfo.getAddressLevel1()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS_LEVEL1));

        if (userAddressLevel1.getNearbyArea() == aptAddressLevel1.getNearbyArea()) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isRestrictedArea(AptInfo aptInfo) {
        if (aptInfo.getSpeculationOverheated().equals(Yn.y) || aptInfo.getSubscriptionOverheated().equals(Yn.y))
            return true;
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetBankbookJoinPeriod(User user, AptInfo aptInfo) { //??????????????????????????????
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        if (optUserBankbook.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_BANKBOOK);
        }
        UserBankbook userBankbook = optUserBankbook.get();

        LocalDate joinDate = userBankbook.getJoinDate();
        LocalDate now = LocalDate.now();
        Period period = joinDate.until(now);
        int joinPeriod = period.getYears() * 12 + period.getMonths(); // ??????????????? ???????????? ??????????????? ???????????? ??????

        List<PriorityJoinPeriod> priorityJoinPeriodList = priorityJoinPeriodRepository.findAll();

        for (PriorityJoinPeriod priorityJoinPeriod : priorityJoinPeriodList) {
            if (priorityJoinPeriod.getSupply().equals(Supply.????????????)) { // ??????????????? ??????????????? ??????????????? ??????????????? ??????,
                if (priorityJoinPeriod.getSpeculationOverheated().equals(aptInfo.getSpeculationOverheated()) && priorityJoinPeriod.getSubscriptionOverheated().equals(aptInfo.getSubscriptionOverheated()) && priorityJoinPeriod.getAtrophyArea().equals(aptInfo.getAtrophyArea()) && priorityJoinPeriod.getMetropolitanAreaYn().equals(addressLevel1Repository.findByAddressLevel1(aptInfo.getAddressLevel1()).get().getMetropolitanAreaYn())) {
                    if (joinPeriod >= priorityJoinPeriod.getSubscriptionPeriod())
                        return true;
                }
            }
        }

        return false;
    }

    // ?????????????????? ??????
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetDeposit(User user, AptInfoTarget aptInfoTarget) { // ??????????????????????????????
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        if (optUserBankbook.isEmpty())
            throw new RuntimeException("????????? ??????????????? ????????????.");
        UserBankbook userBankbook = optUserBankbook.get();

        int housingTypeChange = houseTypeConverter(aptInfoTarget);
        List<PriorityDeposit> priorityDepositList = priorityDepositRepository.findAll();


        for (PriorityDeposit priorityDeposit : priorityDepositList) {
            if (priorityDeposit.getDepositArea().equals(user.getHouse().getAddressLevel1().getDepositArea())) {
                if (priorityDeposit.getAreaOver() < housingTypeChange && priorityDeposit.getAreaLessOrEqual() >= housingTypeChange && userBankbook.getDeposit() >= priorityDeposit.getDeposit()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isPriorityApt(AptInfo aptInfo, AptInfoTarget aptInfoTarget) {
        if ((houseTypeConverter(aptInfoTarget) > 85 && aptInfo.getPublicRentalHousing().equals(Yn.y)))
            return true;
        else if (aptInfo.getHousingType().equals(HousingType.??????) && aptInfo.getPublicHosingDistrict().equals(Yn.y) && addressLevel1Repository.findByAddressLevel1(aptInfo.getAddressLevel1()).equals(addressLevel1Repository.findAllByMetropolitanAreaYn(Yn.y)))
            return true;
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetHouseHavingLessThan2Apt(User user) {
        List<HouseMember> houseMemberList = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse());

        int houseCount = 0;
        houseCount = countHouseHaving(user, houseMemberList, houseCount);
        if (houseCount < 2) //2?????? ???????????? true
            return true;
        else
            return false; //????????? false
    }

    public Integer countHouseHaving(User user, List<HouseMember> houseMemberList, Integer houseCount) {//?????? ????????? List?????????
        // ????????? ????????? ??????
        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMember houseMember : houseMemberList) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                int flag = 0;
                int specialCase = 0;
                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//??????????????? ???????????????
                        HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                        if ((houseMemberRelation.getRelation().getRelation().equals(Relation.???) || houseMemberRelation.getRelation().getRelation().equals(Relation.???) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????) || houseMemberRelation.getRelation().getRelation().equals(Relation.???????????????) || houseMemberRelation.getRelation().getRelation().equals(Relation.???????????????) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????????????????) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????????????????))) {
                            if (calcAmericanAge(houseMember.getBirthDay()) >= 60) {
                                specialCase++;
                                continue;
                            }
                        }
                        if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.????????????)) { //???????????????????????? ??????????????? ??????
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) { //2018.12.11 ????????? ????????? ???????????? ??????
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getExceptionHouseYn().equals(Yn.y)) {
                            specialCase++;
                            continue;
                        } else {
                            if (houseMemberProperty.getExclusiveArea() <= 60 && ((houseMemberProperty.getMetropolitanBuildingYn().equals(Yn.y) && houseMemberProperty.getAmount() <= 80000000) || (houseMemberProperty.getMetropolitanBuildingYn().equals(Yn.y) && houseMemberProperty.getAmount() <= 130000000))) { //60???????????? ????????? ????????? ???????????? ?????? ??????
                                flag++;
                                if (specialCase <= 0)
                                    continue;
                                else
                                    houseCount = flag;
                                if (flag <= 1) // ???, 2??? ?????? 2?????? ????????? ?????? ?????? ???????????? ??????. ???, ???????????? count ??? ????????? ??????.
                                    continue;
                                else
                                    houseCount = flag;
                            }
                        }
                        houseCount++;
                    }
                }
            }
        }
        //????????? ??????????????? ??????
        else {
            if (houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse()) == null) {
                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER);
            }
            List<HouseMember> spouseHouseMemberList = houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse());

            for (HouseMember houseMember : houseMemberList) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                int flag = 0;
                int specialCase = 0;
                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//??????????????? ???????????????
                        HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                        if ((houseMemberRelation.getRelation().getRelation().equals(Relation.???) || houseMemberRelation.getRelation().getRelation().equals(Relation.???) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????) || houseMemberRelation.getRelation().getRelation().equals(Relation.???????????????) || houseMemberRelation.getRelation().getRelation().equals(Relation.???????????????) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????????????????) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????????????????))) {
                            if (calcAmericanAge(houseMember.getBirthDay()) >= 60) {
                                specialCase++;
                                continue;
                            }
                        }
                        if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.????????????)) { //???????????????????????? ??????????????? ??????
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) { //2018.12.11 ????????? ????????? ???????????? ??????
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getExceptionHouseYn().equals(Yn.y)) {
                            specialCase++;
                            continue;
                        } else {
                            if (houseMemberProperty.getExclusiveArea() <= 60 && ((houseMemberProperty.getMetropolitanBuildingYn().equals(Yn.y) && houseMemberProperty.getAmount() <= 80000000) || (houseMemberProperty.getMetropolitanBuildingYn().equals(Yn.y) && houseMemberProperty.getAmount() <= 130000000))) { //60???????????? ????????? ????????? ???????????? ?????? ??????
                                flag++;
                                if (specialCase <= 0)
                                    continue;
                                else
                                    houseCount = flag;
                                if (flag <= 1) // ???, 2??? ?????? 2?????? ????????? ?????? ?????? ???????????? ??????. ???, ???????????? count ??? ????????? ??????.
                                    continue;
                                else
                                    houseCount = flag;
                            }
                        }
                        houseCount++;
                    }
                }
            }
            for (HouseMember houseMember : spouseHouseMemberList) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                int flag = 0;
                int specialCase = 0;
                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//??????????????? ???????????????
                        HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                        if ((houseMemberRelation.getRelation().getRelation().equals(Relation.???) || houseMemberRelation.getRelation().getRelation().equals(Relation.???) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????) || houseMemberRelation.getRelation().getRelation().equals(Relation.???????????????) || houseMemberRelation.getRelation().getRelation().equals(Relation.???????????????) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????????????????) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????????????????))) {
                            if (calcAmericanAge(houseMember.getBirthDay()) >= 60) {
                                specialCase++;
                                continue;
                            }
                        }
                        if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.????????????)) { //???????????????????????? ??????????????? ??????
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) { //2018.12.11 ????????? ????????? ???????????? ??????
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getExceptionHouseYn().equals(Yn.y)) {
                            specialCase++;
                            continue;
                        } else {
                            if (houseMemberProperty.getExclusiveArea() <= 60 && ((houseMemberProperty.getMetropolitanBuildingYn().equals(Yn.y) && houseMemberProperty.getAmount() <= 80000000) || (houseMemberProperty.getMetropolitanBuildingYn().equals(Yn.y) && houseMemberProperty.getAmount() <= 130000000))) { //60???????????? ????????? ????????? ???????????? ?????? ??????
                                flag++;
                                if (specialCase <= 0)
                                    continue;
                                else
                                    houseCount = flag;
                                if (flag <= 1) // ???, 2??? ?????? 2?????? ????????? ?????? ?????? ???????????? ??????. ???, ???????????? count ??? ????????? ??????.
                                    continue;
                                else
                                    houseCount = flag;
                            }
                        }
                        houseCount++;
                    }
                }
            }
        }
        System.out.println("houseCount : " + houseCount);
        return houseCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetAllHouseMemberNotWinningIn5years(User user) { // ?????? 5??? ????????? ?????? ????????? ????????? ?????? ?????? ?????? ????????????????????????
        LocalDate now = LocalDate.now();
        int periodYear = 0;

        List<HouseMember> houseMemberListUser = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse());

        //???????????? ?????? ???????????????, ????????? ??????
        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMember houseMember : houseMemberListUser) {
                List<HouseMemberChungyak> houseMemberChungyakList = houseMemberChungyakRepository.findAllByHouseMember(houseMember);

                for (HouseMemberChungyak houseMemberChungyak : houseMemberChungyakList) {
                    periodYear = now.minusYears(houseMemberChungyak.getWinningDate().getYear()).getYear();

                    if (periodYear <= 5)
                        return false;
                }
                return true;
            }
        }
        //????????? ??????????????? ??????
        else {
            List<HouseMember> spouseHouseMemberList = houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse()); // ???????????? ???????????? ????????????????????? ?????? ????????? List??? ?????????

            for (HouseMember houseMember : houseMemberListUser) {
                List<HouseMemberChungyak> houseMemberChungyakList = houseMemberChungyakRepository.findAllByHouseMember(houseMember);

                for (HouseMemberChungyak houseMemberChungyak : houseMemberChungyakList) {
                    periodYear = now.minusYears(houseMemberChungyak.getWinningDate().getYear()).getYear();

                    if (periodYear <= 5)
                        return false;
                }
            }

            for (HouseMember houseMember : spouseHouseMemberList) {
                List<HouseMemberChungyak> houseMemberChungyakList = houseMemberChungyakRepository.findAllByHouseMember(houseMember);

                for (HouseMemberChungyak houseMemberChungyak : houseMemberChungyakList) {
                    periodYear = now.minusYears(houseMemberChungyak.getWinningDate().getYear()).getYear();

                    if (periodYear <= 5)
                        return false;
                }
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetAllHouseMemberRewinningRestriction(User user) { //?????????????????????????????????
        LocalDate now = LocalDate.now(); //?????? ?????? ????????????

        List<HouseMemberChungyak> houseMemberListUser = houseMemberChungyakRepository.findAllByHouseMember(user.getHouseMember());

        //???????????? ?????? ???????????????, ????????? ??????
        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMemberChungyak houseMemberChungyak : houseMemberListUser) {
                List<HouseMemberChungyakRestriction> houseMemberChungyakRestrictionList = houseMemberChungyakRestrictionRepository.findAllByHouseMemberChungyak(houseMemberChungyak);

                for (HouseMemberChungyakRestriction houseMemberChungyakRestriction : houseMemberChungyakRestrictionList) {
                    if (!(houseMemberChungyakRestriction.getReWinningRestrictedDate() == null)) { // ??????????????? ????????? ?????? ?????????????????? ??????
                        if (houseMemberChungyakRestriction.getReWinningRestrictedDate().isAfter(now)) { // ?????? ????????? ???????????? ??????????????? ????????? ???????????? ??????
                            return false;
                        }
                    }
                }
            }
        }
        //????????? ??????????????? ??????
        else {
            List<HouseMemberChungyak> spouseHouseMemberListUser = houseMemberChungyakRepository.findAllByHouseMember(user.getSpouseHouseMember());

            for (HouseMemberChungyak houseMemberChungyak : houseMemberListUser) {
                List<HouseMemberChungyakRestriction> houseMemberChungyakRestrictionList = houseMemberChungyakRestrictionRepository.findAllByHouseMemberChungyak(houseMemberChungyak);

                for (HouseMemberChungyakRestriction houseMemberChungyakRestriction : houseMemberChungyakRestrictionList) {
                    if (!(houseMemberChungyakRestriction.getReWinningRestrictedDate() == null)) { // ??????????????? ????????? ?????? ?????????????????? ??????
                        if (houseMemberChungyakRestriction.getReWinningRestrictedDate().isAfter(now)) { // ?????? ????????? ???????????? ??????????????? ????????? ???????????? ??????
                            return false;
                        }
                    }
                }
            }

            for (HouseMemberChungyak houseMemberChungyak : spouseHouseMemberListUser) {
                List<HouseMemberChungyakRestriction> houseMemberChungyakRestrictionList = houseMemberChungyakRestrictionRepository.findAllByHouseMemberChungyak(houseMemberChungyak);

                for (HouseMemberChungyakRestriction houseMemberChungyakRestriction : houseMemberChungyakRestrictionList) {
                    if (!(houseMemberChungyakRestriction.getReWinningRestrictedDate() == null)) { // ??????????????? ????????? ?????? ?????????????????? ??????
                        if (houseMemberChungyakRestriction.getReWinningRestrictedDate().isAfter(now)) { // ?????? ????????? ???????????? ??????????????? ????????? ???????????? ??????
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
