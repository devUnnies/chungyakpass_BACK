package com.hanium.chungyakpassback.service.verification;

import com.hanium.chungyakpassback.dto.verification.*;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.apt.AptInfoTarget;
import com.hanium.chungyakpassback.entity.input.*;
import com.hanium.chungyakpassback.entity.verification.VerificationOfSpecialMinyeongOldParent;
import com.hanium.chungyakpassback.entity.standard.AddressLevel1;
import com.hanium.chungyakpassback.entity.standard.PriorityDeposit;
import com.hanium.chungyakpassback.entity.standard.PriorityJoinPeriod;
import com.hanium.chungyakpassback.enumtype.*;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.apt.AptInfoTargetRepository;
import com.hanium.chungyakpassback.repository.input.*;
import com.hanium.chungyakpassback.repository.verification.VerificationOfSpecialMinyeongOldParentRepository;
import com.hanium.chungyakpassback.repository.standard.AddressLevel1Repository;
import com.hanium.chungyakpassback.repository.standard.BankbookRepository;
import com.hanium.chungyakpassback.repository.standard.PriorityDepositRepository;
import com.hanium.chungyakpassback.repository.standard.PriorityJoinPeriodRepository;
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
public class VerificationOfSpecialMinyeongOldParentServiceImpl implements VerificationOfSpecialMinyeongOldParentService {

    final AddressLevel1Repository addressLevel1Repository;
    final UserBankbookRepository userBankbookRepository;
    final HouseMemberRepository houseMemberRepository;
    final HouseMemberPropertyRepository houseMemberPropertyRepository;
    final HouseMemberRelationRepository houseMemberRelationRepository;
    final HouseMemberChungyakRepository houseMemberChungyakRepository;
    final PriorityDepositRepository priorityDepositRepository;
    final PriorityJoinPeriodRepository priorityJoinPeriodRepository;
    final BankbookRepository bankbookRepository;
    final HouseMemberChungyakRestrictionRepository houseMemberChungyakRestrictionRepository;
    final UserRepository userRepository;
    final AptInfoTargetRepository aptInfoTargetRepository;
    final AptInfoRepository aptInfoRepository;
    final VerificationOfSpecialMinyeongOldParentRepository verificationOfSpecialMinyeongOldParentRepository;

