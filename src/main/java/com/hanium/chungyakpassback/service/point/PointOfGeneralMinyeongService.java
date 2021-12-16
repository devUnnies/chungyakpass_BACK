package com.hanium.chungyakpassback.service.point;

import com.hanium.chungyakpassback.dto.point.PointOfGeneralMinyeongDto;
import com.hanium.chungyakpassback.dto.point.PointOfGeneralMinyeongResponseDto;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.input.User;

import java.util.List;

public interface PointOfGeneralMinyeongService {

    List<PointOfGeneralMinyeongResponseDto> readGeneralMinyeongResponsePointCalculations();

    PointOfGeneralMinyeongResponseDto createGeneralMinyeongPointCalculation(PointOfGeneralMinyeongDto pointOfGeneralMinyeongDto);

    Integer periodOfHomelessness(User user);

    Integer bankbookJoinPeriod(User user);

    Integer numberOfDependents(User user, AptInfo aptInfo);

    boolean bankBookVaildYn(User user);
}
