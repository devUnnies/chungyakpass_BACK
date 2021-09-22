package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.verification.GeneralMinyeongDto;
import com.hanium.chungyakpassback.dto.verification.GeneralMinyeongResponseDto;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.apt.AptInfoTarget;
import com.hanium.chungyakpassback.entity.input.HouseMember;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.enumtype.ErrorCode;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.apt.AptInfoTargetRepository;
import com.hanium.chungyakpassback.repository.input.UserRepository;
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
    private final AptInfoRepository aptInfoRepository;
    private final AptInfoTargetRepository aptInfoTargetRepository;

    public VerificationController(UserRepository userRepository, GeneralPrivateVerificationService generalPrivateVerificationService, AptInfoRepository aptInfoRepository, AptInfoTargetRepository aptInfoTargetRepository) {
        this.userRepository = userRepository;
        this.generalPrivateVerificationService = generalPrivateVerificationService;
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

        boolean meetLivingInSurroundArea = generalPrivateVerificationService.meetLivingInSurroundArea(user, aptInfo);
        boolean meetBankbookType = generalPrivateVerificationService.meetBankbookType(user, aptInfo, aptInfoTarget);
        Integer calcAmericanAge = generalPrivateVerificationService.calcAmericanAge(Optional.ofNullable(houseMember.getBirthDay()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BIRTHDAY)));
        boolean isHouseholder = generalPrivateVerificationService.isHouseholder(user);
        boolean isRestrictedArea = generalPrivateVerificationService.isRestrictedArea(aptInfo);
        boolean meetAllHouseMemberNotWinningIn5years = generalPrivateVerificationService.meetAllHouseMemberNotWinningIn5years(user);
        boolean meetHouseHavingLessThan2Apt = generalPrivateVerificationService.meetHouseHavingLessThan2Apt(user);
        boolean meetBankbookJoinPeriod = generalPrivateVerificationService.meetBankbookJoinPeriod(user, aptInfo);
        boolean meetDeposit = generalPrivateVerificationService.meetDeposit(user, aptInfoTarget);
        boolean isPriorityApt = generalPrivateVerificationService.isPriorityApt(aptInfo, aptInfoTarget);


        return new ResponseEntity<>(new GeneralMinyeongResponseDto(meetLivingInSurroundArea, meetBankbookType, calcAmericanAge, isHouseholder, isRestrictedArea, meetAllHouseMemberNotWinningIn5years, meetHouseHavingLessThan2Apt, meetBankbookJoinPeriod, meetDeposit, isPriorityApt), HttpStatus.OK);
    }
}
