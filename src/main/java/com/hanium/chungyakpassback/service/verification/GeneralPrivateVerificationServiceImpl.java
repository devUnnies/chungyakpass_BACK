package com.hanium.chungyakpassback.service.verification;

import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.apt.AptInfoTarget;
import com.hanium.chungyakpassback.entity.input.*;
import com.hanium.chungyakpassback.entity.standard.AddressLevel1;
import com.hanium.chungyakpassback.entity.standard.PriorityDeposit;
import com.hanium.chungyakpassback.entity.standard.PriorityJoinPeriod;
import com.hanium.chungyakpassback.enumtype.*;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.apt.AptInfoTargetRepository;
import com.hanium.chungyakpassback.repository.input.*;
import com.hanium.chungyakpassback.repository.standard.*;
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
public class GeneralPrivateVerificationServiceImpl implements GeneralPrivateVerificationService {

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


    public int houseTypeConverter(AptInfoTarget aptInfoTarget) { // . 기준으로 주택형 자른후 면적 비교를 위해서 int 형으로 형변환
        String housingTypeChange = aptInfoTarget.getHousingType().substring(0, aptInfoTarget.getHousingType().indexOf("."));

        return Integer.parseInt(housingTypeChange);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int calcAmericanAge(LocalDate birthday) {
        if (birthday == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY); //생일이 입력되지 않은 경우 경고문을 띄워줌.
        }

        LocalDate now = LocalDate.now();
        int americanAge = now.minusYears(birthday.getYear()).getYear();

        if (birthday.plusYears(americanAge).isAfter(now)) // 생일이 지났는지 여부를 판단
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
            int housingTypeChange = houseTypeConverter(aptInfoTarget); // 주택형변환 메소드 호출
            if (stdBankbook.get().getPrivateHousingSupplyIsPossible().equals(Yn.y)) {
                if (stdBankbook.get().getBankbook().equals(Bankbook.청약부금)) {
                    if (housingTypeChange <= stdBankbook.get().getRestrictionSaleArea()) {
                        return true;
                    }
                    return false;
                }
                return true;
            } else {
                throw new CustomException(ErrorCode.BAD_REQUEST_BANKBOOK);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isHouseholder(User user) {
        if (user.getHouse().getHouseHolder() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_HOLDER); //세대주 지정이 안되어있을 경우 경고를 띄움.
        } else if (user.getHouse().getHouseHolder().getId().equals(user.getHouseMember().getId())) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetLivingInSurroundArea(User user, AptInfo aptInfo) {//아파트 분양정보의 인근지역과 거주지의 인근지역이 같다면
        if (user.getHouseMember() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER); // 세대구성원->세대를 통해서 주소를 user의 지역_레벨1을 가져오는 것이기 때문에 user의 세대구성원이 비어있으면 안됨.
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
    public boolean meetBankbookJoinPeriod(User user, AptInfo aptInfo) { //가입기간충족여부확인
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        if (optUserBankbook.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_BANKBOOK);
        }
        UserBankbook userBankbook = optUserBankbook.get();

        LocalDate joinDate = userBankbook.getJoinDate();
        LocalDate now = LocalDate.now();
        Period period = joinDate.until(now);
        int joinPeriod = period.getYears() * 12 + period.getMonths(); // 가입날짜를 받아와서 현재까지의 개월수를 계산

        List<PriorityJoinPeriod> priorityJoinPeriodList = priorityJoinPeriodRepository.findAll();

        for (PriorityJoinPeriod priorityJoinPeriod : priorityJoinPeriodList) {
            if (userBankbook.getValidYn().equals(Yn.y) && priorityJoinPeriod.getSupply().equals(Supply.일반공급)) { // 청약통장이 유효하면서 공급유형이 일반공급인 경우,
                if (priorityJoinPeriod.getSpeculationOverheated().equals(aptInfo.getSpeculationOverheated()) && priorityJoinPeriod.getSubscriptionOverheated().equals(aptInfo.getSubscriptionOverheated()) && priorityJoinPeriod.getAtrophyArea().equals(aptInfo.getAtrophyArea()) && priorityJoinPeriod.getMetropolitanAreaYn().equals(addressLevel1Repository.findByAddressLevel1(aptInfo.getAddressLevel1()).get().getMetropolitanAreaYn())) {
                    if (joinPeriod >= priorityJoinPeriod.getSubscriptionPeriod())
                        return true;
                }
            }
        }

        return false;
    }

    // 예치금액충족 여부
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetDeposit(User user, AptInfoTarget aptInfoTarget) { // 예치금액충족여부확인
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        if (optUserBankbook.isEmpty())
            throw new RuntimeException("등록된 청약통장이 없습니다.");
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
        else if (aptInfo.getHousingType().equals(HousingType.민영) && aptInfo.getPublicHosingDistrict().equals(Yn.y) && addressLevel1Repository.findByAddressLevel1(aptInfo.getAddressLevel1()).equals(addressLevel1Repository.findAllByMetropolitanAreaYn(Yn.y)))
            return true;
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetHouseHavingLessThan2Apt(User user) {
        List<HouseMember> houseMemberList = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse());

        int houseCount = 0;

        // 하나의 세대일 경우
        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMember houseMember : houseMemberList) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                int flag = 0;
                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//소유주택이 주거용이면
                        HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                        if ((houseMemberRelation.getRelation().getRelation().equals(Relation.부) || houseMemberRelation.getRelation().getRelation().equals(Relation.모) || houseMemberRelation.getRelation().getRelation().equals(Relation.조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.조모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의부)) && calcAmericanAge(houseMember.getBirthDay()) >= 60)
                            continue;
                        else if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.오피스텔))
                            continue;
                        else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11")))
                            continue;
                        else
                            houseCount++;
                    }
                }
            }
        }
        //배우자 분리세대일 경우
        else {
            List<HouseMember> spouseHouseMemberList = houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse());

            for (HouseMember houseMember : houseMemberList) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                int flag = 0;
                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//소유주택이 주거용이면
                        HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                        if ((houseMemberRelation.getRelation().getRelation().equals(Relation.부) || houseMemberRelation.getRelation().getRelation().equals(Relation.모) || houseMemberRelation.getRelation().getRelation().equals(Relation.조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.조모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의부)) && calcAmericanAge(houseMember.getBirthDay()) >= 60)
                            continue;
                        else if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.오피스텔))
                            continue;
                        else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11")))
                            continue;
                        else
                            houseCount++;
                    }
                }
            }
            for (HouseMember houseMember : spouseHouseMemberList) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                int flag = 0;
                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//소유주택이 주거용이면
                        HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                        if ((houseMemberRelation.getRelation().getRelation().equals(Relation.부) || houseMemberRelation.getRelation().getRelation().equals(Relation.모) || houseMemberRelation.getRelation().getRelation().equals(Relation.조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.조모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의부)) && calcAmericanAge(houseMember.getBirthDay()) >= 60)
                            continue;
                        else if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.오피스텔))
                            continue;
                        else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11")))
                            continue;
                        else
                            houseCount++;
                    }
                }
            }
        }
        System.out.println("주택수 : " + houseCount);

        if (houseCount < 2) //2주택 미만이면 true
            return true;
        else
            return false; //아니면 false
    }


    //보류
    public Integer getHouseMember(User user) {//세대구성원 대상여부 파악하는 메소드
        Integer houseCount = 0;
        List<HouseMember> houseMemberList = houseMemberRepository.findAllByHouse(user.getHouse());
        List<HouseMember> wifeHouseMemberList = houseMemberRepository.findAllByHouse(user.getSpouseHouse());
        List<HouseMember> copleHouMemberList = new ArrayList<>();
        copleHouMemberList.addAll(houseMemberList);
        copleHouMemberList.addAll(wifeHouseMemberList);

        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) { //배우자와 같은 세대이거나, 미혼일 경우
            for (int i = 0; i < houseMemberList.size(); i++) {//본인이 세대주일 때 무주택직계존속 포함
                houseCount = countHouseHaving(user, houseMemberList, houseCount);
            }
        } else if (user.getHouse() != user.getSpouseHouse()) {//분리 세대인 경우
            for (int i = 0; i < copleHouMemberList.size(); i++) {
                houseCount = countHouseHaving(user, copleHouMemberList, houseCount);//본인 세대에 속한 직계존속및 직계비속 List
            }
        }
        return houseCount;
    }

    //보류
    public Integer countHouseHaving(User user, List<HouseMember> houseMemberList, Integer houseCount) {//세대 구성원 List가져옴
        for (HouseMember houseMember : houseMemberList) {
            List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);
            for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//소유주택이 주거용이면
                    HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                    for (long i = 3L; i <= 10L; i++) {//i->getRelation().getId()직계존속의 id
                        if (houseMemberRelation.getRelation().getId().equals(i) && calcAmericanAge(houseMember.getBirthDay()) >= 60)
                            continue;
                    }
                    if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.오피스텔)) //주거용건물유형이 오피스텥일 경우
                        continue;
                    else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) //2018.12.11 이전에 취득한 분양권일 경우
                        continue;
                    else
                        houseCount++;
                }
            }
        }
        System.out.println(houseCount);
        return houseCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetAllHouseMemberNotWinningIn5years(User user) { // 과거 5년 이내에 다른 주택에 당첨된 자가 속해 있는 무주택세대구성원
        HouseMember houseMember = user.getHouseMember();
        HouseMember spouseHouseMember = user.getSpouseHouseMember();

        LocalDate now = LocalDate.now();
        int periodYear = 0;

        List<HouseMemberChungyak> houseMemberChungyakList = houseMemberChungyakRepository.findAll();

        for (HouseMemberChungyak houseMemberChungyak : houseMemberChungyakList) {
            periodYear = now.minusYears(houseMemberChungyak.getWinningDate().getYear()).getYear();

            if (periodYear <= 5)
                return false;
        }
        return true;
    }
}
