package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.verification.*;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.apt.AptInfoTarget;
import com.hanium.chungyakpassback.entity.input.HouseMember;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.enumtype.ErrorCode;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.apt.AptInfoTargetRepository;
import com.hanium.chungyakpassback.repository.input.UserRepository;
import com.hanium.chungyakpassback.service.verification.*;
import com.hanium.chungyakpassback.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/verification")
public class VerificationController {
    private final UserRepository userRepository;
    private final AptInfoRepository aptInfoRepository;
    private final AptInfoTargetRepository aptInfoTargetRepository;
    private final GeneralPrivateVerificationService generalPrivateVerificationService;
    private final GeneralKookminVerificationService generalKookminVerificationService;
    private final SpecialPrivateMultiChildVerificationService specialPrivateMultiChildVerificationService;
    private final SpecialKookminPublicMultiChildVerificationService specialKookminPublicMultiChildVerificationService;
    private final SpecialPrivateOldParentVerificationService specialPrivateOldParentVerificationService;
    private final SpecialKookminPublicOldParentVerificationService specialKookminPublicOldParentVerificationService;
    private final SpecialPrivateNewlyMarriedVerificationService specialPrivateNewlyMarriedVerificationService;
    private final SpecialKookminNewlyMarriedVerificationService specialKookminNewlyMarriedVerificationService;
    private final SpecialKookminPublicNewlyMarriedVerificationService specialKookminPublicNewlyMarriedVerificationService;
    private final SpecialPrivateFirstLifeVerificationService specialPrivateFirstLifeVerificationService;
    private final SpecialKookminPublicFirstLifeVerificationService specialKookminPublicFirstLifeVerificationService;

    public VerificationController(UserRepository userRepository, AptInfoRepository aptInfoRepository, AptInfoTargetRepository aptInfoTargetRepository, GeneralPrivateVerificationService generalPrivateVerificationService, GeneralKookminVerificationService generalKookminVerificationService, SpecialPrivateMultiChildVerificationService specialPrivateMultiChildVerificationService, SpecialKookminPublicMultiChildVerificationService specialKookminPublicMultiChildVerificationService, SpecialPrivateOldParentVerificationService specialPrivateOldParentVerificationService, SpecialKookminPublicOldParentVerificationService specialKookminPublicOldParentVerificationService, SpecialPrivateNewlyMarriedVerificationService specialPrivateNewlyMarriedVerificationService, SpecialKookminNewlyMarriedVerificationService specialKookminNewlyMarriedVerificationService, SpecialKookminPublicNewlyMarriedVerificationService specialKookminPublicNewlyMarriedVerificationService, SpecialPrivateFirstLifeVerificationService specialPrivateFirstLifeVerificationService, SpecialKookminPublicFirstLifeVerificationService specialKookminPublicFirstLifeVerificationService) {
        this.userRepository = userRepository;
        this.aptInfoRepository = aptInfoRepository;
        this.aptInfoTargetRepository = aptInfoTargetRepository;
        this.generalPrivateVerificationService = generalPrivateVerificationService;
        this.generalKookminVerificationService = generalKookminVerificationService;
        this.specialPrivateMultiChildVerificationService = specialPrivateMultiChildVerificationService;
        this.specialKookminPublicMultiChildVerificationService = specialKookminPublicMultiChildVerificationService;
        this.specialPrivateOldParentVerificationService = specialPrivateOldParentVerificationService;
        this.specialKookminPublicOldParentVerificationService = specialKookminPublicOldParentVerificationService;
        this.specialPrivateNewlyMarriedVerificationService = specialPrivateNewlyMarriedVerificationService;
        this.specialKookminNewlyMarriedVerificationService = specialKookminNewlyMarriedVerificationService;
        this.specialKookminPublicNewlyMarriedVerificationService = specialKookminPublicNewlyMarriedVerificationService;
        this.specialPrivateFirstLifeVerificationService = specialPrivateFirstLifeVerificationService;
        this.specialKookminPublicFirstLifeVerificationService = specialKookminPublicFirstLifeVerificationService;
    }


