package com.hanium.chungyakpassback.dto.verification;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SpecialPointOfOldParentsSupportResponseDto {
    Integer periodOfHomelessness;
    Integer bankbookJoinPeriod;
    Integer numberOfDependents;
}
