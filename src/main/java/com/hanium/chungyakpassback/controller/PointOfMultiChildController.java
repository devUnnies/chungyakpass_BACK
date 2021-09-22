package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.verification.*;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.enumtype.ErrorCode;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.input.UserRepository;
import com.hanium.chungyakpassback.service.verification.PointCalculationOfMultiChildService;
import com.hanium.chungyakpassback.service.verification.PointCalculationService;
import com.hanium.chungyakpassback.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/point")
public class PointOfMultiChildController {
    private final UserRepository userRepository;
    private final AptInfoRepository aptInfoRepository;
    private final PointCalculationOfMultiChildService pointCalculationOfMultiChildService;

    public PointOfMultiChildController(UserRepository userRepository,AptInfoRepository aptInfoRepository,PointCalculationOfMultiChildService pointCalculationOfMultiChildService)  {
        this.userRepository = userRepository;
        this.aptInfoRepository = aptInfoRepository;
        this.pointCalculationOfMultiChildService = pointCalculationOfMultiChildService;
    }

    @PostMapping("/special/multiChild")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialPointOfMultiChildResponseDto> specialMultiChildPoint(@RequestBody SpecialPointOfMultiChildDto specialPointOfMultiChildDto){
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        AptInfo aptInfo = aptInfoRepository.findById(specialPointOfMultiChildDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));
        Integer numberOfChild = pointCalculationOfMultiChildService.numberOfChild(user);
        Integer numberOfChildUnder6Year = pointCalculationOfMultiChildService.numberOfChildUnder6Year(user);
        Integer bankbookJoinPeriod = pointCalculationOfMultiChildService.bankbookJoinPeriod(user);
        Integer periodOfApplicableAreaResidence = pointCalculationOfMultiChildService.periodOfApplicableAreaResidence(user, aptInfo);
        Integer periodOfHomelessness = pointCalculationOfMultiChildService.periodOfHomelessness(user);
        Integer generationComposition = pointCalculationOfMultiChildService.generationComposition (specialPointOfMultiChildDto);
        return new ResponseEntity<>(new SpecialPointOfMultiChildResponseDto(numberOfChild,numberOfChildUnder6Year,bankbookJoinPeriod,periodOfApplicableAreaResidence,periodOfHomelessness,generationComposition), HttpStatus.OK);
    }
}
