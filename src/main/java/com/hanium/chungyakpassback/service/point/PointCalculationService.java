package com.hanium.chungyakpassback.service.point;

import com.hanium.chungyakpassback.dto.point.GeneralMinyeongPointDto;
import com.hanium.chungyakpassback.dto.point.GeneralMinyeongResponsePointDto;
import com.hanium.chungyakpassback.entity.input.User;

public interface PointCalculationService {
    //int periodOfHomelessness(User user);

    GeneralMinyeongResponsePointDto recordPointCalculationService(GeneralMinyeongPointDto generalMinyeongPointDto);

    Integer periodOfHomelessness(User user);

    Integer bankbookJoinPeriod(User user);

    Integer numberOfDependents(User user, GeneralMinyeongPointDto generalMinyeongPointDto);

    boolean bankBookVaildYn(User user);
}
