package com.hanium.chungyakpassback.dto.verification;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralMultiChildResponseDto {

    Integer americanAge;
    boolean meetLivingInSurroundAreaTf;
    boolean accountTf;
    boolean meetHomelessHouseholdMembersTf;
    boolean meet3MoreMinorChildrenTf;
    boolean householderTf;
    boolean meetAllHouseMemberNotWinningIn5yearsTf;
    boolean isRestrictedAreaTf;
    boolean hasHouse;
    boolean specialTf;
    boolean meetDepositTf;
    boolean meetBankbookJoinPeriodTf;
}
