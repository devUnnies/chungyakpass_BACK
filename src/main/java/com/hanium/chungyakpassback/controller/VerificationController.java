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
import com.hanium.chungyakpassback.service.verification.GeneralKookminVerificationService;
import com.hanium.chungyakpassback.service.verification.GeneralPrivateMultiChildVerificationService;
import com.hanium.chungyakpassback.service.verification.GeneralPrivateVerificationService;
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
    private final GeneralPrivateVerificationService generalPrivateVerificationService;
    private final GeneralKookminVerificationService generalKookminVerificationService;
    private final GeneralPrivateMultiChildVerificationService generalPrivateMultiChildVerificationService;
    private final AptInfoRepository aptInfoRepository;
    private final AptInfoTargetRepository aptInfoTargetRepository;

    public VerificationController(UserRepository userRepository, GeneralPrivateVerificationService generalPrivateVerificationService, GeneralKookminVerificationService generalKookminVerificationService, GeneralPrivateMultiChildVerificationService generalPrivateMultiChildVerificationService, AptInfoRepository aptInfoRepository, AptInfoTargetRepository aptInfoTargetRepository) {
        this.userRepository = userRepository;
        this.generalPrivateVerificationService = generalPrivateVerificationService;
        this.generalKookminVerificationService = generalKookminVerificationService;
        this.generalPrivateMultiChildVerificationService = generalPrivateMultiChildVerificationService;
        this.aptInfoRepository = aptInfoRepository;
        this.aptInfoTargetRepository = aptInfoTargetRepository;
    }

    @PostMapping("/general/minyeoung")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GeneralMinyeongResponseDto> generalMinyeong(@RequestBody GeneralMinyeongDto generalMinyeongDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(generalMinyeongDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingType(generalMinyeongDto.getHousingType()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        boolean meetLivingInSurroundAreaTf = generalPrivateVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = generalPrivateVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        Integer americanAge = generalPrivateVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean houseHolderTf = generalPrivateVerificationService.isHouseholder(user);
        boolean isRestrictedAreaTf = generalPrivateVerificationService.isRestrictedArea(aptInfo);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = generalPrivateVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean meetHouseHavingLessThan2Apt = generalPrivateVerificationService.meetHouseHavingLessThan2Apt(user);
        boolean meetBankbookJoinPeriodTf = generalPrivateVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetDepositTf = generalPrivateVerificationService.meetDeposit(user, aptInfoTarget);
        boolean specialTf = generalPrivateVerificationService.isPriorityApt(aptInfo, aptInfoTarget);


        return new ResponseEntity<>(new GeneralMinyeongResponseDto(meetLivingInSurroundAreaTf, accountTf, americanAge, houseHolderTf, isRestrictedAreaTf, meetAllHouseMemberNotWinningIn5yearsTf, meetHouseHavingLessThan2Apt, meetBankbookJoinPeriodTf, meetDepositTf, specialTf), HttpStatus.OK);
    }

    @PostMapping("/general/kookmin")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GeneralKookminResponseDto> generalKookmin(@RequestBody GeneralKookminDto generalKookminDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(generalKookminDto.getNotificationNumber()).get();
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingType(generalKookminDto.getHousingType()).orElseThrow();

        Integer americanAge = generalKookminVerificationService.calcAmericanAge(houseMember.getBirthDay());
        boolean meetLivingInSurroundAreaTf = generalPrivateMultiChildVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = generalKookminVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetHomelessHouseholdMembersTf = generalKookminVerificationService.meetHomelessHouseholdMembers(user);
        boolean householderTf = generalKookminVerificationService.isHouseholder(user);
        boolean isRestrictedAreaTf = generalKookminVerificationService.isRestrictedArea(aptInfo);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = generalKookminVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean meetBankbookJoinPeriodTf = generalKookminVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetNumberOfPaymentsTf = generalKookminVerificationService.meetNumberOfPayments(user, aptInfo);

        return new ResponseEntity<>(new GeneralKookminResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetHomelessHouseholdMembersTf, householderTf, isRestrictedAreaTf, meetAllHouseMemberNotWinningIn5yearsTf, meetBankbookJoinPeriodTf, meetNumberOfPaymentsTf), HttpStatus.OK);
    }

    @PostMapping("/general/multichild")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GeneralMultiChildResponseDto> generalMultiChild(@RequestBody GeneralMultiChildDto generalMultiChildDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        HouseMember houseMember = user.getHouseMember();
        AptInfo aptInfo = aptInfoRepository.findById(generalMultiChildDto.getNotificationNumber()).get();
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingType(generalMultiChildDto.getHousingType()).orElseThrow();

        Integer americanAge = generalPrivateMultiChildVerificationService.calcAmericanAge(houseMember.getBirthDay());
        boolean meetLivingInSurroundAreaTf = generalPrivateMultiChildVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean accountTf = generalPrivateMultiChildVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        boolean meetHomelessHouseholdMembersTf = generalPrivateMultiChildVerificationService.meetHomelessHouseholdMembers(user);
        boolean meet3MoreMinorChildrenTf = generalPrivateMultiChildVerificationService.meet3MoreMinorChildren(user);
        boolean householderTf = generalPrivateMultiChildVerificationService.isHouseholder(user);
        boolean meetAllHouseMemberNotWinningIn5yearsTf = generalPrivateMultiChildVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean isRestrictedAreaTf = generalPrivateMultiChildVerificationService.isRestrictedArea(aptInfo);
        boolean meetHouseHavingLessThan2Apt = generalPrivateMultiChildVerificationService.meetHouseHavingLessThan2Apt(user);
        boolean specialTf = generalPrivateMultiChildVerificationService.isPriorityApt(aptInfo, aptInfoTarget);
        boolean meetDepositTf = generalPrivateMultiChildVerificationService.meetDeposit(user, aptInfoTarget);
        boolean meetBankbookJoinPeriodTf = generalPrivateMultiChildVerificationService.meetBankbookJoinPeriod(user, aptInfo);

        return new ResponseEntity<>(new GeneralMultiChildResponseDto(americanAge, meetLivingInSurroundAreaTf, accountTf, meetHomelessHouseholdMembersTf, meet3MoreMinorChildrenTf, householderTf, meetAllHouseMemberNotWinningIn5yearsTf, isRestrictedAreaTf, meetHouseHavingLessThan2Apt, specialTf, meetDepositTf, meetBankbookJoinPeriodTf), HttpStatus.OK);
    }
}
