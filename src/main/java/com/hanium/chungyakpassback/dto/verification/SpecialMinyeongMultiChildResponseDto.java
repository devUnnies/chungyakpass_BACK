package com.hanium.chungyakpassback.dto.verification;

import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfMultiChild;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialMinyeongMultiChildResponseDto {

    Integer americanAge;
    boolean meetLivingInSurroundAreaTf;
    boolean accountTf;
    boolean meetHomelessHouseholdMembersTf;
    Integer calcMinorChildren;
    boolean householderTf;
    boolean meetAllHouseMemberNotWinningIn5yearsTf;
    boolean isRestrictedAreaTf;
    boolean meetHouseHavingLessThan2AptTf;
    boolean isPriorityApt;
    boolean meetDepositTf;
    boolean meetBankbookJoinPeriodTf;

}
