package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.entity.enumtype.MultiChildHouseholdType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="inp_subscription_qualifications_multiChildHousehold")
public class SubscriptionQualificationsMultiChildHousehold {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_qualifications_multiChildHousehold_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_qualifications_id")
    private SubscriptionQualifications subscriptionQualifications;

    @Column
    private int numberMinors;

    @Column
    private int numberInfantsChildren;

    @Column
    @Enumerated(EnumType.STRING)
    private MultiChildHouseholdType multiChildHouseholdType;

    @Builder
    public SubscriptionQualificationsMultiChildHousehold(com.hanium.chungyakpassback.entity.input.SubscriptionQualifications subscriptionQualifications, int numberMinors, int numberInfantsChildren, MultiChildHouseholdType multiChildHouseholdType) {
        this.subscriptionQualifications = subscriptionQualifications;
        this.numberMinors = numberMinors;
        this.numberInfantsChildren = numberInfantsChildren;
        this.multiChildHouseholdType = multiChildHouseholdType;
    }
}
