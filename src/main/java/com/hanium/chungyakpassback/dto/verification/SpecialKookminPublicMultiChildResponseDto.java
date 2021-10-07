package com.hanium.chungyakpassback.dto.verification;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialKookminPublicMultiChildResponseDto {
    Integer americanAge;
    boolean meetLivingInSurroundAreaTf;
    boolean accountTf;
    boolean meetMonthlyAverageIncomeTf;
    boolean meetPropertyTf;
    boolean meetHomelessHouseholdMembersTf;
    Integer calcMinorChildren;
    boolean householderTf;
    boolean meetAllHouseMemberNotWinningIn5yearsTf;
    boolean isRestrictedAreaTf;
    boolean meetBankJoinPeriodTf;
    boolean meetNumberOfPaymentsTf;
}