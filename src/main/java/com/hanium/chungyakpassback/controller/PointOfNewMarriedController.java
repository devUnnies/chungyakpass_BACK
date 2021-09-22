package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.verification.SpecialMinyeongPointOfNewMarriedDto;
import com.hanium.chungyakpassback.dto.verification.SpecialMinyeongPointOfNewMarriedResponseDto;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.enumtype.ErrorCode;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.input.UserRepository;
import com.hanium.chungyakpassback.service.verification.PointCalculationOfNewMarriedService;
import com.hanium.chungyakpassback.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/point")
public class PointOfNewMarriedController {
    private final UserRepository userRepository;
    private final AptInfoRepository aptInfoRepository;
    private final PointCalculationOfNewMarriedService pointCalculationOfNewMarriedService;

    public PointOfNewMarriedController(UserRepository userRepository, AptInfoRepository aptInfoRepository, PointCalculationOfNewMarriedService pointCalculationOfNewMarriedService) {
        this.userRepository = userRepository;
        this.aptInfoRepository = aptInfoRepository;
        this.pointCalculationOfNewMarriedService = pointCalculationOfNewMarriedService;
    }

    @PostMapping ("/special/newMarried")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialMinyeongPointOfNewMarriedResponseDto> specialMinyeongPointOfNewMarried(@RequestBody SpecialMinyeongPointOfNewMarriedDto specialMinyeongPointOfNewMarriedDto){
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        AptInfo aptInfo = aptInfoRepository.findById(specialMinyeongPointOfNewMarriedDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer numberOfMinors = pointCalculationOfNewMarriedService.numberOfMinors(user);
        Integer ageOfMostYoungChild = pointCalculationOfNewMarriedService.ageOfMostYoungChild(user);
        Integer periodOfMarriged = pointCalculationOfNewMarriedService.periodOfMarriged(user);
        Integer bankbookPaymentsCount = pointCalculationOfNewMarriedService.bankbookPaymentsCount(user);
        Integer periodOfApplicableAreaResidence =  pointCalculationOfNewMarriedService.periodOfApplicableAreaResidence ( user, aptInfo);
        Integer monthOfAverageIncome = pointCalculationOfNewMarriedService.monthOfAverageIncome(user);

        return new ResponseEntity<>(new SpecialMinyeongPointOfNewMarriedResponseDto(numberOfMinors,periodOfMarriged,bankbookPaymentsCount,periodOfApplicableAreaResidence,ageOfMostYoungChild,monthOfAverageIncome), HttpStatus.OK);
    }

}
