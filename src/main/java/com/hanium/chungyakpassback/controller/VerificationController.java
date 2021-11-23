package com.hanium.chungyakpassback.controller;

import com.hanium.chungyakpassback.dto.verification.ReadAllUserVerificationDto;
import com.hanium.chungyakpassback.dto.verification.*;
import com.hanium.chungyakpassback.service.verification.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/verification")
public class VerificationController {
    private final VerificationOfGeneralMinyeongService verificationOfGeneralMinyeongService;
    private final VerificationOfGeneralKookminService verificationOfGeneralKookminService;
    private final VerificationOfSpecialMinyeongMultiChildService verificationOfSpecialMinyeongMultiChildService;
    private final VerificationOfSpecialKookminPublicMultiChildService verificationOfSpecialKookminPublicMultiChildService;
    private final VerificationOfSpecialMinyeongOldParentService verificationOfSpecialMinyeongOldParentService;
    private final VerificationOfSpecialKookminPublicOldParentService verificationOfSpecialKookminPublicOldParentService;
    private final VerificationOfSpecialMinyeongNewlyMarriedService verificationOfSpecialMinyeongNewlyMarriedService;
    private final VerificationOfSpecialKookminNewlyMarriedService verificationOfSpecialKookminNewlyMarriedService;
    private final VerificationOfSpecialKookminPublicNewlyMarriedService verificationOfSpecialKookminPublicNewlyMarriedService;
    private final VerificationOfSpecialMinyeongFirstLifeService verificationOfSpecialMinyeongFirstLifeService;
    private final VerificationOfSpecialKookminPublicFirstLifeService verificationOfSpecialKookminPublicFirstLifeService;
    public final ReadAllUserVerificationService readAllUserVerificationService;

    public VerificationController(VerificationOfGeneralMinyeongService verificationOfGeneralMinyeongService, VerificationOfGeneralKookminService verificationOfGeneralKookminService, VerificationOfSpecialMinyeongMultiChildService verificationOfSpecialMinyeongMultiChildService, VerificationOfSpecialKookminPublicMultiChildService verificationOfSpecialKookminPublicMultiChildService, VerificationOfSpecialMinyeongOldParentService verificationOfSpecialMinyeongOldParentService, VerificationOfSpecialKookminPublicOldParentService verificationOfSpecialKookminPublicOldParentService, VerificationOfSpecialMinyeongNewlyMarriedService verificationOfSpecialMinyeongNewlyMarriedService, VerificationOfSpecialKookminNewlyMarriedService verificationOfSpecialKookminNewlyMarriedService, VerificationOfSpecialKookminPublicNewlyMarriedService verificationOfSpecialKookminPublicNewlyMarriedService, VerificationOfSpecialMinyeongFirstLifeService verificationOfSpecialMinyeongFirstLifeService, VerificationOfSpecialKookminPublicFirstLifeService verificationOfSpecialKookminPublicFirstLifeService, ReadAllUserVerificationService readAllUserVerificationService) {
        this.verificationOfGeneralMinyeongService = verificationOfGeneralMinyeongService;
        this.verificationOfGeneralKookminService = verificationOfGeneralKookminService;
        this.verificationOfSpecialMinyeongMultiChildService = verificationOfSpecialMinyeongMultiChildService;
        this.verificationOfSpecialKookminPublicMultiChildService = verificationOfSpecialKookminPublicMultiChildService;
        this.verificationOfSpecialMinyeongOldParentService = verificationOfSpecialMinyeongOldParentService;
        this.verificationOfSpecialKookminPublicOldParentService = verificationOfSpecialKookminPublicOldParentService;
        this.verificationOfSpecialMinyeongNewlyMarriedService = verificationOfSpecialMinyeongNewlyMarriedService;
        this.verificationOfSpecialKookminNewlyMarriedService = verificationOfSpecialKookminNewlyMarriedService;
        this.verificationOfSpecialKookminPublicNewlyMarriedService = verificationOfSpecialKookminPublicNewlyMarriedService;
        this.verificationOfSpecialMinyeongFirstLifeService = verificationOfSpecialMinyeongFirstLifeService;
        this.verificationOfSpecialKookminPublicFirstLifeService = verificationOfSpecialKookminPublicFirstLifeService;
        this.readAllUserVerificationService = readAllUserVerificationService;
    }

