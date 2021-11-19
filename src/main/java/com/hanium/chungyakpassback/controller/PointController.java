package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.point.*;
import com.hanium.chungyakpassback.dto.record.UserPointRecordDto;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.recordPoint.RecordGeneralMinyeongPoint;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.input.UserRepository;
import com.hanium.chungyakpassback.repository.recordPoint.*;
import com.hanium.chungyakpassback.service.point.*;
import com.hanium.chungyakpassback.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/point")
public class PointController {
    private final PointCalculationService pointCalculationService;
    private final PointCalculationOfMultiChildService pointCalculationOfMultiChildService;
    private final PointCalculationOfNewMarriedService pointCalculationOfNewMarriedService;
    private final PointCalculationOfOldParentSupportService pointCalculationOfOldParentSupportService;
    private final PointCalculationOfSingleParentsService pointCalculationOfSingleParentsService;
    private final PointRecordService pointRecordService;

    public PointController(PointCalculationService pointCalculationService, PointCalculationOfMultiChildService pointCalculationOfMultiChildService,  PointCalculationOfNewMarriedService pointCalculationOfNewMarriedService, PointCalculationOfOldParentSupportService pointCalculationOfOldParentSupportService, PointCalculationOfSingleParentsService pointCalculationOfSingleParentsService,PointRecordService pointRecordService) {
        this.pointCalculationService = pointCalculationService;
        this.pointCalculationOfMultiChildService = pointCalculationOfMultiChildService;
        this.pointCalculationOfNewMarriedService = pointCalculationOfNewMarriedService;
        this.pointCalculationOfOldParentSupportService = pointCalculationOfOldParentSupportService;
        this.pointCalculationOfSingleParentsService = pointCalculationOfSingleParentsService;
        this.pointRecordService = pointRecordService;
    }


    @GetMapping("/genereal/minyeoung")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserPointRecordDto> readGeneralMinyeongPoint() {
        return new ResponseEntity(pointRecordService.recordGeneralMinyeongResponsePoint(), HttpStatus.OK);
    }

    @PostMapping("/genereal/minyeoung")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GeneralMinyeongResponsePointDto> generalMinyeongPoint(@RequestBody GeneralMinyeongPointDto generalMinyeongPointDto) {
        return new ResponseEntity<>(pointCalculationService.recordPointCalculationService(generalMinyeongPointDto), HttpStatus.OK);
    }


    @GetMapping("/record/point")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserPointRecordDto> readAllUserPointRecord() {

        return new ResponseEntity<>(pointRecordService.readAllUserPointRecord(), HttpStatus.OK);
    }

    @GetMapping("/special/multiChild")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserPointRecordDto> readSpecialMinyeongPointOfMultiChild() {
        return new ResponseEntity(pointRecordService.recordSpecialMinyeongPointOfMultiChild(), HttpStatus.OK);
    }

    @PostMapping("/special/multiChild")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialMinyeongPointOfMultiChildResponseDto> specialMinyeongPointOfMultiChild(@RequestBody SpecialMinyeongPointOfMultiChildDto specialMinyeongPointOfMultiChildDto) {
        return new ResponseEntity<>(pointCalculationOfMultiChildService.recordPointCalculationOfMultiChildService(specialMinyeongPointOfMultiChildDto), HttpStatus.OK);
    }

    @GetMapping("/special/newMarried")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserPointRecordDto> readSpecialMinyeongPointOfNewMarriedResponseDto() {
        return new ResponseEntity(pointRecordService.recordSpecialMinyeongPointOfNewMarried(), HttpStatus.OK);
    }

    @PostMapping("/special/newMarried")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialMinyeongPointOfNewMarriedResponseDto> specialMinyeongPointOfNewMarried(@RequestBody SpecialMinyeongPointOfNewMarriedDto specialMinyeongPointOfNewMarriedDto) {

        return new ResponseEntity<>(pointCalculationOfNewMarriedService.recordPointCalculationOfNewMarriedService(specialMinyeongPointOfNewMarriedDto), HttpStatus.OK);
    }

    @GetMapping("/special/singleParents")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserPointRecordDto> readSpecialMinyeongPointOfSingleParents() {
        return new ResponseEntity(pointRecordService.recordSpecialMinyeongPointOfSingleParents(), HttpStatus.OK);
    }

    @PostMapping("/special/singleParents")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialMinyeongPointOfSingleParentsResponseDto> specialMinyeongPointOfSingleParents(@RequestBody SpecialMinyeongPointOfSingleParentsDto specialMinyeongPointOfSingleParentsDto) {
        return new ResponseEntity<>(pointCalculationOfSingleParentsService.recordPointCalculationOfSingleParentsService(specialMinyeongPointOfSingleParentsDto), HttpStatus.OK);
    }

    @GetMapping("/special/oldParentsSupport")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserPointRecordDto> readSpecialMinyeongPointOfOldParentsSupport() {
        return new ResponseEntity(pointRecordService.recordSpecialMinyeongPointOfOldParentsSupport(), HttpStatus.OK);
    }

    @PostMapping("/special/oldParentsSupport")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SpecialMinyeongPointOfOldParentsSupportResponseDto> specialMinyeongPointOfOldParentsSupport(@RequestBody SpecialPointOfOldParentsSupportDto specialPointOfOldParentsSupportDto) {
        return new ResponseEntity<>(pointCalculationOfOldParentSupportService.recordPointCalculationOfNewMarriedService(specialPointOfOldParentsSupportDto), HttpStatus.OK);
    }



}