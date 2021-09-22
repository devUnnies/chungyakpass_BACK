package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.verification.GeneralMinyeongDto;
import com.hanium.chungyakpassback.dto.verification.GeneralMinyeongResponseDto;
import com.hanium.chungyakpassback.dto.verification.SpecialPrivateFirstLifeDto;
import com.hanium.chungyakpassback.dto.verification.SpecialPrivateFirstLifeResponseDto;
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
import com.hanium.chungyakpassback.service.verification.SpecialPrivateVerificationFirstLifeService;
import com.hanium.chungyakpassback.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/verification")
public class VerificationOfSpecialPrivateFirstLifeController {
    private final UserRepository userRepository;
    private final AptInfoRepository aptInfoRepository;
    private final AptInfoTargetRepository aptInfoTargetRepository;
    private final SpecialPrivateVerificationFirstLifeService specialPrivateVerificationFirstLifeService;

    public VerificationOfSpecialPrivateFirstLifeController(UserRepository userRepository, SpecialPrivateVerificationFirstLifeService specialPrivateVerificationFirstLifeService, AptInfoRepository aptInfoRepository, AptInfoTargetRepository aptInfoTargetRepository) {
        this.userRepository = userRepository;
        this.specialPrivateVerificationFirstLifeService = specialPrivateVerificationFirstLifeService;
        this.aptInfoRepository = aptInfoRepository;
        this.aptInfoTargetRepository = aptInfoTargetRepository;
    }


    @PostMapping("/special/private/firstLife")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialPrivateFirstLifeResponseDto> generalMinyeong(@RequestBody SpecialPrivateFirstLifeDto specialPrivateFirstLifeDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        AptInfo aptInfo = aptInfoRepository.findById(specialPrivateFirstLifeDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingType(specialPrivateFirstLifeDto.getHousingType()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        boolean targetHousingType = specialPrivateVerificationFirstLifeService.targetHousingType(aptInfoTarget);
        boolean targetHouseAmount = specialPrivateVerificationFirstLifeService.targetHouseAmount(aptInfo,aptInfoTarget);
        boolean monthOfAverageIncome = specialPrivateVerificationFirstLifeService.monthOfAverageIncome(user);
        boolean HomelessYn = specialPrivateVerificationFirstLifeService.HomelessYn(user);
        boolean vaildObject = specialPrivateVerificationFirstLifeService.vaildObject(user, aptInfo);
        return new ResponseEntity<>(new SpecialPrivateFirstLifeResponseDto(targetHousingType,targetHouseAmount,monthOfAverageIncome,HomelessYn,vaildObject), HttpStatus.OK);
    }
}


