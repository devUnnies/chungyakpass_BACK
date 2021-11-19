package com.hanium.chungyakpassback.service.point;

import com.hanium.chungyakpassback.dto.point.SpecialMinyeongPointOfMultiChildDto;
import com.hanium.chungyakpassback.dto.point.SpecialMinyeongPointOfMultiChildResponseDto;
import com.hanium.chungyakpassback.dto.verification.SpecialMinyeongMultiChildResponseDto;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.input.User;

public interface PointCalculationOfMultiChildService {
    SpecialMinyeongPointOfMultiChildResponseDto recordPointCalculationOfMultiChildService(SpecialMinyeongPointOfMultiChildDto specialMinyeongPointOfMultiChildDto);

    Integer numberOfChild(User user);

    Integer numberOfChildUnder6Year(User user);

    Integer bankbookJoinPeriod(User user);

    Integer periodOfApplicableAreaResidence(User user, AptInfo aptInfo);

    Integer periodOfHomelessness(User user);

    Integer generationComposition(SpecialMinyeongPointOfMultiChildDto specialMinyeongPointOfMultiChildDto);
}