    @Override //???????????????????????????
    public List<VerificationOfSpecialMinyeongOldParentResponseDto> readSpecialMinyeongOldParentVerifications() {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();

        List<VerificationOfSpecialMinyeongOldParentResponseDto> specialMinyeongOldParentResponseDtos = new ArrayList<>();
        for (VerificationOfSpecialMinyeongOldParent verificationOfSpecialMinyeongOldParent : verificationOfSpecialMinyeongOldParentRepository.findAllByUser(user)) {
            VerificationOfSpecialMinyeongOldParentResponseDto verificationOfSpecialMinyeongOldParentResponseDto = new VerificationOfSpecialMinyeongOldParentResponseDto(verificationOfSpecialMinyeongOldParent);
            specialMinyeongOldParentResponseDtos.add(verificationOfSpecialMinyeongOldParentResponseDto);
        }

        return specialMinyeongOldParentResponseDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VerificationOfSpecialMinyeongOldParentResponseDto createSpecialMinyeongOldParentVerification(VerificationOfSpecialMinyeongOldParentDto verificationOfSpecialMinyeongOldParentDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(verificationOfSpecialMinyeongOldParentDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(verificationOfSpecialMinyeongOldParentDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetOldParentSupportMore3yearsTf = meetOldParentSupportMore3years(user);
        boolean meetHomelessHouseholdMembersTf = meetHomelessHouseholdMembers(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = meetAllHouseMemberRewinningRestriction(user);
        boolean householderTf = isHouseholder(user);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = meetAllHouseMemberNotWinningIn5years(user);
        boolean isRestrictedAreaTf = isRestrictedArea(aptInfo);
        boolean meetBankbookJoinPeriodTf = meetBankbookJoinPeriod(user, aptInfo);
        boolean meetDepositTf = meetDeposit(user, aptInfoTarget);

        VerificationOfSpecialMinyeongOldParent verificationOfSpecialMinyeongOldParent = new VerificationOfSpecialMinyeongOldParent(user, americanAge, meetLivingInSurroundAreaTf, accountTf, meetOldParentSupportMore3yearsTf, meetHomelessHouseholdMembersTf, householderTf, isRestrictedAreaTf, meetAllHouseMemberNotWinningIn5yearsTf, meetAllHouseMemberRewinningRestrictionTf, meetBankbookJoinPeriodTf, meetDepositTf, aptInfo, aptInfoTarget);
        verificationOfSpecialMinyeongOldParentRepository.save(verificationOfSpecialMinyeongOldParent);
        return new VerificationOfSpecialMinyeongOldParentResponseDto(verificationOfSpecialMinyeongOldParent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VerificationOfSpecialMinyeongOldParentResponseDto updateSpecialMinyeongOldParentVerification(Long id, VerificationOfSpecialMinyeongOldParentUpdateDto verificationOfSpecialMinyeongOldParentUpdateDto) {
        VerificationOfSpecialMinyeongOldParent verificationOfSpecialMinyeongOldParent = verificationOfSpecialMinyeongOldParentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VERIFICATION_RECORD_ID));
        verificationOfSpecialMinyeongOldParent.setSibilingSupportYn(verificationOfSpecialMinyeongOldParentUpdateDto.getSibilingSupportYn());
        verificationOfSpecialMinyeongOldParent.setRanking(verificationOfSpecialMinyeongOldParentUpdateDto.getRanking());
        verificationOfSpecialMinyeongOldParentRepository.save(verificationOfSpecialMinyeongOldParent);
        return new VerificationOfSpecialMinyeongOldParentResponseDto(verificationOfSpecialMinyeongOldParent);
    }

    public int houseTypeConverter(AptInfoTarget aptInfoTarget) { // . ???????????? ????????? ????????? ?????? ????????? ????????? int ????????? ?????????
        String housingTypeChange = aptInfoTarget.getHousingType().substring(0, aptInfoTarget.getHousingType().indexOf("."));

        return Integer.parseInt(housingTypeChange);
    }

    public Long calcDate(LocalDate transferdate) { //?????????????????? ????????? ?????? ?????????
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime departure = transferdate.atStartOfDay();

        Long days = Duration.between(departure, today).toDays();

        return days;
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
    public boolean meetOldParentSupportMore3years(User user) { //3????????????????????????????????????
        List<HouseMember> houseMemberListUser = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse());

        int oldParentSupportCount = 0;

        for (HouseMember houseMember : houseMemberListUser) {
            HouseMemberRelation houseMemberRelation = houseMemberRelationRepository.findByUserAndOpponent(user, houseMember).get();

            if (user.getHouse() == houseMember.getHouse()) { // ???????????? ?????? ???????????? ?????? ???,
                if (!(houseMember.getBirthDay() == null) && calcAmericanAge(houseMember.getBirthDay()) >= 65 && ((houseMemberRelation.getRelation().getRelation().equals(Relation.???) || houseMemberRelation.getRelation().getRelation().equals(Relation.???) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????) || houseMemberRelation.getRelation().getRelation().equals(Relation.??????) || houseMemberRelation.getRelation().getRelation().equals(Relation.???????????????) || houseMemberRelation.getRelation().getRelation().equals(Relation.???????????????)))) { // ??? 65??? ????????? ????????????(???????????? ????????????)?????? ?????? ???,
                    if (calcDate(houseMember.getTransferDate()) >= 1095 && calcDate(user.getHouseMember().getTransferDate()) >= 1095) { //???????????? ???????????? ??? ??? 3??? ?????? ????????? ????????? ??????
                        oldParentSupportCount++;
                    }
                }
            }
        }

        System.out.println("?????????????????? : " + oldParentSupportCount);

        if (oldParentSupportCount >= 1) // 1??? ????????? ????????? ????????? ??? ?????? true
            return true;
        else // ????????? ????????? false
            return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetHomelessHouseholdMembers(User user) { //????????????????????????????????????????????????
        List<HouseMember> houseMemberListUser = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse()); //???????????? ??????????????? ????????????

        int houseCount = 0;

        //???????????? ?????? ???????????????, ????????? ??????
        if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) {
            for (HouseMember houseMember : houseMemberListUser) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                int flag = 0;
                int specialCase = 0;
                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//??????????????? ???????????????
                        if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.????????????)) { //???????????????????????? ??????????????? ??????
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) { //2018.12.11 ????????? ????????? ???????????? ??????
                            specialCase++;
                            continue;
                        } else if (!(houseMemberProperty.getDispositionDate() == null)) {
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getExceptionHouseYn().equals(Yn.y)) {
                            specialCase++;
                            continue;
                        } else {
                            if (houseMemberProperty.getExclusiveArea() <= 60) { //60???????????? ????????? ????????? ???????????? ?????? ??????
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
            List<HouseMember> spouseHouseMemberList = houseMemberRepository.findAllByHouse(user.getSpouseHouseMember().getHouse());

            for (HouseMember houseMember : houseMemberListUser) {
                List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

                int flag = 0;
                int specialCase = 0;
                for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
                    if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//??????????????? ???????????????
                        if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.????????????)) { //???????????????????????? ??????????????? ??????
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) { //2018.12.11 ????????? ????????? ???????????? ??????
                            specialCase++;
                            continue;
                        } else if (!(houseMemberProperty.getDispositionDate() == null)) {
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getExceptionHouseYn().equals(Yn.y)) {
                            specialCase++;
                            continue;
                        } else {
                            if (houseMemberProperty.getExclusiveArea() <= 60) { //60???????????? ????????? ????????? ???????????? ?????? ??????
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
                        if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.????????????)) { //???????????????????????? ??????????????? ??????
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) { //2018.12.11 ????????? ????????? ???????????? ??????
                            specialCase++;
                            continue;
                        } else if (!(houseMemberProperty.getDispositionDate() == null)) {
                            specialCase++;
                            continue;
                        } else if (houseMemberProperty.getExceptionHouseYn().equals(Yn.y)) {
                            specialCase++;
                            continue;
                        } else {
                            if (houseMemberProperty.getExclusiveArea() <= 60) { //60???????????? ????????? ????????? ???????????? ?????? ??????
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

        System.out.println("????????? :" + houseCount);

        if (houseCount == 0) // ???????????? 0??? ?????? ?????????????????????????????? ??????
            return true;
        else // ?????? ?????? ?????????????????????????????? ??????, ??????
            return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isHouseholder(User user) {
        if (user.getHouse().getHouseHolder() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSEHOLDER); //????????? ????????? ??????????????? ?????? ????????? ??????.
        } else if (user.getHouse().getHouseHolder().getId().equals(user.getHouseMember().getId())) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetLivingInSurroundArea(User user, AptInfo aptInfo) {//????????? ??????????????? ??????????????? ???????????? ??????????????? ?????????
        AddressLevel1 userAddressLevel1 = Optional.ofNullable(user.getHouseMember().getHouse().getAddressLevel1()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS_LEVEL1));
        AddressLevel1 aptAddressLevel1 = addressLevel1Repository.findByAddressLevel1(aptInfo.getAddressLevel1()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADDRESS_LEVEL1));

        if (userAddressLevel1.getNearbyArea() == aptAddressLevel1.getNearbyArea())
            return true;
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isRestrictedArea(AptInfo aptInfo) { // ??????????????????
        if (aptInfo.getSpeculationOverheated().equals(Yn.y) || aptInfo.getSubscriptionOverheated().equals(Yn.y))
            return true;
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean meetBankbookJoinPeriod(User user, AptInfo aptInfo) { //??????????????????????????????
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        if (optUserBankbook.isEmpty())
            throw new RuntimeException("????????? ??????????????? ????????????.");
        UserBankbook userBankbook = optUserBankbook.get();

        LocalDate joinDate = userBankbook.getJoinDate();
        LocalDate now = LocalDate.now();
        Period period = joinDate.until(now);
        int joinPeriod = period.getYears() * 12 + period.getMonths(); // ??????????????? ???????????? ??????????????? ???????????? ??????

        List<PriorityJoinPeriod> priorityJoinPeriodList = priorityJoinPeriodRepository.findAll();

        for (PriorityJoinPeriod priorityJoinPeriod : priorityJoinPeriodList) {
            if (priorityJoinPeriod.getSupply().equals(Supply.????????????) && priorityJoinPeriod.getSpecialSupply().equals(SpecialSupply.???????????????)) {
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
