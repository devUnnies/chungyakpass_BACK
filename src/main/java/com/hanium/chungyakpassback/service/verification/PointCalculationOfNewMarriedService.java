package com.hanium.chungyakpassback.service.verification;

import com.hanium.chungyakpassback.dto.verification.SpecialMinyeongPointOfNewMarriedDto;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.input.User;

public interface PointCalculationOfNewMarriedService {

    Integer numberOfMinors(User user);
    Integer periodOfMarriged(User user);
    Integer bankbookPaymentsCount(User user);
    Integer periodOfApplicableAreaResidence(User user, AptInfo aptInfo);
    Integer ageOfMostYoungChild(User user);
    Integer monthOfAverageIncome(User user);

}
