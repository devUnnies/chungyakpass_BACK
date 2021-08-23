package com.hanium.chungyakpassback.entity.standard;

import com.hanium.chungyakpassback.entity.enumtype.SupplyType;
import com.hanium.chungyakpassback.entity.enumtype.Yn;
import com.hanium.chungyakpassback.entity.enumtype.SpecialSupplyType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "std_priority_subscription_period")
public class PrioritySubscriptionPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_subscription_period_id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private SupplyType supplyType;

    @Column
    @Enumerated(EnumType.STRING)
    private SpecialSupplyType specialSupplyType;


    @Column
    @Enumerated(EnumType.STRING)
    private Yn overheated_speculation_zone;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn overheated_subscription_area;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn atrophy_area;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn metropolitanAreaYn;

    @Column
    private int subscription_period;

    @Builder
    public PrioritySubscriptionPeriod(SupplyType supplyType, SpecialSupplyType specialSupplyType, Yn overheated_speculation_zone, Yn overheated_subscription_area, Yn atrophy_area, Yn metropolitanAreaYn, int subscription_period) {
        this.supplyType = supplyType;
        this.specialSupplyType = specialSupplyType;
        this.overheated_speculation_zone = overheated_speculation_zone;
        this.overheated_subscription_area = overheated_subscription_area;
        this.atrophy_area = atrophy_area;
        this.metropolitanAreaYn = metropolitanAreaYn;
        this.subscription_period = subscription_period;
    }
}
