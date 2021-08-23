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
@Table(name = "std_priority_numeber_payments")
public class PriorityNumberPayments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_numeber_payments_id")
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
    private int number_payments;

    @Builder
    public PriorityNumberPayments(SupplyType supplyType, SpecialSupplyType specialSupplyType, Yn overheated_speculation_zone, Yn overheated_subscription_area, Yn atrophy_area, Yn metropolitanAreaYn, int number_payments) {
        this.supplyType = supplyType;
        this.specialSupplyType = specialSupplyType;
        this.overheated_speculation_zone = overheated_speculation_zone;
        this.overheated_subscription_area = overheated_subscription_area;
        this.atrophy_area = atrophy_area;
        this.metropolitanAreaYn = metropolitanAreaYn;
        this.number_payments = number_payments;
    }
}