    @GetMapping("/record/all") //청악자격결과전체조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readAllVerifications() {

        return new ResponseEntity<>(readAllUserVerificationService.readAllVerifications(), HttpStatus.OK);
    }

    @GetMapping("/general/minyeong") //일반민영조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readGeneralMinyeongVerifications() {
        return new ResponseEntity(verificationOfGeneralMinyeongService.readGeneralMinyeongVerifications(), HttpStatus.OK);
    }

    @PostMapping("/general/minyeong") //일반민영저장
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfGeneralMinyeongResponseDto> createGeneralMinyeongVerification(@RequestBody VerificationOfGeneralMinyeongDto verificationOfGeneralMinyeongDto) {
        return new ResponseEntity<>(verificationOfGeneralMinyeongService.createGeneralMinyeongVerification(verificationOfGeneralMinyeongDto), HttpStatus.OK);
    }

    @PatchMapping("/general/minyeong/{verificationRecordGeneralMinyeongId}") //일반민영업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfGeneralMinyeongResponseDto> updateGeneralMinyeongVerification(@PathVariable Long verificationRecordGeneralMinyeongId, @RequestBody VerificationOfGeneralMinyeongUpdateDto verificationOfGeneralMinyeongUpdateDto) {
        return ResponseEntity.ok(verificationOfGeneralMinyeongService.updateGeneralMinyeongVerification(verificationRecordGeneralMinyeongId, verificationOfGeneralMinyeongUpdateDto));
    }

    @GetMapping("/general/kookmin") //일반국민조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readGeneralKookminVerifications() {
        return new ResponseEntity(verificationOfGeneralKookminService.readGeneralKookminVerifications(), HttpStatus.OK);
    }

    @PostMapping("/general/kookmin") //일반국민저장
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfGeneralKookminResponseDto> createGeneralKookminVerification(@RequestBody VerificationOfGeneralKookminDto verificationOfGeneralKookminDto) {
        return new ResponseEntity<>(verificationOfGeneralKookminService.createGeneralKookminVerification(verificationOfGeneralKookminDto), HttpStatus.OK);
    }

    @PatchMapping("/general/kookmin/{verificationRecordGeneralKookminId}") //일반국민업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfGeneralKookminResponseDto> updateGeneralKookminVerification(@PathVariable Long verificationRecordGeneralKookminId, @RequestBody VerificationOfGeneralKookminUpdateDto verificationOfGeneralKookminUpdateDto) {
        return ResponseEntity.ok(verificationOfGeneralKookminService.updateGeneralKookminVerification(verificationRecordGeneralKookminId, verificationOfGeneralKookminUpdateDto));
    }

    @GetMapping("/special/minyeong/multi-child") //특별다자녀민영조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readSpecialMinyeongMultiChildVerifications() {
        return new ResponseEntity(verificationOfSpecialMinyeongMultiChildService.readSpecialMinyeongMultiChildVerifications(), HttpStatus.OK);
    }

    @PostMapping("/special/minyeong/multi-child") //특별다자녀민영저장
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialMinyeongMultiChildResponseDto> createSpecialMinyeongMultiChildVerification(@RequestBody VerificationOfSpecialMinyeongMultiChildDto verificationOfSpecialMinyeongMultiChildDto) {
        return new ResponseEntity<>(verificationOfSpecialMinyeongMultiChildService.createSpecialMinyeongMultiChildVerification(verificationOfSpecialMinyeongMultiChildDto), HttpStatus.OK);
    }

    @PatchMapping("/special/minyeong/multi-child/{verificationRecordSpecialMinyeongMultiChildId}") //특별다자녀민영업데이트
    //특별신혼부부국민공공주택업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialMinyeongMultiChildResponseDto> updateSpecialMinyeongMultiChildVerification(@PathVariable Long verificationRecordSpecialMinyeongMultiChildId, @RequestBody VerificationOfSpecialMinyeongMultiChildUpdateDto verificationOfSpecialMinyeongMultiChildUpdateDto) {
        return ResponseEntity.ok(verificationOfSpecialMinyeongMultiChildService.updateSpecialMinyeongMultiChildVerification(verificationRecordSpecialMinyeongMultiChildId, verificationOfSpecialMinyeongMultiChildUpdateDto));
    }

    @GetMapping("/special/kookmin/public/multi-child") //특별다자녀국민조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readSpecialKookminMultiChildVerifications() {
        return new ResponseEntity(verificationOfSpecialKookminPublicMultiChildService.readSpecialKookminMultiChildVerifications(), HttpStatus.OK);
    }

    @PostMapping("/special/kookmin/public/multi-child") //특별다자녀국민공공주택
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialKookminPublicMultiChildResponseDto> createSpecialKookminPublicMultiChildVerification(@RequestBody VerificationOfSpecialKookminPublicMultiChildDto verificationOfSpecialKookminPublicMultiChildDto) {
        return new ResponseEntity<>(verificationOfSpecialKookminPublicMultiChildService.createSpecialKookminPublicMultiChildVerification(verificationOfSpecialKookminPublicMultiChildDto), HttpStatus.OK);
    }

    @PatchMapping("/special/kookmin/public/multi-child/{verificationRecordSpecialKookminMultiChildId}")
    //특별다자녀국민공공주택업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialKookminPublicMultiChildResponseDto> updateSpecialKookminPublicMultiChildVerification(@PathVariable Long verificationRecordSpecialKookminMultiChildId, @RequestBody VerificationOfSpecialKookminPublicMultiChildUpdateDto verificationOfSpecialKookminPublicMultiChildUpdateDto) {
        return ResponseEntity.ok(verificationOfSpecialKookminPublicMultiChildService.updateSpecialKookminPublicMultiChildVerification(verificationRecordSpecialKookminMultiChildId, verificationOfSpecialKookminPublicMultiChildUpdateDto));
    }

    @GetMapping("/special/minyeong/old-parent") //특별노부모민영조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readSpecialMinyeongOldParentVerifications() {
        return new ResponseEntity(verificationOfSpecialMinyeongOldParentService.readSpecialMinyeongOldParentVerifications(), HttpStatus.OK);
    }

    @PostMapping("/special/minyeong/old-parent") //특별노부모민영
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialMinyeongOldParentResponseDto> createSpecialMinyeongOldParentVerification(@RequestBody VerificationOfSpecialMinyeongOldParentDto verificationOfSpecialMinyeongOldParentDto) {
        return new ResponseEntity<>(verificationOfSpecialMinyeongOldParentService.createSpecialMinyeongOldParentVerification(verificationOfSpecialMinyeongOldParentDto), HttpStatus.OK);
    }

    @PatchMapping("/special/minyeong/old-parent/{verificationRecordSpecialMinyeongOldParentId}") //특별노부모민영업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialMinyeongOldParentResponseDto> updateSpecialMinyeongOldParentVerification(@PathVariable Long verificationRecordSpecialMinyeongOldParentId, @RequestBody VerificationOfSpecialMinyeongOldParentUpdateDto verificationOfSpecialMinyeongOldParentUpdateDto) {
        return ResponseEntity.ok(verificationOfSpecialMinyeongOldParentService.updateSpecialMinyeongOldParentVerification(verificationRecordSpecialMinyeongOldParentId, verificationOfSpecialMinyeongOldParentUpdateDto));
    }

    @GetMapping("/special/kookmin/public/old-parent") //특별노부모국민조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readSpecialKookminOldParentVerifications() {
        return new ResponseEntity(verificationOfSpecialKookminPublicOldParentService.readSpecialKookminOldParentVerifications(), HttpStatus.OK);
    }

    @PostMapping("/special/kookmin/public/old-parent") //특별노부모국민공공주택
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialKookminPublicOldParentResponseDto> createSpecialKookminPublicOldParentVerification(@RequestBody VerificationOfSpecialKookminPublicOldParentDto verificationOfSpecialKookminPublicOldParentDto) {
        return new ResponseEntity<>(verificationOfSpecialKookminPublicOldParentService.createSpecialKookminPublicOldParentVerification(verificationOfSpecialKookminPublicOldParentDto), HttpStatus.OK);
    }

    @PatchMapping("/special/kookmin/public/old-parent/{verificationRecordSpecialKookminOldParentId}") //특별노부모국민공공주택업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialKookminPublicOldParentResponseDto> updateSpecialKookminPublicOldParentVerification(@PathVariable Long verificationRecordSpecialKookminOldParentId, @RequestBody VerificationOfSpecialKookminPublicOldParentUpdateDto verificationOfSpecialKookminPublicOldParentUpdateDto) {
        return ResponseEntity.ok(verificationOfSpecialKookminPublicOldParentService.updateSpecialKookminPublicOldParentVerification(verificationRecordSpecialKookminOldParentId, verificationOfSpecialKookminPublicOldParentUpdateDto));
    }

    @GetMapping("/special/minyeong/newly-married") //특별신혼부부민영조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readSpecialMinyeongNewlyMarriedVerifications() {
        return new ResponseEntity(verificationOfSpecialMinyeongNewlyMarriedService.readSpecialMinyeongNewlyMarriedVerifications(), HttpStatus.OK);
    }

    @PostMapping("/special/minyeong/newly-married") //특별신혼부부민영
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialMinyeongNewlyMarriedResponseDto> createSpecialMinyeongNewlyMarriedVerification(@RequestBody VerificationOfSpecialMinyeongNewlyMarriedDto verificationOfSpecialMinyeongNewlyMarriedDto) {
        return new ResponseEntity<>(verificationOfSpecialMinyeongNewlyMarriedService.createSpecialMinyeongNewlyMarriedVerification(verificationOfSpecialMinyeongNewlyMarriedDto), HttpStatus.OK);
    }

    @PatchMapping("/special/minyeong/newly-married/{verificationRecordSpecialMinyeongNewlyMarriedId}") //특별신혼부부민영업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialMinyeongNewlyMarriedResponseDto> updateSpecialMinyeongNewlyMarriedVerification(@PathVariable Long verificationRecordSpecialMinyeongNewlyMarriedId, @RequestBody VerificationOfSpecialMinyeongNewlyMarriedUpdateDto verificationOfSpecialMinyeongNewlyMarriedUpdateDto) {
        return ResponseEntity.ok(verificationOfSpecialMinyeongNewlyMarriedService.updateSpecialMinyeongNewlyMarriedVerification(verificationRecordSpecialMinyeongNewlyMarriedId, verificationOfSpecialMinyeongNewlyMarriedUpdateDto));
    }

    @GetMapping("/special/kookmin/newly-married") //특별신혼부부국민조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readSpecialKookminPublicNewlyMarriedVerifications() {
        return new ResponseEntity(verificationOfSpecialKookminPublicNewlyMarriedService.readSpecialKookminPublicNewlyMarriedVerifications(), HttpStatus.OK);
    }

    @PostMapping("/special/kookmin/newly-married") //특별신혼부부국민
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialKookminNewlyMarriedResponseDto> createSpecialKookminNewlyMarriedVerification(@RequestBody VerificationOfSpecialKookminNewlyMarriedDto verificationOfSpecialKookminNewlyMarriedDto) {
        return new ResponseEntity<>(verificationOfSpecialKookminNewlyMarriedService.createSpecialKookminNewlyMarriedVerification(verificationOfSpecialKookminNewlyMarriedDto), HttpStatus.OK);
    }

    @PatchMapping("/special/kookmin/newly-married/{verificationRecordSpecialKookminNewlyMarriedId}") //특별신혼부부국민업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialKookminNewlyMarriedResponseDto> updateSpecialKookminNewlyMarriedVerification(@PathVariable Long verificationRecordSpecialKookminNewlyMarriedId, @RequestBody VerificationOfSpecialKookminNewlyMarriedUpdateDto verificationOfSpecialKookminNewlyMarriedUpdateDto) {
        return ResponseEntity.ok(verificationOfSpecialKookminNewlyMarriedService.updateSpecialKookminNewlyMarriedVerification(verificationRecordSpecialKookminNewlyMarriedId, verificationOfSpecialKookminNewlyMarriedUpdateDto));
    }

    @PostMapping("/special/kookmin/public/newly-married") //특별신혼부부국민공공주택
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialKookminPublicNewlyMarriedResponseDto> createSpecialKookminPublicNewlyMarriedVerification(@RequestBody VerificationOfSpecialKookminPublicNewlyMarriedDto verificationOfSpecialKookminPublicNewlyMarriedDto) {
        return new ResponseEntity<>(verificationOfSpecialKookminPublicNewlyMarriedService.createSpecialKookminPublicNewlyMarriedVerification(verificationOfSpecialKookminPublicNewlyMarriedDto), HttpStatus.OK);
    }

    @PatchMapping("/special/kookmin/public/newly-married/{verificationRecordSpecialKookminNewlyMarriedId}")
    //특별신혼부부국민공공주택업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialKookminPublicNewlyMarriedResponseDto> updateSpecialKookminPublicNewlyMarriedVerification(@PathVariable Long verificationRecordSpecialKookminNewlyMarriedId, @RequestBody VerificationOfSpecialKookminPublicNewlyMarriedUpdateDto verificationOfSpecialKookminPublicNewlyMarriedUpdateDto) {
        return ResponseEntity.ok(verificationOfSpecialKookminPublicNewlyMarriedService.updateSpecialKookminPublicNewlyMarriedVerification(verificationRecordSpecialKookminNewlyMarriedId, verificationOfSpecialKookminPublicNewlyMarriedUpdateDto));
    }

    @GetMapping("/special/minyeong/first-life") //특별생애최초민영조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readSpecialMinyeongFirstLifeVerifications() {
        return new ResponseEntity(verificationOfSpecialMinyeongFirstLifeService.readSpecialMinyeongFirstLifeVerifications(), HttpStatus.OK);
    }

    @PostMapping("/special/minyeong/first-life") //특별생애최초민영
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialMinyeongFirstLifeResponseDto> createSpecialMinyeongFirstLifeVerification(@RequestBody VerificationOfSpecialMinyeongFirstLifeDto verificationOfSpecialMinyeongFirstLifeDto) {
        return new ResponseEntity<>(verificationOfSpecialMinyeongFirstLifeService.createSpecialMinyeongFirstLifeVerification(verificationOfSpecialMinyeongFirstLifeDto), HttpStatus.OK);
    }

    @PatchMapping("/special/minyeong/first-life/{verificationRecordSpecialMinyeongFirstLifeId}") //특별생애최초민영업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialMinyeongFirstLifeResponseDto> updateSpecialMinyeongFirstLifeVerification(@PathVariable Long verificationRecordSpecialMinyeongFirstLifeId, @RequestBody VerificationOfSpecialMinyeongFirstLifeUpdateDto verificationOfSpecialMinyeongFirstLifeUpdateDto) {
        return ResponseEntity.ok(verificationOfSpecialMinyeongFirstLifeService.updateSpecialMinyeongFirstLifeVerification(verificationRecordSpecialMinyeongFirstLifeId, verificationOfSpecialMinyeongFirstLifeUpdateDto));
    }

    @GetMapping("/special/kookmin/public/first-life") //특별생애최초국민조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReadAllUserVerificationDto> readSpecialKookminFirstLifeVerifications() {
        return new ResponseEntity(verificationOfSpecialKookminPublicFirstLifeService.readSpecialKookminFirstLifeVerifications(), HttpStatus.OK);
    }

    @PostMapping("/special/kookmin/public/first-life") //특별생애최초국민공공주택
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialKookminPublicFirstLifeResponseDto> createSpecialKookminPublicFirstLifeVerification(@RequestBody VerificationOfSpecialKookminPublicFirstLifeDto verificationOfSpecialKookminPublicFirstLifeDto) {
        return new ResponseEntity<>(verificationOfSpecialKookminPublicFirstLifeService.createSpecialKookminPublicFirstLifeVerification(verificationOfSpecialKookminPublicFirstLifeDto), HttpStatus.OK);
    }

    @PatchMapping("/special/kookmin/public/first-life/{verificationRecordSpecialKookminFirstLifeId}") //특별생애최초국민공공주택업데이트
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<VerificationOfSpecialKookminPublicFirstLifeResponseDto> updateSpecialKookminPublicFirstLifeVerification(@PathVariable Long verificationRecordSpecialKookminFirstLifeId, @RequestBody VerificationOfSpecialKookminPublicFirstLifeUpdateDto verificationOfSpecialKookminPublicFirstLifeUpdateDto) {
        return ResponseEntity.ok(verificationOfSpecialKookminPublicFirstLifeService.updateSpecialKookminPublicFirstLifeVerification(verificationRecordSpecialKookminFirstLifeId, verificationOfSpecialKookminPublicFirstLifeUpdateDto));
    }

}