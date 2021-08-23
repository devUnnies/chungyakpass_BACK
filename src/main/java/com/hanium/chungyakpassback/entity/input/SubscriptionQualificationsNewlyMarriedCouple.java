package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.entity.enumtype.NewlywedType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "input_subscription_qualifications_newlyMarriedCouple")
public class SubscriptionQualificationsNewlyMarriedCouple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_qualifications_newlyMarriedCouple_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_qualifications_id")
    private SubscriptionQualifications SubscriptionQualifications;

    @Column
    @Enumerated(EnumType.STRING)
    private NewlywedType NewlywedType;

    @Column
    private int numberMinors;

    @Column
    private Integer singleParentFamilyChildAge;

    @Builder
    public SubscriptionQualificationsNewlyMarriedCouple(com.hanium.chungyakpassback.entity.input.SubscriptionQualifications subscriptionQualifications, com.hanium.chungyakpassback.entity.enumtype.NewlywedType newlywedType, int numberMinors, Integer singleParentFamilyChildAge) {
        SubscriptionQualifications = subscriptionQualifications;
        NewlywedType = newlywedType;
        this.numberMinors = numberMinors;
        this.singleParentFamilyChildAge = singleParentFamilyChildAge;
    }
}