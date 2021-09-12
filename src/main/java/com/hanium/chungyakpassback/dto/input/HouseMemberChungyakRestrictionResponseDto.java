package com.hanium.chungyakpassback.dto.input;

import com.hanium.chungyakpassback.entity.input.HouseMemberChungyak;
import com.hanium.chungyakpassback.entity.input.HouseMemberChungyakRestriction;
import com.hanium.chungyakpassback.enumtype.Yn;
import com.hanium.chungyakpassback.repository.input.HouseMemberChungyakRestrictionRepository;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseMemberChungyakRestrictionResponseDto {

    private Long id; //세대구성원청약제한사항id

    private Long houseMemberChungyakId; //세대구성원청약신청내역id

    private LocalDate reWinningRestrictedDate; //재당첨제한일

    private Yn specialSupplyRestrictedYn; //특별공급제한여부

    private LocalDate unqualifiedSubscriberRestrictedDate; //부적격당첨자제한일

    private LocalDate regulatedAreaFirstPriorityRestrictedDate; //투기과열지구청약과열지역1순위제한일

    private LocalDate additionalPointSystemRestrictedDate; //가점제당첨제한일

    @Builder
    public HouseMemberChungyakRestrictionResponseDto(HouseMemberChungyakRestriction houseMemberChungyakRestriction){
        this.id = houseMemberChungyakRestriction.getId();
        this.houseMemberChungyakId = houseMemberChungyakRestriction.getHouseMemberChungyak().getId();
        this.reWinningRestrictedDate = houseMemberChungyakRestriction.getReWinningRestrictedDate();
        this.specialSupplyRestrictedYn = houseMemberChungyakRestriction.getSpecialSupplyRestrictedYn();
        this.unqualifiedSubscriberRestrictedDate = houseMemberChungyakRestriction.getUnqualifiedSubscriberRestrictedDate();
        this.regulatedAreaFirstPriorityRestrictedDate = houseMemberChungyakRestriction.getRegulatedAreaFirstPriorityRestrictedDate();
        this.additionalPointSystemRestrictedDate = houseMemberChungyakRestriction.getAdditionalPointSystemRestrictedDate();
    }
}
