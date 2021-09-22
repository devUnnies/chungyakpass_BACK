package com.hanium.chungyakpassback.dto.verification;

import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.input.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialMinyeongPointOfNewMarriedResponseDto {
    Integer numberOfMinors;
    Integer periodOfMarriged;
    Integer bankbookPaymentsCount;
    Integer periodOfApplicableAreaResidence;
    Integer ageOfMostYoungChild;
    Integer monthOfAverageIncome;

}
