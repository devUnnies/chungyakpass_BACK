package com.hanium.chungyakpassback.dto.verification;

import com.hanium.chungyakpassback.enumtype.MultiChildHouseholdType;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialPointOfMultiChildDto {
    Integer notificationNumber;
    MultiChildHouseholdType multiChildHouseholdType;

}
