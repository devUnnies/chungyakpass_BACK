package com.hanium.chungyakpassback.dto.verification;

import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.apt.AptInfoTarget;
import com.hanium.chungyakpassback.entity.input.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialPrivateFirstLifeResponseDto {
    boolean targetHousingType;
    boolean targetHouseAmount;
    boolean monthOfAverageIncome;
    boolean HomelessYn;
    boolean vaildObject;


}
