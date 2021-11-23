package com.hanium.chungyakpassback.service.verification;

import com.hanium.chungyakpassback.dto.verification.*;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.apt.AptInfoTarget;
import com.hanium.chungyakpassback.entity.input.*;
import com.hanium.chungyakpassback.entity.verification.VerificationOfpecialKookminNewlyMarried;
import com.hanium.chungyakpassback.entity.standard.Income;
import com.hanium.chungyakpassback.entity.standard.PriorityJoinPeriod;
import com.hanium.chungyakpassback.entity.standard.PriorityPaymentsCount;
import com.hanium.chungyakpassback.enumtype.*;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.apt.AptInfoTargetRepository;
import com.hanium.chungyakpassback.repository.input.*;
import com.hanium.chungyakpassback.repository.verification.VerificationOfSpecialKookminNewlyMarriedRepository;
import com.hanium.chungyakpassback.repository.standard.*;
import com.hanium.chungyakpassback.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VerificationOfSpecialKookminPublicNewlyMarriedServiceImpl implements VerificationOfSpecialKookminPublicNewlyMarriedService {

    final UserBankbookRepository userBankbookRepository;
    final BankbookRepository bankbookRepository;
    final HouseMemberRepository houseMemberRepository;
    final HouseMemberPropertyRepository houseMemberPropertyRepository;
    final HouseMemberRelationRepository houseMemberRelationRepository;
    final AddressLevel1Repository addressLevel1Repository;
    final HouseMemberChungyakRepository houseMemberChungyakRepository;
    final IncomeRepository incomeRepository;
    final PriorityJoinPeriodRepository priorityJoinPeriodRepository;
    final PriorityPaymentsCountRepository priorityPaymentsCountRepository;
    final HouseMemberChungyakRestrictionRepository houseMemberChungyakRestrictionRepository;
    final UserRepository userRepository;
    final AptInfoTargetRepository aptInfoTargetRepository;
    final AptInfoRepository aptInfoRepository;
    final VerificationOfSpecialKookminNewlyMarriedRepository verificationOfSpecialKookminNewlyMarriedRepository;

    @Override //특별신혼부부국민조회
    public List<VerificationOfSpecialKookminPublicNewlyMarriedResponseDto> readSpecialKookminPublicNewlyMarriedVerifications() {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();

        List<VerificationOfSpecialKookminPublicNewlyMarriedResponseDto> verificationOfSpecialKookminPublicNewlyMarriedResponseDtos = new ArrayList<>();
        for (VerificationOfpecialKookminNewlyMarried verificationOfpecialKookminNewlyMarried : verificationOfSpecialKookminNewlyMarriedRepository.findAllByUser(user)) {
            VerificationOfSpecialKookminPublicNewlyMarriedResponseDto verificationOfSpecialKookminPublicNewlyMarriedResponseDto = new VerificationOfSpecialKookminPublicNewlyMarriedResponseDto(verificationOfpecialKookminNewlyMarried);
            verificationOfSpecialKookminPublicNewlyMarriedResponseDtos.add(verificationOfSpecialKookminPublicNewlyMarriedResponseDto);
        }

        return verificationOfSpecialKookminPublicNewlyMarriedResponseDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VerificationOfSpecialKookminPublicNewlyMarriedResponseDto createSpecialKookminPublicNewlyMarriedVerification(VerificationOfSpecialKookminPublicNewlyMarriedDto verificationOfSpecialKookminPublicNewlyMarriedDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(verificationOfSpecialKookminPublicNewlyMarriedDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(verificationOfSpecialKookminPublicNewlyMarriedDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetRecipientTf = meetRecipient(user);
        boolean hasMinorChildren = hasMinorChildren(user);
        boolean meetMonthlyAverageIncomePriorityTf = meetMonthlyAverageIncomePriority(user);
        boolean meetMonthlyAverageIncomeGeneralTf = meetMonthlyAverageIncomeGeneral(user);
        boolean meetPropertyTf = meetProperty(user);
        boolean secondChungyak = secondChungyak(user);
        boolean meetHomelessHouseholdMembersTf = meetHomelessHouseholdMembers(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = meetAllHouseMemberRewinningRestriction(user);
        boolean householderTf = isHouseholder(user);
        boolean isRestrictedAreaTf = isRestrictedArea(aptInfo);
        boolean meetBankbookJoinPeriodTf = meetBankbookJoinPeriod(user, aptInfo);
        boolean meetNumberOfPaymentsTf = meetNumberOfPayments(user, aptInfo);

        VerificationOfpecialKookminNewlyMarried verificationOfpecialKookminNewlyMarried = new VerificationOfpecialKookminNewlyMarried(user, americanAge, meetLivingInSurroundAreaTf, accountTf, meetRecipientTf, hasMinorChildren, meetMonthlyAverageIncomePriorityTf, meetMonthlyAverageIncomeGeneralTf, hasMinorChildren, meetPropertyTf, meetHomelessHouseholdMembersTf, meetAllHouseMemberRewinningRestrictionTf, householderTf, meetBankbookJoinPeriodTf, meetNumberOfPaymentsTf, isRestrictedAreaTf, secondChungyak, aptInfo, aptInfoTarget);
        verificationOfSpecialKookminNewlyMarriedRepository.save(verificationOfpecialKookminNewlyMarried);
        return new VerificationOfSpecialKookminPublicNewlyMarriedResponseDto(verificationOfpecialKookminNewlyMarried);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VerificationOfSpecialKookminPublicNewlyMarriedResponseDto updateSpecialKookminPublicNewlyMarriedVerification(Long verificationRecordSpecialKookminNewlyMarriedId, VerificationOfSpecialKookminPublicNewlyMarriedUpdateDto verificationOfSpecialKookminPublicNewlyMarriedUpdateDto) {
        VerificationOfpecialKookminNewlyMarried verificationOfpecialKookminNewlyMarried = verificationOfSpecialKookminNewlyMarriedRepository.findById(verificationRecordSpecialKookminNewlyMarriedId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VERIFICATION_RECORD_ID));
        verificationOfpecialKookminNewlyMarried.setSibilingSupportYn(verificationOfSpecialKookminPublicNewlyMarriedUpdateDto.getSibilingSupportYn());
        verificationOfpecialKookminNewlyMarried.setKookminType(verificationOfSpecialKookminPublicNewlyMarriedUpdateDto.getKookminType());
        verificationOfpecialKookminNewlyMarried.setPreNewMarriedYn(verificationOfSpecialKookminPublicNewlyMarriedUpdateDto.getPreNewMarriedYn());
        verificationOfpecialKookminNewlyMarried.setRanking(verificationOfSpecialKookminPublicNewlyMarriedUpdateDto.getRanking());
        verificationOfSpecialKookminNewlyMarriedRepository.save(verificationOfpecialKookminNewlyMarried);
        return new VerificationOfSpecialKookminPublicNewlyMarriedResponseDto(verificationOfpecialKookminNewlyMarried);
    }

    public int houseTypeConverter(AptInfoTarget aptInfoTarget) { // 주택형 변환 메소드
        // . 기준으로 주택형 자른후 면적 비교를 위해서 int 형으로 형변환
        String housingTypeChange = aptInfoTarget.getHousingType().substring(0, aptInfoTarget.getHousingType().indexOf("."));

        return Integer.parseInt(housingTypeChange);
    }


    public Long calcDate(LocalDate transferdate) { //신혼 기간 구하기
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime departure = transferdate.atStartOfDay();

        Long days = Duration.between(departure, today).toDays();

        return days;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int calcAmericanAge(LocalDate birthday) { //만나이계산 메소드
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
            if (stdBankbook.get().getNationalHousingSupplyPossible().equals(Yn.y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetRecipient(User user) { //신혼부공공주택적용국민주택대상자충족여부
        List<HouseMember> houseMemberListUser = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse());

        // 혼인기간이 7년 이내일 경우
        if (user.getHouseMember().getMarriageDate() == null) { //혼인신고일이 null일 경우 경고문을 띄워줌
            throw new CustomException(ErrorCode.NOT_FOUND_MARRIAGES);
        } else if (calcDate(user.getHouseMember().getMarriageDate()) < 2555) {
            return true;
        }
        // 한부모가족일 경우
        else if (user.getSpouseHouse() == null) { //배우자의 세대가 null이고(배우자가 없다는 의미),
            for (HouseMember houseMember : houseMemberListUser) {
                HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                if ((houseMemberRelation.getRelation().getRelation().equals(Relation.자녀_일반) && calcAmericanAge(houseMember.getBirthDay()) >= 6) || houseMemberRelation.getRelation().getRelation().equals(Relation.자녀_태아)) //만6세 이상의 자녀나 태아가 있을 경우,
                    return true; // 한부모가족 조건 충족
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean hasMinorChildren(User user) {
        List<HouseMember> houseMemberListUser = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse()); //신청자의 세대구성원 가져오기

        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMember houseMember : houseMemberListUser) {
                HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();

                // 만19세 미만의 미성년 자녀가 있거나, 태아가 있을 경우 1순위
                if ((houseMemberRelation.getRelation().getRelation().equals(Relation.자녀_일반) && calcAmericanAge(houseMember.getBirthDay()) < 19) || houseMemberRelation.getRelation().getRelation().equals(Relation.자녀_태아))
                    return true;
            }
        }

        // 배우자 분리세대일 경우
        else {
            List<HouseMember> houseMemberListSpouse = houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse());

            for (HouseMember houseMember : houseMemberListUser) {
                HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();

                // 만19세 미만의 미성년 자녀가 있거나, 태아가 있을 경우 1순위
                if ((houseMemberRelation.getRelation().getRelation().equals(Relation.자녀_일반) && calcAmericanAge(houseMember.getBirthDay()) < 19) || houseMemberRelation.getRelation().getRelation().equals(Relation.자녀_태아))
                    return true;
            }

            for (HouseMember houseMember : houseMemberListSpouse) {
                HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();

                // 만19세 미만의 미성년 자녀가 있거나, 태아가 있을 경우 1순위
                if ((houseMemberRelation.getRelation().getRelation().equals(Relation.자녀_일반) && calcAmericanAge(houseMember.getBirthDay()) < 19) || houseMemberRelation.getRelation().getRelation().equals(Relation.자녀_태아))
                    return true;
            }
        }
        return false; //그렇지 않으면, 2순위
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetMonthlyAverageIncomePriority(User user) { //월평균소득기준충족여부_우선공급
        List<HouseMember> houseMemberListUser = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse()); //신청자의 세대구성원 가져오기

        Optional<Income> noneDualIncome = incomeRepository.findBySpecialSupplyAndSupplyAndDualIncomeAndApplicationPublicHousingSpecialLaws(SpecialSupply.신혼부부, Supply.우선공급, Yn.n, Yn.y);
        Optional<Income> dualIncome = incomeRepository.findBySpecialSupplyAndSupplyAndDualIncomeAndApplicationPublicHousingSpecialLaws(SpecialSupply.신혼부부, Supply.우선공급, Yn.y, Yn.y);

        int houseMemberCount = 0; //세대구성원수
        int sumIncome = 0; // 소득합산

        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMember houseMember : houseMemberListUser) {
                houseMemberCount++;
                if (!(houseMember.getBirthDay() == null) && calcAmericanAge(houseMember.getBirthDay()) >= 19 && houseMember.getIncome() != null) //만19세 이상만 소득 산정
                    sumIncome += houseMember.getIncome();
            }
        }
        //배우자 분리세대일 경우
        else {
            List<HouseMember> spouseHouseMemberList = houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse()); // 신청자의 배우자의 전세대구성원의 자산 정보를 List로 가져옴

            for (HouseMember houseMember : houseMemberListUser) {
                houseMemberCount++;
                if (!(houseMember.getBirthDay() == null) && calcAmericanAge(houseMember.getBirthDay()) >= 19 && houseMember.getIncome() != null) //만19세 이상만 소득 산정
                    sumIncome += houseMember.getIncome();
            }
            for (HouseMember houseMember : spouseHouseMemberList) {
                houseMemberCount++;
                if (!(houseMember.getBirthDay() == null) && calcAmericanAge(houseMember.getBirthDay()) >= 19 && houseMember.getIncome() != null) //만19세 이상만 소득 산정
                    sumIncome += houseMember.getIncome();
            }
        }

        System.out.println("세대구성원 수 : " + houseMemberCount);
        System.out.println("소득합산 : " + sumIncome);

        // 배우자가 세대구성원에 등록되어 있지 않을 경우 경고문을 띄워줌.
        if (user.getSpouseHouseMember() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_SPOUSE);
        }

        //user나 배우자 둘 중에 한 명이 소득이 없을 경우(외벌이)
        if (user.getHouseMember().getIncome() == null || user.getSpouseHouseMember().getIncome() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_INCOME); //신청자나 배우자 둘 중에 한명이라도 income에 null 값이 들어올 경우 경고문을 띄워줌.
        }

        if (user.getHouseMember().getIncome() == 0 || user.getSpouseHouseMember().getIncome() == 0) {
            if (houseMemberCount <= 3) {
                if (sumIncome <= noneDualIncome.get().getAverageMonthlyIncome3peopleLessBelow()) {
                    return true;
                }
            } else if (houseMemberCount <= 4) {
                if (sumIncome <= noneDualIncome.get().getAverageMonthlyIncome4peopleLessBelow()) {
                    return true;
                }
            } else if (houseMemberCount <= 5) {
                if (sumIncome <= noneDualIncome.get().getAverageMonthlyIncome5peopleLessBelow()) {
                    return true;
                }
            }
        }
        // 맞벌이일 경우
        else {
            if (!(user.getHouseMember().getIncome() > noneDualIncome.get().getAverageMonthlyIncome3peopleLessBelow() || user.getSpouseHouseMember().getIncome() > noneDualIncome.get().getAverageMonthlyIncome3peopleLessBelow())) { // 맞벌이일 경우, 부부 중 1인의 소득이 월평균소득의 100%를 초과하면 안됨.
                if (houseMemberCount <= 3) {
                    if (sumIncome <= dualIncome.get().getAverageMonthlyIncome3peopleLessBelow() && sumIncome > dualIncome.get().getAverageMonthlyIncome3peopleLessExcess()) {
                        return true;
                    }
                } else if (houseMemberCount <= 4) {
                    if (sumIncome <= dualIncome.get().getAverageMonthlyIncome4peopleLessBelow() && sumIncome > dualIncome.get().getAverageMonthlyIncome4peopleLessExcess()) {
                        return true;
                    }
                } else if (houseMemberCount <= 5) {
                    if (sumIncome <= dualIncome.get().getAverageMonthlyIncome5peopleLessBelow() && sumIncome > dualIncome.get().getAverageMonthlyIncome5peopleLessExcess()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetMonthlyAverageIncomeGeneral(User user) { //월평균소득기준충족여부_일반공급
        List<HouseMember> houseMemberListUser = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse()); //신청자의 세대구성원 가져오기

        Optional<Income> noneDualIncome = incomeRepository.findBySpecialSupplyAndSupplyAndDualIncomeAndApplicationPublicHousingSpecialLaws(SpecialSupply.신혼부부, Supply.일반공급, Yn.n, Yn.y);
        Optional<Income> dualIncome = incomeRepository.findBySpecialSupplyAndSupplyAndDualIncomeAndApplicationPublicHousingSpecialLaws(SpecialSupply.신혼부부, Supply.일반공급, Yn.y, Yn.y);

        int houseMemberCount = 0; //세대구성원수
        int sumIncome = 0; // 소득합산

        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMember houseMember : houseMemberListUser) {
                houseMemberCount++;
                if (!(houseMember.getBirthDay() == null) && calcAmericanAge(houseMember.getBirthDay()) >= 19 && houseMember.getIncome() != null) //만19세 이상만 소득 산정
                    sumIncome += houseMember.getIncome();
            }
        }
        //배우자 분리세대일 경우
        else {
            List<HouseMember> spouseHouseMemberList = houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse()); // 신청자의 배우자의 전세대구성원의 자산 정보를 List로 가져옴

            for (HouseMember houseMember : houseMemberListUser) {
                houseMemberCount++;
                if (!(houseMember.getBirthDay() == null) && calcAmericanAge(houseMember.getBirthDay()) >= 19 && houseMember.getIncome() != null) //만19세 이상만 소득 산정
                    sumIncome += houseMember.getIncome();
            }
            for (HouseMember houseMember : spouseHouseMemberList) {
                houseMemberCount++;
                if (!(houseMember.getBirthDay() == null) && calcAmericanAge(houseMember.getBirthDay()) >= 19 && houseMember.getIncome() != null) //만19세 이상만 소득 산정
                    sumIncome += houseMember.getIncome();
            }
        }

        System.out.println("세대구성원 수 : " + houseMemberCount);
        System.out.println("소득합산 : " + sumIncome);

        // 배우자가 세대구성원에 등록되어 있지 않을 경우 경고문을 띄워줌.
        if (user.getSpouseHouseMember() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_SPOUSE);
        }

        //user나 배우자 둘 중에 한 명이 소득이 없을 경우(외벌이)
        if (user.getHouseMember().getIncome() == null || user.getSpouseHouseMember().getIncome() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_INCOME); //신청자나 배우자 둘 중에 한명이라도 income에 null 값이 들어올 경우 경고문을 띄워줌.
        }

        if (user.getHouseMember().getIncome() == 0 || user.getSpouseHouseMember().getIncome() == 0) {
            if (houseMemberCount <= 3) {
                if (sumIncome <= noneDualIncome.get().getAverageMonthlyIncome3peopleLessBelow() && sumIncome > noneDualIncome.get().getAverageMonthlyIncome3peopleLessExcess()) {
                    return true;
                }
            } else if (houseMemberCount <= 4) {
                if (sumIncome <= noneDualIncome.get().getAverageMonthlyIncome4peopleLessBelow() && sumIncome > noneDualIncome.get().getAverageMonthlyIncome4peopleLessExcess()) {
                    return true;
                }
            } else if (houseMemberCount <= 5) {
                if (sumIncome <= noneDualIncome.get().getAverageMonthlyIncome5peopleLessBelow() && sumIncome > noneDualIncome.get().getAverageMonthlyIncome5peopleLessExcess()) {
                    return true;
                }
            }
        }
        // 맞벌이일 경우
        else {
            if (!(user.getHouseMember().getIncome() > noneDualIncome.get().getAverageMonthlyIncome3peopleLessBelow() || user.getSpouseHouseMember().getIncome() > noneDualIncome.get().getAverageMonthlyIncome3peopleLessBelow())) { // 맞벌이일 경우, 부부 중 1인의 소득이 월평균소득의 130%를 초과하면 안됨.
                if (houseMemberCount <= 3) {
                    if (sumIncome <= dualIncome.get().getAverageMonthlyIncome3peopleLessBelow() && sumIncome > dualIncome.get().getAverageMonthlyIncome3peopleLessExcess()) {
                        return true;
                    }
                } else if (houseMemberCount <= 4) {
                    if (sumIncome <= dualIncome.get().getAverageMonthlyIncome4peopleLessBelow() && sumIncome > dualIncome.get().getAverageMonthlyIncome4peopleLessExcess()) {
                        return true;
                    }
                } else if (houseMemberCount <= 5) {
                    if (sumIncome <= dualIncome.get().getAverageMonthlyIncome5peopleLessBelow() && sumIncome > dualIncome.get().getAverageMonthlyIncome5peopleLessExcess()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetProperty(User user) { //자산기준충족여부
        List<HouseMember> houseMemberListUser = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse());

//        int overPropertyStandard = 0;
        int sumEstateProperty = 0; //부동산자산합
        int sumCarProperty = 0; //자동차자산합


        //배우자와 같은 세대이거나, 미혼일 경우
        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMember houseMember : houseMemberListUser) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getProperty().equals(Property.건물) || houseMemberProperty.getProperty().equals(Property.토지)) { //해당 세대가 소유하고 있는 부동산(건물+토지)이 215,500천원 초과일 경우 false
                        sumEstateProperty += houseMemberProperty.getAmount();
                    } else if (houseMemberProperty.getProperty().equals(Property.자동차)) { //해당 세대가 소유하고 있는 자동차가 34,960천원 초과일 경우 false
                        sumCarProperty += houseMemberProperty.getAmount();
                    }
                }
            }
        }
        // 배우자분리세대일 경우
        else {
            List<HouseMember> houseMemberListSpouse = houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse()); //배우자분리세대일 경우, 배우자의 세대구성원 가져오기

            for (HouseMember houseMember : houseMemberListUser) { //신청자 세대 조회
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getProperty().equals(Property.건물) || houseMemberProperty.getProperty().equals(Property.토지)) { //해당 세대가 소유하고 있는 부동산(건물+토지)이 215,500천원 초과일 경우 false
                        sumEstateProperty += houseMemberProperty.getAmount();
                    } else if (houseMemberProperty.getProperty().equals(Property.자동차)) { //해당 세대가 소유하고 있는 자동차가 34,960천원 초과일 경우 false
                        sumCarProperty += houseMemberProperty.getAmount();
                    }
                }
            }

            for (HouseMember houseMember : houseMemberListSpouse) { // 배우자 세대 조회
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getProperty().equals(Property.건물) || houseMemberProperty.getProperty().equals(Property.토지)) { //해당 세대가 소유하고 있는 부동산(건물+토지)이 215,500천원 초과일 경우 false
                        sumEstateProperty += houseMemberProperty.getAmount();
                    } else if (houseMemberProperty.getProperty().equals(Property.자동차)) { //해당 세대가 소유하고 있는 자동차가 34,960천원 초과일 경우 false
                        sumCarProperty += houseMemberProperty.getAmount();
                    }
                }
            }
        }

        System.out.println("부동산자산합 : " + sumEstateProperty);
        System.out.println("자동차자산합 : " + sumCarProperty);

        if (sumEstateProperty > 215500000 || sumCarProperty > 34960000) //부동산 or 자동차 둘 중에 하나라도 자산기준 초과일 경우 false
            return false;
        else  //그 외에는 true
            return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean secondChungyak(User user) {
        List<HouseMember> houseMemberListUser = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse()); //신청자의 세대구성원 가져오기

        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMember houseMember : houseMemberListUser) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getDispositionDate() != null) {
                        if (user.getHouseMember().getMarriageDate().isBefore(houseMemberProperty.getDispositionDate())) //혼인신고일 이후에 처분일 이력이 있고,
                            if (houseMemberProperty.getDispositionDate().isBefore(LocalDate.of(2018, 12, 10))) // 2018년 12월 10일 이후에 처분했을 경우,
                                return true; // true(2순위 신청만 가능)
                    }
                }
            }
        }
        // 배우자 분리세대일 경우
        else {
            List<HouseMember> houseMemberListSpouse = houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse()); //배우자분리세대일 경우, 배우자의 세대구성원 가져오기

            for (HouseMember houseMember : houseMemberListUser) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (user.getHouseMember().getMarriageDate().isBefore(houseMemberProperty.getDispositionDate())) //혼인신고일 이후에 처분일 이력이 있고,
                        if (houseMemberProperty.getDispositionDate().isAfter(LocalDate.of(2018, 12, 10))) // 2018년 12월 10일 이후에 처분했을 경우,
                            return true; // true(2순위 신청만 가능)
                }
            }

            for (HouseMember houseMember : houseMemberListSpouse) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (user.getHouseMember().getMarriageDate().isBefore(houseMemberProperty.getDispositionDate())) //혼인신고일 이후에 처분일 이력이 있고,
                        if (houseMemberProperty.getDispositionDate().isAfter(LocalDate.of(2018, 12, 10))) // 2018년 12월 10일 이후에 처분했을 경우,
                            return true; // true(2순위 신청만 가능)
                }
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetHomelessHouseholdMembers(User user) { //전세대원무주택세대구성원충족여부 메소드
        List<HouseMember> houseMemberListUser = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse()); //신청자의 세대구성원 가져오기

        int houseCount = 0;

        //배우자와 같은 세대이거나, 미혼일 경우
        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMember houseMember : houseMemberListUser) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                int flag = 0;
                int specialCase = 0; // 예외주택에 해당하는 경우를 count 하기 위해 specialCase라는 변수에 0으로 초기 세팅

                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) { // 반복문을 통해서 세대구성원의 자산 정보 List를 돌면서,
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//소유주택이 주거용이면
                        HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                        if ((houseMemberRelation.getRelation().getRelation().equals(Relation.부) || houseMemberRelation.getRelation().getRelation().equals(Relation.모) || houseMemberRelation.getRelation().getRelation().equals(Relation.조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.조모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조모))) { // 신청자와의 관계가 부, 모, 조부, 조모, 배우자의부, 배우자의모, 배우자의조부, 배우자의조모일 경우
                            if (calcAmericanAge(houseMember.getBirthDay()) >= 60) { // 만나이가 60세 이상일 경우 specialCase 증가시킴
                                specialCase++;
                                continue;
                            }
                        }
                        if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.오피스텔)) { // 주거용건물유형이 오피스텥일 경우 specialCase 증가
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) { // 2018.12.11 이전에 취득한 분양권일 경우 specialCase 증가
                            specialCase++;
                            continue;
                        } else if (!(houseMemberProperty.getDispositionDate() == null)) { // 주택 처분일이 있을 경우
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getExceptionHouseYn().equals(Yn.y)) { // 주택예외사항해당여부에 해당하는 경우 specialCase 증가
                            specialCase++;
                            continue;
                        } else {
                            if (houseMemberProperty.getExclusiveArea() <= 20) { // 20제곱미터 이하의 주택을 소유하고 있는 경우
                                flag++;
                                if (specialCase <= 0) // 만약, specialCase가 없을 경우 continue
                                    continue;
                                else // 그렇지 않으면, flag 값을 houseCount에 넣음
                                    houseCount = flag;
                                if (flag <= 1) // 단, 2호 또는 2세대 이상의 주택 또는 분양권은 제외. 즉, 하나까진 count 안 한다는 의미.
                                    continue;
                                else
                                    houseCount = flag;
                            }
                        }
                        houseCount++; // 그 외 주택은 houseCount 값 증가시킴
                    }
                }
            }
        }
        //배우자 분리세대일 경우
        else {
            List<HouseMember> spouseHouseMemberList = houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse()); // 신청자의 배우자의 전세대구성원의 자산 정보를 List로 가져옴

            for (HouseMember houseMember : houseMemberListUser) { // 반복문을 통해서 신청자의 세대구성원 List를 돔
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                int flag = 0;
                int specialCase = 0;

                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//소유주택이 주거용이면
                        HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                        if ((houseMemberRelation.getRelation().getRelation().equals(Relation.부) || houseMemberRelation.getRelation().getRelation().equals(Relation.모) || houseMemberRelation.getRelation().getRelation().equals(Relation.조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.조모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조모))) { // 신청자와의 관계가 부, 모, 조부, 조모, 배우자의부, 배우자의모, 배우자의조부, 배우자의조모일 경우
                            if (calcAmericanAge(houseMember.getBirthDay()) >= 60) {
                                specialCase++;
                                continue;
                            }
                        }
                        if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.오피스텔)) { //주거용건물유형이 오피스텥일 경우
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) { //2018.12.11 이전에 취득한 분양권일 경우
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getExceptionHouseYn().equals(Yn.y)) {
                            specialCase++;
                            continue;
                        } else if (!(houseMemberProperty.getDispositionDate() == null)) { // 주택 처분일이 있을 경우
                            specialCase++;
                            continue;
                        } else {
                            if (houseMemberProperty.getExclusiveArea() <= 20) { //20제곱미터 이하의 주택을 소유하고 있는 경우
                                flag++;
                                if (specialCase <= 0)
                                    continue;
                                else
                                    houseCount = flag;
                                if (flag <= 1) // 단, 2호 또는 2세대 이상의 주택 또는 분양권은 제외. 즉, 하나까진 count 안 한다는 의미.
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
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//소유주택이 주거용이면
                        HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();
                        if ((houseMemberRelation.getRelation().getRelation().equals(Relation.부) || houseMemberRelation.getRelation().getRelation().equals(Relation.모) || houseMemberRelation.getRelation().getRelation().equals(Relation.조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.조모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조모))) {
                            if (calcAmericanAge(houseMember.getBirthDay()) >= 60) { // 만나이가 60세 이상일 경우 specialCase 증가시킴
                                specialCase++;
                                continue;
                            }
                        }
                        if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.오피스텔)) { // 주거용건물유형이 오피스텥일 경우 specialCase 증가
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) { // 2018.12.11 이전에 취득한 분양권일 경우 specialCase 증가
                            specialCase++;
                            continue;
                        } else if (!(houseMemberProperty.getDispositionDate() == null)) { // 주택 처분일이 있을 경우
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getExceptionHouseYn().equals(Yn.y)) { // 주택예외사항해당여부에 해당하는 경우 specialCase 증가
                            specialCase++;
                            continue;
                        } else {
                            if (houseMemberProperty.getExclusiveArea() <= 20) { //20제곱미터 이하의 주택을 소유하고 있는 경우
                                flag++;
                                if (specialCase <= 0) // 만약, specialCase가 없을 경우 continue
                                    continue;
                                else // 그렇지 않으면, flag 값을 houseCount에 넣음
                                    houseCount = flag;
                                if (flag <= 1) // 단, 2호 또는 2세대 이상의 주택 또는 분양권은 제외. 즉, 하나까진 count 안 한다는 의미.
                                    continue;
                                else
                                    houseCount = flag;
                            }
                        }
                        houseCount++; // 그 외 주택은 houseCount 값 증가시킴
                    }
                }
            }
        }

        if (houseCount == 0) // 주택수가 0일 경우 무주택세대구성원으로 판별
            return true;
        else // 아닐 경우 유주택세대구성원으로 판별, 탈락
            return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isHouseholder(User user) { // 세대주여부 메소드
        if (user.getHouse().getHouseHolder() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_HOLDER); //세대주 지정이 안되어있을 경우 경고를 띄움.
        } else if (user.getHouse().getHouseHolder().getId().equals(user.getHouseMember().getId())) { // 사용자의 세대의 세대주 id가 사용자의 세대구성원id와 같으면 true
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetLivingInSurroundArea(User user, AptInfo aptInfo) { //인근지역거주조건충족여부 메소드
        if (user.getHouseMember() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER); // 세대구성원->세대를 통해서 주소를 user의 지역_레벨1을 가져오는 것이기 때문에 user의 세대구성원이 비어있으면 안됨.
        }

        com.hanium.chungyakpassback.entity.standard.AddressLevel1 userAddressLevel1 = Optional.ofNullable(user.getHouseMember().getHouse().getAddressLevel1()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS_LEVEL1));
        com.hanium.chungyakpassback.entity.standard.AddressLevel1 aptAddressLevel1 = addressLevel1Repository.findByAddressLevel1(aptInfo.getAddressLevel1()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS_LEVEL1));

        if (userAddressLevel1.getNearbyArea() == aptAddressLevel1.getNearbyArea()) { // 아파트 분양정보의 인근지역과 거주지의 인근지역이 같다면 true
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isRestrictedArea(AptInfo aptInfo) { // 규제지역여부 메소드
        if (aptInfo.getSpeculationOverheated().equals(Yn.y) || aptInfo.getSubscriptionOverheated().equals(Yn.y)) // 사용자가 선택한 아파트분양정보가 투기과열지구 또는 청약과열지역에 해당하는 경우 true
            return true;
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetBankbookJoinPeriod(User user, AptInfo aptInfo) { //가입기간충족여부확인
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        if (optUserBankbook.isEmpty())
            throw new RuntimeException("등록된 청약통장이 없습니다.");
        UserBankbook userBankbook = optUserBankbook.get();

        LocalDate joinDate = userBankbook.getJoinDate();
        LocalDate now = LocalDate.now();
        Period period = joinDate.until(now);
        int joinPeriod = period.getYears() * 12 + period.getMonths(); // 가입날짜를 받아와서 현재까지의 개월수를 계산

        List<PriorityJoinPeriod> priorityJoinPeriodList = priorityJoinPeriodRepository.findAll();

        for (PriorityJoinPeriod priorityJoinPeriod : priorityJoinPeriodList) {
            if (priorityJoinPeriod.getSupply().equals(Supply.특별공급) && priorityJoinPeriod.getSpecialSupply().equals(SpecialSupply.신혼부부)) {
                if (priorityJoinPeriod.getSpeculationOverheated().equals(aptInfo.getSpeculationOverheated()) && priorityJoinPeriod.getSubscriptionOverheated().equals(aptInfo.getSubscriptionOverheated()) && priorityJoinPeriod.getAtrophyArea().equals(aptInfo.getAtrophyArea()) && priorityJoinPeriod.getMetropolitanAreaYn().equals(addressLevel1Repository.findByAddressLevel1(aptInfo.getAddressLevel1()).get().getMetropolitanAreaYn())) {
                    if (joinPeriod >= priorityJoinPeriod.getSubscriptionPeriod())
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetNumberOfPayments(User user, AptInfo aptInfo) { //납입횟수충족여부확인
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        if (optUserBankbook.isEmpty())
            throw new RuntimeException("등록된 청약통장이 없습니다.");
        UserBankbook userBankbook = optUserBankbook.get();

        List<PriorityPaymentsCount> priorityPaymentsCountList = priorityPaymentsCountRepository.findAll();

        for (PriorityPaymentsCount priorityPaymentsCount : priorityPaymentsCountList) {
            if (priorityPaymentsCount.getSupply().equals(Supply.특별공급) && priorityPaymentsCount.getSpecialSupply().equals(SpecialSupply.신혼부부)) { // 청약통장이 유효하면서 공급유형이 신혼부부인 경우,
                if (priorityPaymentsCount.getSpeculationOverheated().equals(aptInfo.getSpeculationOverheated()) && priorityPaymentsCount.getSubscriptionOverheated().equals(aptInfo.getSubscriptionOverheated()) && priorityPaymentsCount.getAtrophyArea().equals(aptInfo.getAtrophyArea()) && priorityPaymentsCount.getMetropolitanAreaYn().equals(addressLevel1Repository.findByAddressLevel1(aptInfo.getAddressLevel1()).get().getMetropolitanAreaYn())) {
                    if (userBankbook.getPaymentsCount() >= priorityPaymentsCount.getCountPayments())
                        return true;
                }
            }
        }

        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetAllHouseMemberRewinningRestriction(User user) { //전세대원재당첨제한여부
        LocalDate now = LocalDate.now(); //현재 날짜 가져오기

        List<HouseMemberChungyak> houseMemberListUser = houseMemberChungyakRepository.findAllByHouseMember(user.getHouseMember());

        //배우자와 같은 세대이거나, 미혼일 경우
        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMemberChungyak houseMemberChungyak : houseMemberListUser) {
                List<HouseMemberChungyakRestriction> houseMemberChungyakRestrictionList = houseMemberChungyakRestrictionRepository.findAllByHouseMemberChungyak(houseMemberChungyak);

                for (HouseMemberChungyakRestriction houseMemberChungyakRestriction : houseMemberChungyakRestrictionList) {
                    if (!(houseMemberChungyakRestriction.getReWinningRestrictedDate() == null)) { // 재당첨제한 날짜가 있는 세대구성원만 조회
                        if (houseMemberChungyakRestriction.getReWinningRestrictedDate().isAfter(now)) { // 현재 날짜와 비교하여 재당첨제한 기간이 끝났는지 판단
                            return false;
                        }
                    }
                }
            }
        }
        //배우자 분리세대일 경우
        else {
            List<HouseMemberChungyak> spouseHouseMemberListUser = houseMemberChungyakRepository.findAllByHouseMember(user.getSpouseHouseMember());

            for (HouseMemberChungyak houseMemberChungyak : houseMemberListUser) {
                List<HouseMemberChungyakRestriction> houseMemberChungyakRestrictionList = houseMemberChungyakRestrictionRepository.findAllByHouseMemberChungyak(houseMemberChungyak);

                for (HouseMemberChungyakRestriction houseMemberChungyakRestriction : houseMemberChungyakRestrictionList) {
                    if (!(houseMemberChungyakRestriction.getReWinningRestrictedDate() == null)) { // 재당첨제한 날짜가 있는 세대구성원만 조회
                        if (houseMemberChungyakRestriction.getReWinningRestrictedDate().isAfter(now)) { // 현재 날짜와 비교하여 재당첨제한 기간이 끝났는지 판단
                            return false;
                        }
                    }
                }
            }

            for (HouseMemberChungyak houseMemberChungyak : spouseHouseMemberListUser) {
                List<HouseMemberChungyakRestriction> houseMemberChungyakRestrictionList = houseMemberChungyakRestrictionRepository.findAllByHouseMemberChungyak(houseMemberChungyak);

                for (HouseMemberChungyakRestriction houseMemberChungyakRestriction : houseMemberChungyakRestrictionList) {
                    if (!(houseMemberChungyakRestriction.getReWinningRestrictedDate() == null)) { // 재당첨제한 날짜가 있는 세대구성원만 조회
                        if (houseMemberChungyakRestriction.getReWinningRestrictedDate().isAfter(now)) { // 현재 날짜와 비교하여 재당첨제한 기간이 끝났는지 판단
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}

