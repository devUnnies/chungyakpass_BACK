package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.verification.GeneralMinyeongDto;
import com.hanium.chungyakpassback.dto.verification.GeneralMinyeongPointDto;
import com.hanium.chungyakpassback.dto.verification.GeneralMinyeongResponsePointDto;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.repository.input.UserRepository;
import com.hanium.chungyakpassback.service.verification.PointCalculationService;
import com.hanium.chungyakpassback.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/point")
public class PointController {
    private final UserRepository userRepository;
    private final PointCalculationService pointCalculationService;

    public PointController(UserRepository userRepository, PointCalculationService pointCalculationService) {
        this.userRepository = userRepository;
        this.pointCalculationService = pointCalculationService;
    }

    @PostMapping("/genereal/minyeoung")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GeneralMinyeongResponsePointDto> generalMinyeongPoint(@RequestBody GeneralMinyeongPointDto generalMinyeongPointDto){
        System.out.println("!!!!!!!!!!!");
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        System.out.println("!!!!!!!!!!!!!"+generalMinyeongPointDto.getTypeOfApplication());
        Integer periodOfHomelessness = pointCalculationService.periodOfHomelessness(user);
        Integer periodOfBankbook = pointCalculationService.bankbookJoinPeriod(user);
        Integer numberOfDependents = pointCalculationService.numberOfDependents(user, generalMinyeongPointDto);

        return new ResponseEntity<>(new GeneralMinyeongResponsePointDto(periodOfHomelessness, periodOfBankbook, numberOfDependents), HttpStatus.OK);
    }

}