package com.hanium.chungyakpassback.dto.input;

import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseMemberChungyakRestrictionUpdateDto {

//    private List<HouseMemberChungyakRestrictionUpdateDto> houseMemberChungyakRestrictionUpdateDtoList;

//    private Long houseMemberChungyakRestrictionId;

    private Long houseMemberChungyakId; //세대구성원청약신청내역id

    private LocalDate reWinningRestrictedDate; //재당첨제한일

    private Yn specialSupplyRestrictedYn; //특별공급제한여부

    private LocalDate unqualifiedSubscriberRestrictedDate; //부적격당첨자제한일

    private LocalDate regulatedAreaFirstPriorityRestrictedDate; //투기과열지구청약과열지역1순위제한일

    private LocalDate additionalPointSystemRestrictedDate; //가점제당첨제한일

}
