package com.hanium.chungyakpassback.dto.verification;

import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.input.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialPointOfMultiChildResponseDto {
    Integer numberOfChild;
    Integer numberOfChildUnder6Year;
    Integer bankbookJoinPeriod;
    Integer periodOfApplicableAreaResidence;
    Integer periodOfHomelessness;
    Integer generationComposition;
}