    @PostMapping("/general/minyeong")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GeneralMinyeongResponseDto> generalMinyeong(@RequestBody GeneralMinyeongDto generalMinyeongDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(generalMinyeongDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(generalMinyeongDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        boolean meetLivingInSurroundAreaTf = generalPrivateVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = generalPrivateVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        Integer americanAge = generalPrivateVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean householderTf = generalPrivateVerificationService.isHouseholder(user);
        boolean isRestrictedAreaTf = generalPrivateVerificationService.isRestrictedArea(aptInfo);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = generalPrivateVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = generalPrivateVerificationService.meetAllHouseMemberRewinningRestriction(user);
        boolean meetHouseHavingLessThan2AptTf = generalPrivateVerificationService.meetHouseHavingLessThan2Apt(user);
        boolean meetBankbookJoinPeriodTf = generalPrivateVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetDepositTf = generalPrivateVerificationService.meetDeposit(user, aptInfoTarget);
        boolean isPriorityApt = generalPrivateVerificationService.isPriorityApt(aptInfo, aptInfoTarget);


        return new ResponseEntity<>(new GeneralMinyeongResponseDto(meetLivingInSurroundAreaTf, accountTf, americanAge, householderTf, isRestrictedAreaTf, meetAllHouseMemberNotWinningIn5yearsTf, meetAllHouseMemberRewinningRestrictionTf, meetHouseHavingLessThan2AptTf, meetBankbookJoinPeriodTf, meetDepositTf, isPriorityApt), HttpStatus.OK);
    }

    @PostMapping("/general/kookmin") //일반국민
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GeneralKookminResponseDto> generalKookmin(@RequestBody GeneralKookminDto generalKookminDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(generalKookminDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(generalKookminDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = generalKookminVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = specialPrivateMultiChildVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = generalKookminVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetHomelessHouseholdMembersTf = generalKookminVerificationService.meetHomelessHouseholdMembers(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = generalKookminVerificationService.meetAllHouseMemberRewinningRestriction(user);
        boolean householderTf = generalKookminVerificationService.isHouseholder(user);
        boolean isRestrictedAreaTf = generalKookminVerificationService.isRestrictedArea(aptInfo);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = generalKookminVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean meetBankbookJoinPeriodTf = generalKookminVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetNumberOfPaymentsTf = generalKookminVerificationService.meetNumberOfPayments(user, aptInfo);

        return new ResponseEntity<>(new GeneralKookminResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetHomelessHouseholdMembersTf, meetAllHouseMemberRewinningRestrictionTf, householderTf, isRestrictedAreaTf, meetAllHouseMemberNotWinningIn5yearsTf, meetBankbookJoinPeriodTf, meetNumberOfPaymentsTf), HttpStatus.OK);
    }

    @PostMapping("/special/minyeong/multichild") //특별다자녀민영
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialMinyeongMultiChildResponseDto> specialMultiChild(@RequestBody SpecialMinyeongMultiChildDto specialMinyeongMultiChildDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(specialMinyeongMultiChildDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(specialMinyeongMultiChildDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = specialPrivateMultiChildVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = specialPrivateMultiChildVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = specialPrivateMultiChildVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetHomelessHouseholdMembersTf = specialPrivateMultiChildVerificationService.meetHomelessHouseholdMembers(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = specialPrivateMultiChildVerificationService.meetAllHouseMemberRewinningRestriction(user);
        Integer calcMinorChildren = specialPrivateMultiChildVerificationService.calcMinorChildren(user);
        boolean householderTf = specialPrivateMultiChildVerificationService.isHouseholder(user);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = specialPrivateMultiChildVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean isRestrictedAreaTf = specialPrivateMultiChildVerificationService.isRestrictedArea(aptInfo);
        boolean meetHouseHavingLessThan2Apt = specialPrivateMultiChildVerificationService.meetHouseHavingLessThan2Apt(user);
        boolean isPriorityApt = specialPrivateMultiChildVerificationService.isPriorityApt(aptInfo, aptInfoTarget);
        boolean meetDepositTf = specialPrivateMultiChildVerificationService.meetDeposit(user, aptInfoTarget);
        boolean meetBankbookJoinPeriodTf = specialPrivateMultiChildVerificationService.meetBankbookJoinPeriod(user, aptInfo);

        return new ResponseEntity<>(new SpecialMinyeongMultiChildResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetHomelessHouseholdMembersTf, meetAllHouseMemberRewinningRestrictionTf, calcMinorChildren, householderTf, meetAllHouseMemberNotWinningIn5yearsTf, isRestrictedAreaTf, meetHouseHavingLessThan2Apt, isPriorityApt, meetDepositTf, meetBankbookJoinPeriodTf), HttpStatus.OK);
    }

    @PostMapping("/special/kookmin/public/multichild") //특별다자녀국민공공주택
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialKookminPublicMultiChildResponseDto> specialMultiChild(@RequestBody SpecialKookminPublicMultiChildDto specialKookminPublicMultiChildDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(specialKookminPublicMultiChildDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(specialKookminPublicMultiChildDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = specialKookminPublicMultiChildVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = specialKookminPublicMultiChildVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = specialKookminPublicMultiChildVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetMonthlyAverageIncomeTf = specialKookminPublicMultiChildVerificationService.meetMonthlyAverageIncome(user);
        boolean meetPropertyTf = specialKookminPublicMultiChildVerificationService.meetProperty(user);
        boolean meetHomelessHouseholdMembersTf = specialKookminPublicMultiChildVerificationService.meetHomelessHouseholdMembers(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = specialKookminPublicMultiChildVerificationService.meetAllHouseMemberRewinningRestriction(user);
        Integer calcMinorChildren = specialKookminPublicMultiChildVerificationService.calcMinorChildren(user);
        boolean householderTf = specialKookminPublicMultiChildVerificationService.isHouseholder(user);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = specialKookminPublicMultiChildVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean isRestrictedAreaTf = specialKookminPublicMultiChildVerificationService.isRestrictedArea(aptInfo);
        boolean meetBankbookJoinPeriodTf = specialKookminPublicMultiChildVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetNumberOfPaymentsTf = specialKookminPublicMultiChildVerificationService.meetNumberOfPayments(user, aptInfo);

        return new ResponseEntity<>(new SpecialKookminPublicMultiChildResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetMonthlyAverageIncomeTf, meetPropertyTf, meetHomelessHouseholdMembersTf, meetAllHouseMemberRewinningRestrictionTf, calcMinorChildren, householderTf, meetAllHouseMemberNotWinningIn5yearsTf, isRestrictedAreaTf, meetBankbookJoinPeriodTf, meetNumberOfPaymentsTf), HttpStatus.OK);
    }

    @PostMapping("/special/minyeong/oldparent") //특별노부모민영
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialMinyeongOldParentResponseDto> specialOldParent(@RequestBody SpecialMinyeongOldParentDto specialMinyeongOldParentDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(specialMinyeongOldParentDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(specialMinyeongOldParentDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = specialPrivateOldParentVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = specialPrivateOldParentVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = specialPrivateOldParentVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetOldParentSupportMore3yearsTf = specialPrivateOldParentVerificationService.meetOldParentSupportMore3years(user);
        boolean meetHomelessHouseholdMembersTf = specialPrivateOldParentVerificationService.meetHomelessHouseholdMembers(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = specialPrivateOldParentVerificationService.meetAllHouseMemberRewinningRestriction(user);
        boolean householderTf = specialPrivateOldParentVerificationService.isHouseholder(user);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = specialPrivateOldParentVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean isRestrictedAreaTf = specialPrivateOldParentVerificationService.isRestrictedArea(aptInfo);
        boolean meetBankbookJoinPeriodTf = specialPrivateOldParentVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetDepositTf = specialPrivateOldParentVerificationService.meetDeposit(user, aptInfoTarget);

        return new ResponseEntity<>(new SpecialMinyeongOldParentResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetOldParentSupportMore3yearsTf, meetHomelessHouseholdMembersTf, meetAllHouseMemberRewinningRestrictionTf, householderTf, meetAllHouseMemberNotWinningIn5yearsTf, isRestrictedAreaTf, meetBankbookJoinPeriodTf, meetDepositTf), HttpStatus.OK);
    }

    @PostMapping("/special/kookmin/public/oldparent") //특별노부모국민공공주택
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialKookminPublicOldParentResponseDto> specialOldParent(@RequestBody SpecialKookminPublicOldParentDto specialKookminPublicOldParentDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(specialKookminPublicOldParentDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(specialKookminPublicOldParentDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = specialKookminPublicOldParentVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = specialKookminPublicOldParentVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = specialKookminPublicOldParentVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetMonthlyAverageIncome = specialKookminPublicOldParentVerificationService.meetMonthlyAverageIncome(user);
        boolean meetPropertyTf = specialKookminPublicOldParentVerificationService.meetProperty(user);
        boolean meetOldParentSupportMore3yearsTf = specialKookminPublicOldParentVerificationService.meetOldParentSupportMore3years(user);
        boolean meetHomelessHouseholdMembersTf = specialKookminPublicOldParentVerificationService.meetHomelessHouseholdMembers(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = specialKookminPublicOldParentVerificationService.meetAllHouseMemberRewinningRestriction(user);
        boolean householderTf = specialKookminPublicOldParentVerificationService.isHouseholder(user);
        boolean isRestrictedAreaTf = specialKookminPublicOldParentVerificationService.isRestrictedArea(aptInfo);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = specialKookminPublicOldParentVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean meetBankbookJoinPeriodTf = specialKookminPublicOldParentVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetNumberOfPaymentsTf = specialKookminPublicOldParentVerificationService.meetNumberOfPayments(user, aptInfo);

        return new ResponseEntity<>(new SpecialKookminPublicOldParentResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetMonthlyAverageIncome, meetPropertyTf, meetOldParentSupportMore3yearsTf, meetHomelessHouseholdMembersTf, meetAllHouseMemberRewinningRestrictionTf, householderTf, isRestrictedAreaTf, meetAllHouseMemberNotWinningIn5yearsTf, meetBankbookJoinPeriodTf, meetNumberOfPaymentsTf), HttpStatus.OK);
    }

    @PostMapping("/special/minyeong/newlymarried") //특별신혼부부민영
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialMinyeongNewlyMarriedResponseDto> specialNewlyMarried(@RequestBody SpecialMinyeongNewlyMarriedDto specialMinyeongNewlyMarriedDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(specialMinyeongNewlyMarriedDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(specialMinyeongNewlyMarriedDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = specialPrivateNewlyMarriedVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = specialPrivateNewlyMarriedVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = specialPrivateNewlyMarriedVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetMonthlyAverageIncomePriorityTf = specialPrivateNewlyMarriedVerificationService.meetMonthlyAverageIncomePriority(user);
        boolean meetMonthlyAverageIncomeGeneralTf = specialPrivateNewlyMarriedVerificationService.meetMonthlyAverageIncomeGeneral(user);
        boolean meetMarriagePeriodIn7yearsTf = specialPrivateNewlyMarriedVerificationService.meetMarriagePeriodIn7years(user);
        boolean hasMinorChildren = specialPrivateNewlyMarriedVerificationService.hasMinorChildren(user);
        boolean secondChungyak = specialPrivateNewlyMarriedVerificationService.secondChungyak(user);
        boolean meetHomelessHouseholdMembersTf = specialPrivateNewlyMarriedVerificationService.meetHomelessHouseholdMembers(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = specialPrivateNewlyMarriedVerificationService.meetAllHouseMemberRewinningRestriction(user);
        boolean householderTf = specialPrivateNewlyMarriedVerificationService.isHouseholder(user);
        boolean isRestrictedAreaTf = specialPrivateNewlyMarriedVerificationService.isRestrictedArea(aptInfo);
        boolean meetBankbookJoinPeriodTf = specialPrivateNewlyMarriedVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetDepositTf = specialPrivateNewlyMarriedVerificationService.meetDeposit(user, aptInfoTarget);

        return new ResponseEntity<>(new SpecialMinyeongNewlyMarriedResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetMonthlyAverageIncomePriorityTf, meetMonthlyAverageIncomeGeneralTf, meetMarriagePeriodIn7yearsTf, hasMinorChildren, secondChungyak, meetHomelessHouseholdMembersTf, meetAllHouseMemberRewinningRestrictionTf, householderTf, isRestrictedAreaTf, meetBankbookJoinPeriodTf, meetDepositTf), HttpStatus.OK);
    }

    @PostMapping("/special/kookmin/newlymarried") //특별신혼부부국민
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialKookminNewlyMarriedResponseDto> specialNewlyMarried(@RequestBody SpecialKookminNewlyMarriedDto specialKookminNewlyMarriedDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(specialKookminNewlyMarriedDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(specialKookminNewlyMarriedDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = specialKookminNewlyMarriedVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = specialKookminNewlyMarriedVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = specialKookminNewlyMarriedVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetMonthlyAverageIncomePriorityTf = specialKookminNewlyMarriedVerificationService.meetMonthlyAverageIncomePriority(user);
        boolean meetMonthlyAverageIncomeGeneralTf = specialKookminNewlyMarriedVerificationService.meetMonthlyAverageIncomeGeneral(user);
        boolean meetMarriagePeriodIn7yearsTf = specialKookminNewlyMarriedVerificationService.meetMarriagePeriodIn7years(user);
        boolean hasMinorChildren = specialKookminNewlyMarriedVerificationService.hasMinorChildren(user);
        boolean secondChungyak = specialKookminNewlyMarriedVerificationService.secondChungyak(user);
        boolean meetHomelessHouseholdMembersTf = specialKookminNewlyMarriedVerificationService.meetHomelessHouseholdMembers(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = specialKookminNewlyMarriedVerificationService.meetAllHouseMemberRewinningRestriction(user);
        boolean householderTf = specialKookminNewlyMarriedVerificationService.isHouseholder(user);
        boolean isRestrictedAreaTf = specialKookminNewlyMarriedVerificationService.isRestrictedArea(aptInfo);
        boolean meetBankbookJoinPeriodTf = specialKookminNewlyMarriedVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetNumberOfPaymentsTf = specialKookminNewlyMarriedVerificationService.meetNumberOfPayments(user, aptInfo);

        return new ResponseEntity<>(new SpecialKookminNewlyMarriedResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetMonthlyAverageIncomePriorityTf, meetMonthlyAverageIncomeGeneralTf, meetMarriagePeriodIn7yearsTf, hasMinorChildren, secondChungyak, meetHomelessHouseholdMembersTf, meetAllHouseMemberRewinningRestrictionTf, householderTf, isRestrictedAreaTf, meetBankbookJoinPeriodTf, meetNumberOfPaymentsTf), HttpStatus.OK);
    }

    @PostMapping("/special/kookmin/public/newlymarried") //특별신혼부부국민공공주택
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialKookminPublicNewlyMarriedResponseDto> specialNewlyMarried(@RequestBody SpecialKookminPublicNewlyMarriedDto specialKookminPublicNewlyMarriedDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(specialKookminPublicNewlyMarriedDto.getNotificationNumber()).get();
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(specialKookminPublicNewlyMarriedDto.getHousingType(), aptInfo).orElseThrow();

        Integer americanAge = specialKookminPublicNewlyMarriedVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = specialKookminPublicNewlyMarriedVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = specialKookminPublicNewlyMarriedVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetRecipientTf = specialKookminPublicNewlyMarriedVerificationService.meetRecipient(user);
        boolean hasMinorChildren = specialKookminPublicNewlyMarriedVerificationService.hasMinorChildren(user);
        boolean meetMonthlyAverageIncomePriorityTf = specialKookminPublicNewlyMarriedVerificationService.meetMonthlyAverageIncomePriority(user);
        boolean meetMonthlyAverageIncomeGeneralTf = specialKookminPublicNewlyMarriedVerificationService.meetMonthlyAverageIncomeGeneral(user);
        boolean meetPropertyTf = specialKookminPublicNewlyMarriedVerificationService.meetProperty(user);
        boolean secondChungyak = specialKookminPublicNewlyMarriedVerificationService.secondChungyak(user);
        boolean meetHomelessHouseholdMembersTf = specialKookminPublicNewlyMarriedVerificationService.meetHomelessHouseholdMembers(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = specialKookminPublicNewlyMarriedVerificationService.meetAllHouseMemberRewinningRestriction(user);
        boolean householderTf = specialKookminPublicNewlyMarriedVerificationService.isHouseholder(user);
        boolean isRestrictedAreaTf = specialKookminPublicNewlyMarriedVerificationService.isRestrictedArea(aptInfo);
        boolean meetBankbookJoinPeriodTf = specialKookminPublicNewlyMarriedVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetNumberOfPaymentsTf = specialKookminPublicNewlyMarriedVerificationService.meetNumberOfPayments(user, aptInfo);

        return new ResponseEntity<>(new SpecialKookminPublicNewlyMarriedResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetRecipientTf, hasMinorChildren, meetMonthlyAverageIncomePriorityTf, meetMonthlyAverageIncomeGeneralTf, meetPropertyTf, secondChungyak, meetHomelessHouseholdMembersTf, meetAllHouseMemberRewinningRestrictionTf, householderTf, isRestrictedAreaTf, meetBankbookJoinPeriodTf, meetNumberOfPaymentsTf), HttpStatus.OK);
    }

    @PostMapping("/special/minyeong/firstLife") //특별생애최초민영
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialMinyeongFirstLifeResponseDto> specialFirstLife(@RequestBody SpecialMinyeongFirstLifeDto specialMinyeongFirstLifeDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(specialMinyeongFirstLifeDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(specialMinyeongFirstLifeDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = specialPrivateFirstLifeVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = specialPrivateFirstLifeVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = specialPrivateFirstLifeVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetRecipientTf = specialPrivateFirstLifeVerificationService.meetRecipient(user);
        boolean meetMonthlyAverageIncomePriorityTf = specialPrivateFirstLifeVerificationService.meetMonthlyAverageIncomePriority(user);
        boolean meetMonthlyAverageIncomeGeneralTf = specialPrivateFirstLifeVerificationService.meetMonthlyAverageIncomeGeneral(user);
        boolean meetHomelessHouseholdMembersTf = specialPrivateFirstLifeVerificationService.meetHomelessHouseholdMembers(user);
        boolean householderTf = specialPrivateFirstLifeVerificationService.isHouseholder(user);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = specialPrivateFirstLifeVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = specialPrivateFirstLifeVerificationService.meetAllHouseMemberRewinningRestriction(user);
        boolean isRestrictedAreaTf = specialPrivateFirstLifeVerificationService.isRestrictedArea(aptInfo);
        boolean meetBankbookJoinPeriodTf = specialPrivateFirstLifeVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetDepositTf = specialPrivateFirstLifeVerificationService.meetDeposit(user, aptInfoTarget);

        return new ResponseEntity<>(new SpecialMinyeongFirstLifeResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetRecipientTf, meetMonthlyAverageIncomePriorityTf, meetMonthlyAverageIncomeGeneralTf, meetHomelessHouseholdMembersTf, householderTf, meetAllHouseMemberNotWinningIn5yearsTf, meetAllHouseMemberRewinningRestrictionTf, isRestrictedAreaTf, meetBankbookJoinPeriodTf, meetDepositTf), HttpStatus.OK);
    }

    @PostMapping("/special/kookmin/public/firstLife") //특별생애최초국민공공주택
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialKookminPublicFirstLifeResponseDto> specialFirstLife(@RequestBody SpecialKookminPublicFirstLifeDto specialKookminPublicFirstLifeDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(specialKookminPublicFirstLifeDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingTypeAndAptInfo(specialKookminPublicFirstLifeDto.getHousingType(), aptInfo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer americanAge = specialKookminPublicFirstLifeVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean meetLivingInSurroundAreaTf = specialKookminPublicFirstLifeVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = specialKookminPublicFirstLifeVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetRecipientTf = specialKookminPublicFirstLifeVerificationService.meetRecipient(user);
        boolean meetMonthlyAverageIncomePriorityTf = specialKookminPublicFirstLifeVerificationService.meetMonthlyAverageIncomePriority(user);
        boolean meetMonthlyAverageIncomeGeneralTf = specialKookminPublicFirstLifeVerificationService.meetMonthlyAverageIncomeGeneral(user);
        boolean meetPropertyTf = specialKookminPublicFirstLifeVerificationService.meetProperty(user);
        boolean meetHomelessHouseholdMembersTf = specialKookminPublicFirstLifeVerificationService.meetHomelessHouseholdMembers(user);
        boolean householderTf = specialKookminPublicFirstLifeVerificationService.isHouseholder(user);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = specialKookminPublicFirstLifeVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean meetAllHouseMemberRewinningRestrictionTf = specialKookminPublicFirstLifeVerificationService.meetAllHouseMemberRewinningRestriction(user);
        boolean isRestrictedAreaTf = specialKookminPublicFirstLifeVerificationService.isRestrictedArea(aptInfo);
        boolean meetBankbookJoinPeriodTf = specialKookminPublicFirstLifeVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetNumberOfPaymentsTf = specialKookminPublicFirstLifeVerificationService.meetNumberOfPayments(user, aptInfo);

        return new ResponseEntity<>(new SpecialKookminPublicFirstLifeResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetRecipientTf, meetMonthlyAverageIncomePriorityTf, meetMonthlyAverageIncomeGeneralTf, meetPropertyTf, meetHomelessHouseholdMembersTf, householderTf, meetAllHouseMemberNotWinningIn5yearsTf, meetAllHouseMemberRewinningRestrictionTf, isRestrictedAreaTf, meetNumberOfPaymentsTf, meetBankbookJoinPeriodTf), HttpStatus.OK);
    }
}