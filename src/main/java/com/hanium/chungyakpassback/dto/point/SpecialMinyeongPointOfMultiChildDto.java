package com.hanium.chungyakpassback.dto.point;

import com.hanium.chungyakpassback.enumtype.MultiChildHouseholdType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialMinyeongPointOfMultiChildDto {
    Integer notificationNumber;
    MultiChildHouseholdType multiChildHouseholdType;

}
