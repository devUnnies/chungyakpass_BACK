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
    private Integer areaExcess;

    @Column
    private Integer areaBelow;

    @Column
    @Enumerated(EnumType.STRING)
    private DepositAmountRegionClassification depositAmountArea;

    @Column
    private int depositAmount;

    @Builder
    public PriorityDepositAmount(Integer areaExcess, Integer areaBelow, DepositAmountRegionClassification depositAmountArea, int depositAmount) {
        this.areaExcess = areaExcess;
        this.areaBelow = areaBelow;
        this.depositAmountArea = depositAmountArea;
        this.depositAmount = depositAmount;
    }
}
