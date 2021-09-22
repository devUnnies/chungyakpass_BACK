package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.verification.SpecialPrivateFirstLifeDto;
import com.hanium.chungyakpassback.dto.verification.SpecialPrivateFirstLifeResponseDto;
import com.hanium.chungyakpassback.dto.verification.SpecialPublicFirstLifeResponseDto;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.apt.AptInfoTarget;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.enumtype.ErrorCode;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.apt.AptInfoTargetRepository;
import com.hanium.chungyakpassback.repository.input.UserRepository;
import com.hanium.chungyakpassback.service.verification.SpecialPrivateVerificationFirstLifeService;
import com.hanium.chungyakpassback.service.verification.SpecialPublicVerificationFirstLifeService;
import com.hanium.chungyakpassback.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/verification")
public class VerificationOfSpecialPublicFirstLifeController {
    private final UserRepository userRepository;
    private final AptInfoRepository aptInfoRepository;
    private final AptInfoTargetRepository aptInfoTargetRepository;
    private final SpecialPublicVerificationFirstLifeService specialPublicVerificationFirstLifeService;

    public VerificationOfSpecialPublicFirstLifeController(UserRepository userRepository, SpecialPublicVerificationFirstLifeService specialPublicVerificationFirstLifeService, AptInfoRepository aptInfoRepository, AptInfoTargetRepository aptInfoTargetRepository) {
        this.userRepository = userRepository;
        this.specialPublicVerificationFirstLifeService = specialPublicVerificationFirstLifeService;
        this.aptInfoRepository = aptInfoRepository;
        this.aptInfoTargetRepository = aptInfoTargetRepository;
    }


    @PostMapping("/special/public/firstLife")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialPublicFirstLifeResponseDto> generalMinyeong(@RequestBody SpecialPrivateFirstLifeDto specialPublicFirstLifeDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        AptInfo aptInfo = aptInfoRepository.findById(specialPublicFirstLifeDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        AptInfoTarget aptInfoTarget = aptInfoTargetRepository.findByHousingType(specialPublicFirstLifeDto.getHousingType()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        boolean targetHouseAmount = specialPublicVerificationFirstLifeService.targetHouseAmount(aptInfo,aptInfoTarget);
        boolean monthOfAverageIncome = specialPublicVerificationFirstLifeService.monthOfAverageIncome(user);
        boolean HomelessYn = specialPublicVerificationFirstLifeService.HomelessYn(user);
        boolean vaildObject = specialPublicVerificationFirstLifeService.vaildObject(user, aptInfo);
        boolean meetDeposit = specialPublicVerificationFirstLifeService.meetDeposit(user);

        return new ResponseEntity<>(new SpecialPublicFirstLifeResponseDto(targetHouseAmount,monthOfAverageIncome,HomelessYn,vaildObject,meetDeposit), HttpStatus.OK);
    }
}


