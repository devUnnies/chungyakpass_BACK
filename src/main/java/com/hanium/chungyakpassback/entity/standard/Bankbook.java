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
    private Yn nationalHousingSupplyPossible;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn privateHousingSupplyIsPossible;

    @Column
    private Integer restrictionSaleArea;

    @Builder
    public Bankbook(BankbookType bankbookType, Yn nationalHousingSupplyPossible, Yn privateHousingSupplyIsPossible, Integer restrictionSaleArea) {
        this.bankbookType = bankbookType;
        this.nationalHousingSupplyPossible = nationalHousingSupplyPossible;
        this.privateHousingSupplyIsPossible = privateHousingSupplyIsPossible;
        this.restrictionSaleArea = restrictionSaleArea;
    }
}
