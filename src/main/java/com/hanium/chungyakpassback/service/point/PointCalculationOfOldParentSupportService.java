package com.hanium.chungyakpassback.service.point;

import com.hanium.chungyakpassback.dto.point.SpecialMinyeongPointOfOldParentsSupportResponseDto;
import com.hanium.chungyakpassback.dto.point.SpecialPointOfOldParentsSupportDto;
import com.hanium.chungyakpassback.entity.input.User;

public interface PointCalculationOfOldParentSupportService {
    SpecialMinyeongPointOfOldParentsSupportResponseDto recordPointCalculationOfNewMarriedService(SpecialPointOfOldParentsSupportDto specialPointOfOldParentsSupportDto);

    Integer periodOfHomelessness(User user);

    Integer bankbookJoinPeriod(User user);

    Integer numberOfDependents(User user, SpecialPointOfOldParentsSupportDto specialPointOfOldParentsSupportDto);

    boolean bankBookVaildYn(User user);
}
