package com.hanium.chungyakpassback.entity.verification;

import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.apt.AptInfoTarget;
import com.hanium.chungyakpassback.entity.base.BaseTime;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.enumtype.KookminType;
import com.hanium.chungyakpassback.enumtype.Ranking;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "verification_of_special_kookmin_newly_married")
public class VerificationOfSpecialKookminNewlyMarried extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_of_special_kookmin_newly_married_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Integer americanAge; //만나이

    @Column
    private boolean meetLivingInSurroundAreaTf; //인근지역거주조건충족여부

    @Column
    private boolean accountTf; //청약통장유형조건충족여부

    @Column
    private boolean meetRecipientTf; //신혼부부공공주택적용국민주택대상자충족여부

    @Column
    private boolean meetMonthlyAverageIncomePriority; //월평균소득기준충족여부_우선공급

    @Column
    private boolean meetMonthlyAverageIncomeGeneral; //월평균소득기준충족여부_일반공급

    @Column
    private boolean meetPropertyTf; //자산기준충족여부

    @Column
    private boolean meetMarriagePeriodIn7yearsTf; // 혼인기간7년이내충족여부

    @Column
    private boolean hasMinorChildren; //미성년자녀존재여부

    @Column
    private boolean meetHomelessHouseholdMemberTf; //전세대원무주택구성원충족여부

    @Column
    private boolean meetAllHouseMemberRewinningRestrictionTf; //전세대원재당첨제한여부

    @Column
    private boolean householderTf; //세대주여부

    @Column
    private boolean meetBankbookJoinPeriodTf; //가입기간충족여부

    @Column
    private boolean meetNumberOfPaymentsTf; //납입횟수충족여부

    @Column
    private boolean restrictedAreaTf; //규제지역여부

    @Column
    private boolean secondChungyak; //2순위청약신청대상여부

    //아래는 프론트한테 받는 항목들

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_number_id")
    private AptInfo aptInfo; //아파트분양정보

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "info_target_id")
//    private AptInfoTarget aptInfoTarget; //주택형

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "info_target_id"),
            @JoinColumn(name = "id")
    })
    private AptInfoTarget aptInfoTarget; //주택형

    //아래는 프론트한테 추가로 받는 항목들

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    public Yn sibilingSupportYn; //형제자매부양여부

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    public KookminType kookminType; //국민주택종류

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    public Yn preNewMarriedYn; //예비신혼부부여부

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    public Ranking ranking; //순위

    @Builder
    public VerificationOfSpecialKookminNewlyMarried(User user, Integer americanAge, boolean meetLivingInSurroundAreaTf, boolean accountTf, boolean meetMonthlyAverageIncomePriority, boolean meetMonthlyAverageIncomeGeneral, boolean meetMarriagePeriodIn7yearsTf, boolean hasMinorChildren, boolean meetHomelessHouseholdMemberTf, boolean meetAllHouseMemberRewinningRestrictionTf, boolean householderTf, boolean meetBankbookJoinPeriodTf, boolean meetNumberOfPaymentsTf, boolean restrictedAreaTf, boolean secondChungyak, AptInfo aptInfo, AptInfoTarget aptInfoTarget) {
        this.user = user;
        this.americanAge = americanAge;
        this.meetLivingInSurroundAreaTf = meetLivingInSurroundAreaTf;
        this.accountTf = accountTf;
        this.meetMonthlyAverageIncomePriority = meetMonthlyAverageIncomePriority;
        this.meetMonthlyAverageIncomeGeneral = meetMonthlyAverageIncomeGeneral;
        this.meetMarriagePeriodIn7yearsTf = meetMarriagePeriodIn7yearsTf;
        this.hasMinorChildren = hasMinorChildren;
        this.meetHomelessHouseholdMemberTf = meetHomelessHouseholdMemberTf;
        this.meetAllHouseMemberRewinningRestrictionTf = meetAllHouseMemberRewinningRestrictionTf;
        this.householderTf = householderTf;
        this.meetBankbookJoinPeriodTf = meetBankbookJoinPeriodTf;
        this.meetNumberOfPaymentsTf = meetNumberOfPaymentsTf;
        this.restrictedAreaTf = restrictedAreaTf;
        this.secondChungyak = secondChungyak;
        this.aptInfo = aptInfo;
        this.aptInfoTarget = aptInfoTarget;
    }

    public VerificationOfSpecialKookminNewlyMarried(User user, Integer americanAge, boolean meetLivingInSurroundAreaTf, boolean accountTf, boolean meetRecipientTf, boolean meetMonthlyAverageIncomePriority, boolean meetMonthlyAverageIncomeGeneral, boolean meetPropertyTf, boolean meetMarriagePeriodIn7yearsTf, boolean hasMinorChildren, boolean meetHomelessHouseholdMemberTf, boolean meetAllHouseMemberRewinningRestrictionTf, boolean householderTf, boolean meetBankbookJoinPeriodTf, boolean meetNumberOfPaymentsTf, boolean restrictedAreaTf, boolean secondChungyak, AptInfo aptInfo, AptInfoTarget aptInfoTarget) {
        this.user = user;
        this.americanAge = americanAge;
        this.meetLivingInSurroundAreaTf = meetLivingInSurroundAreaTf;
        this.accountTf = accountTf;
        this.meetRecipientTf = meetRecipientTf;
        this.meetMonthlyAverageIncomePriority = meetMonthlyAverageIncomePriority;
        this.meetMonthlyAverageIncomeGeneral = meetMonthlyAverageIncomeGeneral;
        this.meetPropertyTf = meetPropertyTf;
        this.meetMarriagePeriodIn7yearsTf = meetMarriagePeriodIn7yearsTf;
        this.hasMinorChildren = hasMinorChildren;
        this.meetHomelessHouseholdMemberTf = meetHomelessHouseholdMemberTf;
        this.meetAllHouseMemberRewinningRestrictionTf = meetAllHouseMemberRewinningRestrictionTf;
        this.householderTf = householderTf;
        this.meetBankbookJoinPeriodTf = meetBankbookJoinPeriodTf;
        this.meetNumberOfPaymentsTf = meetNumberOfPaymentsTf;
        this.restrictedAreaTf = restrictedAreaTf;
        this.secondChungyak = secondChungyak;
        this.aptInfo = aptInfo;
        this.aptInfoTarget = aptInfoTarget;
    }
}
