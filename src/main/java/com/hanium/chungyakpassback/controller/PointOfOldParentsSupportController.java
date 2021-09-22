package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.verification.GeneralMinyeongPointDto;
import com.hanium.chungyakpassback.dto.verification.GeneralMinyeongResponsePointDto;
import com.hanium.chungyakpassback.dto.verification.SpecialPointOfOldParentsSupportDto;
import com.hanium.chungyakpassback.dto.verification.SpecialPointOfOldParentsSupportResponseDto;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.repository.input.UserRepository;
import com.hanium.chungyakpassback.service.verification.PointCalculationOfOldParentSupportService;
import com.hanium.chungyakpassback.service.verification.PointCalculationService;
import com.hanium.chungyakpassback.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PointOfOldParentsSupportController {
    private final UserRepository userRepository;
    private final PointCalculationOfOldParentSupportService pointCalculationOfOldParentSupportService;

    public PointOfOldParentsSupportController(UserRepository userRepository,  PointCalculationOfOldParentSupportService pointCalculationOfOldParentSupportService) {
        this.userRepository = userRepository;
        this.pointCalculationOfOldParentSupportService = pointCalculationOfOldParentSupportService;
    }

    @PostMapping("/special/oldParentsSupport")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialPointOfOldParentsSupportResponseDto> generalMinyeongPoint(@RequestBody SpecialPointOfOldParentsSupportDto specialPointOfOldParentsSupportDto){
        System.out.println("!!!!!!!!!!!");
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        Integer periodOfHomelessness = pointCalculationOfOldParentSupportService.periodOfHomelessness(user);
        Integer periodOfBankbook = pointCalculationOfOldParentSupportService.bankbookJoinPeriod(user);
        Integer numberOfDependents = pointCalculationOfOldParentSupportService.numberOfDependents(user, specialPointOfOldParentsSupportDto);
        return new ResponseEntity<>(new SpecialPointOfOldParentsSupportResponseDto(periodOfHomelessness, periodOfBankbook, numberOfDependents), HttpStatus.OK);
    }

}
