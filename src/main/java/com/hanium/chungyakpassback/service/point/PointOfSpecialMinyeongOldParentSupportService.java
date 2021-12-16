package com.hanium.chungyakpassback.service.point;

import com.hanium.chungyakpassback.dto.point.PointOfSpecialMinyeongOldParentsSupportDto;
import com.hanium.chungyakpassback.dto.point.PointOfSpecialMinyeongOldParentsSupportResponseDto;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.input.User;

import java.util.List;

public interface PointOfSpecialMinyeongOldParentSupportService {

    List<PointOfSpecialMinyeongOldParentsSupportResponseDto> readOldParentsSupportPointCalculations();

    PointOfSpecialMinyeongOldParentsSupportResponseDto createOldParentsSupportPointCalculation(PointOfSpecialMinyeongOldParentsSupportDto pointOfSpecialMinyeongOldParentsSupportDto);

    Integer periodOfHomelessness(User user);

    Integer bankbookJoinPeriod(User user);

    Integer numberOfDependents(User user, AptInfo aptInfo);

    boolean bankBookVaildYn(User user);
}
