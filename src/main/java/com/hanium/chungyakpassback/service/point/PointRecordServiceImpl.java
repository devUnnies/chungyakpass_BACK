package com.hanium.chungyakpassback.service.point;

import com.hanium.chungyakpassback.dto.point.SpecialMinyeongPointOfNewMarriedDto;
import com.hanium.chungyakpassback.dto.point.SpecialMinyeongPointOfNewMarriedResponseDto;
import com.hanium.chungyakpassback.dto.record.UserPointRecordDto;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.point.RecordSpecialMinyeongPointOfNewMarried;
import com.hanium.chungyakpassback.repository.input.HouseMemberRelationRepository;
import com.hanium.chungyakpassback.repository.input.HouseMemberRepository;
import com.hanium.chungyakpassback.repository.input.UserBankbookRepository;
import com.hanium.chungyakpassback.repository.input.UserRepository;
import com.hanium.chungyakpassback.repository.point.RecordSpecialMinyeongPointOfNewMarriedRepository;
import com.hanium.chungyakpassback.repository.standard.AddressLevel1Repository;
import com.hanium.chungyakpassback.service.verification.GeneralPrivateVerificationServiceImpl;
import com.hanium.chungyakpassback.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PointRecordServiceImpl implements PointRecordService {
    final UserRepository userRepository;
    final HouseMemberRelationRepository houseMemberRelationRepository;
    final UserBankbookRepository userBankbookRepository;
    final HouseMemberRepository houseMemberRepository;
    final GeneralPrivateVerificationServiceImpl generalPrivateVerificationServiceImpl;
    final PointCalculationOfNewMarriedServiceImpl pointCalculationOfNewMarriedServiceImpl;
    final AddressLevel1Repository addressLevel1Repository;
//    final RecordSpecialMinyeongPointOfNewMarried recordSpecialMinyeongPointOfNewMarried;
    final RecordSpecialMinyeongPointOfNewMarriedRepository recordSpecialMinyeongPointOfNewMarriedRepository;

    @Override
    public UserPointRecordDto readAllUserPointRecord(){
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
//        UserPointRecordDto userPointRecordDto = new UserPointRecordDto();

        List<SpecialMinyeongPointOfNewMarriedResponseDto> specialMinyeongPointOfNewMarriedResponseDtos = new ArrayList<>();
        for (RecordSpecialMinyeongPointOfNewMarried recordSpecialMinyeongPointOfNewMarried : recordSpecialMinyeongPointOfNewMarriedRepository.findAllByUser(user)){
            SpecialMinyeongPointOfNewMarriedResponseDto specialMinyeongPointOfNewMarriedResponseDto = new SpecialMinyeongPointOfNewMarriedResponseDto(recordSpecialMinyeongPointOfNewMarried);
            specialMinyeongPointOfNewMarriedResponseDtos.add(specialMinyeongPointOfNewMarriedResponseDto);
        }
//        userPointRecordDto.setSpecialMinyeongPointOfNewMarriedResponseDtos(specialMinyeongPointOfNewMarriedResponseDtos);


       // RecordSpecialMinyeongPointOfNewMarried recordSpecialMinyeongPointOfNewMarried = RecordSpecialMinyeongPointOfNewMarried.builder()
//                    .user(user)
//                    .bankbookPaymentsCount(specialMinyeongPointOfNewMarriedResponseDto.getBankbookPaymentsCount())
//                    .build();
//            recordSpecialMinyeongPointOfNewMarriedRepository.save(recordSpecialMinyeongPointOfNewMarried);



        UserPointRecordDto userPointRecordDto = UserPointRecordDto.builder()
                .specialMinyeongPointOfNewMarriedResponseDtos(specialMinyeongPointOfNewMarriedResponseDtos)
                .build();

        return userPointRecordDto;
    }
}
