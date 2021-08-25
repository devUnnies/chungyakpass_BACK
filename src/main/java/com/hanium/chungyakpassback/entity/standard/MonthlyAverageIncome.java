package com.hanium.chungyakpassback.entity.standard;

import com.hanium.chungyakpassback.entity.enumtype.SupplyMethod;
import com.hanium.chungyakpassback.entity.enumtype.Yn;
import com.hanium.chungyakpassback.entity.enumtype.SpecialSupplyType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="std_monthly_average_income")
public class MonthlyAverageIncome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monthly_average_income_id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn applicationPublicHousingSpecialLaws;

    @Column
    @Enumerated(EnumType.STRING)
    private SpecialSupplyType specialSupplyType;

    @Column
    @Enumerated(EnumType.STRING)
    private SupplyMethod supplyMethod;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn dualIncome;

    @Column
    private Integer monthlyAverageIncomeExcess;

    @Column
    private int monthlyAverageIncomeBelow;

    @Column
    private Integer averageMonthlyIncome3peopleLessExcess;

    @Column
    private int averageMonthlyIncome3peopleLessBelow;

    @Column
    private Integer averageMonthlyIncome4peopleLessExcess;

    @Column
    private Integer averageMonthlyIncome4peopleLessBelow;

    @Column
    private Integer averageMonthlyIncome5peopleLessExcess;

    @Column
    private Integer averageMonthlyIncome5peopleLessBelow;

    @Builder
    public MonthlyAverageIncome(Yn applicationPublicHousingSpecialLaws, SpecialSupplyType specialSupplyType, SupplyMethod supplyMethod, Yn dualIncome, Integer monthlyAverageIncomeExcess, int monthlyAverageIncomeBelow, Integer averageMonthlyIncome3peopleLessExcess, int averageMonthlyIncome3peopleLessBelow, Integer averageMonthlyIncome4peopleLessExcess, Integer averageMonthlyIncome4peopleLessBelow, Integer averageMonthlyIncome5peopleLessExcess, Integer averageMonthlyIncome5peopleLessBelow) {
        this.applicationPublicHousingSpecialLaws = applicationPublicHousingSpecialLaws;
        this.specialSupplyType = specialSupplyType;
        this.supplyMethod = supplyMethod;
        this.dualIncome = dualIncome;
        this.monthlyAverageIncomeExcess = monthlyAverageIncomeExcess;
        this.monthlyAverageIncomeBelow = monthlyAverageIncomeBelow;
        this.averageMonthlyIncome3peopleLessExcess = averageMonthlyIncome3peopleLessExcess;
        this.averageMonthlyIncome3peopleLessBelow = averageMonthlyIncome3peopleLessBelow;
        this.averageMonthlyIncome4peopleLessExcess = averageMonthlyIncome4peopleLessExcess;
        this.averageMonthlyIncome4peopleLessBelow = averageMonthlyIncome4peopleLessBelow;
        this.averageMonthlyIncome5peopleLessExcess = averageMonthlyIncome5peopleLessExcess;
        this.averageMonthlyIncome5peopleLessBelow = averageMonthlyIncome5peopleLessBelow;
    }
}
