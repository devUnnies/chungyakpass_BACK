package com.hanium.chungyakpassback.entity.standard;

import com.hanium.chungyakpassback.entity.enumtype.Yn;
import com.hanium.chungyakpassback.entity.enumtype.BankbookType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "std_bankbook")
public class Bankbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bankbook_id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private BankbookType bankbookType;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn national_housing_supply_possible;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn private_housing_supplyIs_possible;

    @Column
    private Integer restrictionSaleArea;

    @Builder
    public Bankbook(BankbookType bankbookType, Yn national_housing_supply_possible, Yn private_housing_supplyIs_possible, Integer restrictionSaleArea) {
        this.bankbookType = bankbookType;
        this.national_housing_supply_possible = national_housing_supply_possible;
        this.private_housing_supplyIs_possible = private_housing_supplyIs_possible;
        this.restrictionSaleArea = restrictionSaleArea;
    }
}
