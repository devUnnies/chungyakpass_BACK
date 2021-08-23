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
    private Yn application_public_housing_specialLaws;

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
    private Integer monthly_average_income_percentage_excess;

    @Column
    private int monthly_average_income_percentage_below;

    @Column
    private Integer averageMonthlyIncome_3peopleLess_excess;

    @Column
    private int averageMonthlyIncome_3peopleLess_below;

    @Column
    private Integer averageMonthlyIncome_4peopleLess_excess;

    @Column
    private Integer averageMonthlyIncome_4peopleLess_below;

    @Column
    private Integer averageMonthlyIncome_5peopleLess_excess;

    @Column
    private Integer averageMonthlyIncome_5peopleLess_below;

    @Builder
    public MonthlyAverageIncome(Yn application_public_housing_specialLaws, SpecialSupplyType specialSupplyType, SupplyMethod supplyMethod, Yn dualIncome, Integer monthly_average_income_percentage_excess, int monthly_average_income_percentage_below, Integer averageMonthlyIncome_3peopleLess_excess, int averageMonthlyIncome_3peopleLess_below, Integer averageMonthlyIncome_4peopleLess_excess, Integer averageMonthlyIncome_4peopleLess_below, Integer averageMonthlyIncome_5peopleLess_excess, Integer averageMonthlyIncome_5peopleLess_below) {
        this.application_public_housing_specialLaws = application_public_housing_specialLaws;
        this.specialSupplyType = specialSupplyType;
        this.supplyMethod = supplyMethod;
        this.dualIncome = dualIncome;
        this.monthly_average_income_percentage_excess = monthly_average_income_percentage_excess;
        this.monthly_average_income_percentage_below = monthly_average_income_percentage_below;
        this.averageMonthlyIncome_3peopleLess_excess = averageMonthlyIncome_3peopleLess_excess;
        this.averageMonthlyIncome_3peopleLess_below = averageMonthlyIncome_3peopleLess_below;
        this.averageMonthlyIncome_4peopleLess_excess = averageMonthlyIncome_4peopleLess_excess;
        this.averageMonthlyIncome_4peopleLess_below = averageMonthlyIncome_4peopleLess_below;
        this.averageMonthlyIncome_5peopleLess_excess = averageMonthlyIncome_5peopleLess_excess;
        this.averageMonthlyIncome_5peopleLess_below = averageMonthlyIncome_5peopleLess_below;
    }
}
