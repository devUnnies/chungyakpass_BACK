package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.point.*;
import com.hanium.chungyakpassback.dto.point.ReadAllUserPointDto;
import com.hanium.chungyakpassback.service.point.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/point")
public class PointController {
    private final PointOfGeneralMinyeongService pointOfGeneralMinyeongService;
    private final PointOfSpecialMinyeongMultiChildService pointOfSpecialMinyeongMultiChildService;
    private final PointOfSpecialMinyeongNewlyMarriedService pointOfSpecialMinyeongNewlyMarriedService;
    private final PointOfSpecialMinyeongOldParentSupportService pointOfSpecialMinyeongOldParentSupportService;
    private final PointOfSpecialMinyeongSingleParentsService pointOfSpecialMinyeongSingleParentsService;
    private final ReadAllUserPointService readAllUserPointService;

    public PointController(PointOfGeneralMinyeongService pointOfGeneralMinyeongService, PointOfSpecialMinyeongMultiChildService pointOfSpecialMinyeongMultiChildService, PointOfSpecialMinyeongNewlyMarriedService pointOfSpecialMinyeongNewlyMarriedService, PointOfSpecialMinyeongOldParentSupportService pointOfSpecialMinyeongOldParentSupportService, PointOfSpecialMinyeongSingleParentsService pointOfSpecialMinyeongSingleParentsService, ReadAllUserPointService readAllUserPointService) {
        this.pointOfGeneralMinyeongService = pointOfGeneralMinyeongService;
        this.pointOfSpecialMinyeongMultiChildService = pointOfSpecialMinyeongMultiChildService;
        this.pointOfSpecialMinyeongNewlyMarriedService = pointOfSpecialMinyeongNewlyMarriedService;
        this.pointOfSpecialMinyeongOldParentSupportService = pointOfSpecialMinyeongOldParentSupportService;
        this.pointOfSpecialMinyeongSingleParentsService = pointOfSpecialMinyeongSingleParentsService;
        this.readAllUserPointService = readAllUserPointService;
    }

    @GetMapping("/record/all") //?????????????????????????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserPointDto> readAllUserPointRecord() {

        return new ResponseEntity<>(readAllUserPointService.readAllUserPointRecord(), HttpStatus.OK);
    }

    @GetMapping("/general/minyeong") //??????????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserPointDto> readGeneralMinyeongResponsePointCalculations() {
        return new ResponseEntity(pointOfGeneralMinyeongService.readGeneralMinyeongResponsePointCalculations(), HttpStatus.OK);
    }

    @PostMapping("/general/minyeong") //??????????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PointOfGeneralMinyeongResponseDto> createGeneralMinyeongPointCalculation() {
        return new ResponseEntity<>(pointOfGeneralMinyeongService.createGeneralMinyeongPointCalculation(), HttpStatus.OK);
    }

    @GetMapping("/special/multi-child") //???????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserPointDto> readMultiChildPointCalculations() {
        return new ResponseEntity(pointOfSpecialMinyeongMultiChildService.readMultiChildPointCalculations(), HttpStatus.OK);
    }

    @PostMapping("/special/multi-child") //???????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PointOfSpecialMinyeongMultiChildResponseDto> createMultiChildPointCalculation(@RequestBody PointOfSpecialMinyeongMultiChildDto pointOfSpecialMinyeongMultiChildDto) {
        return new ResponseEntity<>(pointOfSpecialMinyeongMultiChildService.createMultiChildPointCalculation(pointOfSpecialMinyeongMultiChildDto), HttpStatus.OK);
    }

    @GetMapping("/special/newly-married") //??????????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserPointDto> readNewlyMarriedPointCalculations() {
        return new ResponseEntity(pointOfSpecialMinyeongNewlyMarriedService.readNewlyMarriedPointCalculations(), HttpStatus.OK);
    }

    @PostMapping("/special/newly-married") //??????????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PointOfSpecialMinyeongNewlyMarriedResponseDto> createNewlyMarriedPointCalculation(@RequestBody PointOfSpecialMinyeongNewlyMarriedDto pointOfSpecialMinyeongNewlyMarriedDto) {

        return new ResponseEntity<>(pointOfSpecialMinyeongNewlyMarriedService.createNewlyMarriedPointCalculation(pointOfSpecialMinyeongNewlyMarriedDto), HttpStatus.OK);
    }

    @GetMapping("/special/single-parents") //???????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserPointDto> readSingleParentsPointCalculations() {
        return new ResponseEntity(pointOfSpecialMinyeongSingleParentsService.readSingleParentsPointCalculations(), HttpStatus.OK);
    }

    @PostMapping("/special/single-parents") //???????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PointOfSpecialMinyeongSingleParentsResponseDto> createSingleParentsPointCalculation(@RequestBody PointOfSpecialMinyeongSingleParentsDto pointOfSpecialMinyeongSingleParentsDto) {
        return new ResponseEntity<>(pointOfSpecialMinyeongSingleParentsService.createSingleParentsPointCalculation(pointOfSpecialMinyeongSingleParentsDto), HttpStatus.OK);
    }

    @GetMapping("/special/old-parents-support") //???????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserPointDto> readOldParentsSupportPointCalculations() {
        return new ResponseEntity(pointOfSpecialMinyeongOldParentSupportService.readOldParentsSupportPointCalculations(), HttpStatus.OK);
    }

    @PostMapping("/special/old-parents-support") //???????????????
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PointOfSpecialMinyeongOldParentsSupportResponseDto> createOldParentsSupportPointCalculation() {
        return new ResponseEntity<>(pointOfSpecialMinyeongOldParentSupportService.createOldParentsSupportPointCalculation(), HttpStatus.OK);
    }


}