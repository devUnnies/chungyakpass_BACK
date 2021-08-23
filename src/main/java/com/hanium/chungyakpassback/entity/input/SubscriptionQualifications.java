package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.entity.enumtype.SupplyType;
import com.hanium.chungyakpassback.entity.enumtype.Ranking;
import com.hanium.chungyakpassback.entity.enumtype.Yn;
import com.hanium.chungyakpassback.entity.enumtype.SpecialSupplyType;
import com.hanium.chungyakpassback.entity.standard.AptInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inp_subscription_qualifications")
public class SubscriptionQualifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_qualifications_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_number_id")
    private AptInfo AptInfo;

    @Column
    @Enumerated(EnumType.STRING)
    private SupplyType supplyType;

    @Column
    @Enumerated(EnumType.STRING)
    private SpecialSupplyType specialSupplyType;

    @Column
    @Enumerated(EnumType.STRING)
    private Ranking ranking;

    @Column
    private String housingType;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn housingPrescription;

    @Column
    private int dependent;

    @Column
    private int subscriptionPeriod;

    @Column
    private boolean result_subscriptionEligibility;

    @Column
    private int result_score;

    @Builder
    public SubscriptionQualifications(User user, AptInfo AptInfo, SupplyType supplyType, SpecialSupplyType specialSupplyType, Ranking ranking, String housingType, Yn housingPrescription, int dependent, int subscriptionPeriod, boolean result_subscriptionEligibility, int result_score) {
        this.user = user;
        this.AptInfo = AptInfo;
        this.supplyType = supplyType;
        this.specialSupplyType = specialSupplyType;
        this.ranking = ranking;
        this.housingType = housingType;
        this.housingPrescription = housingPrescription;
        this.dependent = dependent;
        this.subscriptionPeriod = subscriptionPeriod;
        this.result_subscriptionEligibility = result_subscriptionEligibility;
        this.result_score = result_score;
    }
}
