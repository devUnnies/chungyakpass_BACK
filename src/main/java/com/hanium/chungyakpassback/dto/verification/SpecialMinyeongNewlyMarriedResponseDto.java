package com.hanium.chungyakpassback.dto.verification;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialMinyeongNewlyMarriedResponseDto {

    Integer americanAge;
    boolean meetLivingInSurroundAreaTf;
    boolean accountTf;
    boolean meetMonthlyAverageIncomePriorityTf;
    boolean meetMonthlyAverageIncomeGeneralTf;
    boolean meetMarriagePeriodIn7yearsTf;
    boolean hasMinorChildren;
    boolean is2ndChungyak;
    boolean meetHomelessHouseholdMembersTf;
    boolean householderTf;
    boolean isRestrictedAreaTf;
    boolean meetBankbookJoinPeriodTf;
    boolean meetDepositTf;

}
