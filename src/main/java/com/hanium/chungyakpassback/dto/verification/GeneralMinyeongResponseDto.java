package com.hanium.chungyakpassback.dto.verification;

import com.hanium.chungyakpassback.entity.record.GeneralMinyeongVerification;
import com.hanium.chungyakpassback.enumtype.Ranking;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralMinyeongResponseDto {

    private Long verificationRecordGeneralMinyeongId;
    private boolean meetLivingInSurroundAreaTf;
    private boolean accountTf;
    private Integer americanAge;
    private boolean householderTf;
    private boolean restrictedAreaTf;
    private boolean meetAllHouseMemberNotWinningIn5yearsTf;
    private boolean meetAllHouseMemberRewinningRestrictionTf;
    private boolean meetHouseHavingLessThan2AptTf;
    private boolean meetBankbookJoinPeriodTf;
    private boolean meetDepositTf;
    private boolean priorityAptTf;
    public Yn sibilingSupportYn; //형제자매부양여부
    public Ranking ranking; //순위
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public GeneralMinyeongResponseDto(GeneralMinyeongVerification generalMinyeongVerification) {
        this.verificationRecordGeneralMinyeongId = generalMinyeongVerification.getId();
        this.meetLivingInSurroundAreaTf = generalMinyeongVerification.isMeetLivingInSurroundAreaTf();
        this.accountTf = generalMinyeongVerification.isAccountTf();
        this.americanAge = generalMinyeongVerification.getAmericanAge();
        this.householderTf = generalMinyeongVerification.isHouseholderTf();
        this.restrictedAreaTf = generalMinyeongVerification.isRestrictedAreaTf();
        this.meetAllHouseMemberNotWinningIn5yearsTf = generalMinyeongVerification.isMeetAllHouseMemberNotWinningIn5yearsTf();
        this.meetAllHouseMemberRewinningRestrictionTf = generalMinyeongVerification.isMeetAllHouseMemberRewinningRestrictionTf();
        this.meetHouseHavingLessThan2AptTf = generalMinyeongVerification.isMeetHouseHavingLessThan2AptTf();
        this.meetBankbookJoinPeriodTf = generalMinyeongVerification.isMeetBankbookJoinPeriodTf();
        this.meetDepositTf = generalMinyeongVerification.isMeetDepositTf();
        this.priorityAptTf = generalMinyeongVerification.isPriorityApt();
        this.sibilingSupportYn = generalMinyeongVerification.getSibilingSupportYn();
        this.ranking = generalMinyeongVerification.getRanking();
        this.createdDate = generalMinyeongVerification.getCreatedDate();
        this.modifiedDate = generalMinyeongVerification.getModifiedDate();
    }
}
