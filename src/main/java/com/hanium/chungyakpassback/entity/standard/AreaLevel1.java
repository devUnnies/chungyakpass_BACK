package com.hanium.chungyakpassback.entity.standard;

import com.hanium.chungyakpassback.entity.enumtype.Yn;
import com.hanium.chungyakpassback.entity.enumtype.DepositAmountRegionClassification;
import com.hanium.chungyakpassback.entity.enumtype.AddressLevel1;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "std_area_leve1")
public class AreaLevel1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_level1_id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private AddressLevel1 area_level1;

    @Column
    private int nearbyArea;

    @Column
    @Enumerated(EnumType.STRING)
    private DepositAmountRegionClassification depositAmountArea;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn metropolitanArea;

    @Builder
    public AreaLevel1(AddressLevel1 area_level1, int nearbyArea, DepositAmountRegionClassification depositAmountArea, Yn metropolitanArea) {
        this.area_level1 = area_level1;
        this.nearbyArea = nearbyArea;
        this.depositAmountArea = depositAmountArea;
        this.metropolitanArea = metropolitanArea;
    }
}
