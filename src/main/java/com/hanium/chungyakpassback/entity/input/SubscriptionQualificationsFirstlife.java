package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.entity.enumtype.Yn;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "inp_subscription_qualifications_firstlife")
public class SubscriptionQualificationsFirstlife {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_qualifications_firstlife_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_qualifications_id")
    private SubscriptionQualifications subscriptionQualifications;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn incomeTax5YearsMoreYn;

    @Column
    private int savingsAmount;

    @Builder
    public SubscriptionQualificationsFirstlife(com.hanium.chungyakpassback.entity.input.SubscriptionQualifications subscriptionQualifications, Yn incomeTax5YearsMoreYn, int savingsAmount) {
        this.subscriptionQualifications = subscriptionQualifications;
        this.incomeTax5YearsMoreYn = incomeTax5YearsMoreYn;
        this.savingsAmount = savingsAmount;
    }
}
