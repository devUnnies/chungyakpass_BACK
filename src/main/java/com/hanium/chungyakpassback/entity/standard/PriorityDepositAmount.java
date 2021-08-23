package com.hanium.chungyakpassback.entity.standard;

import com.hanium.chungyakpassback.entity.enumtype.DepositAmountRegionClassification;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="std_priority_deposit_amount")
public class PriorityDepositAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_deposit_amount_id")
    private Long id;

    @Column
    private Integer area_excess;

    @Column
    private Integer area_below;

    @Column
    @Enumerated(EnumType.STRING)
    private DepositAmountRegionClassification depositAmountArea;

    @Column
    private int depositAmount;

    @Builder
    public PriorityDepositAmount(Integer area_excess, Integer area_below, DepositAmountRegionClassification depositAmountArea, int depositAmount) {
        this.area_excess = area_excess;
        this.area_below = area_below;
        this.depositAmountArea = depositAmountArea;
        this.depositAmount = depositAmount;
    }
}
