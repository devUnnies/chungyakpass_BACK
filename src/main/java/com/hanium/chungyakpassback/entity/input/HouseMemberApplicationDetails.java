package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.entity.enumtype.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "inp_house_member_application_details")
public class HouseMemberApplicationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_member_application_details_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_member_id")
    private HouseMember HouseMember;

    @Column
    private String houseName;

    @Column
    @Enumerated(EnumType.STRING)
    private SupplyType supplyType;

    @Column
    @Enumerated(EnumType.STRING)
    private SpecialSupplyType special_supply_type;

    @Column
    private String housingType;

    @Column
    @Enumerated(EnumType.STRING)
    private Ranking ranking;

    @Column
    @Enumerated(EnumType.STRING)
    private Result result;

    @Column
    private Integer preliminary_number;

    @Column
    private LocalDate winDate;

    @Column
    @Enumerated(EnumType.STRING)
    private RaffleMethod lotteryMethod;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn cancelwinYn;

    @Builder
    public HouseMemberApplicationDetails(com.hanium.chungyakpassback.entity.input.HouseMember houseMember, String houseName, SupplyType supplyType, SpecialSupplyType special_supply_type, String housingType, Ranking ranking, Result result, Integer preliminary_number, LocalDate winDate, RaffleMethod lotteryMethod, Yn cancelwinYn) {
        HouseMember = houseMember;
        this.houseName = houseName;
        this.supplyType = supplyType;
        this.special_supply_type = special_supply_type;
        this.housingType = housingType;
        this.ranking = ranking;
        this.result = result;
        this.preliminary_number = preliminary_number;
        this.winDate = winDate;
        this.lotteryMethod = lotteryMethod;
        this.cancelwinYn = cancelwinYn;
    }
}
