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
    private SpecialSupplyType specialSupplyType;

    @Column
    private String housingType;

    @Column
    @Enumerated(EnumType.STRING)
    private Ranking ranking;

    @Column
    @Enumerated(EnumType.STRING)
    private Result result;

    @Column
    private Integer preliminaryNumber;

    @Column
    private LocalDate winDate;

    @Column
    @Enumerated(EnumType.STRING)
    private RaffleMethod lotteryMethod;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn cancelwinYn;

    @Builder
    public HouseMemberApplicationDetails(com.hanium.chungyakpassback.entity.input.HouseMember houseMember, String houseName, SupplyType supplyType, SpecialSupplyType specialSupplyType, String housingType, Ranking ranking, Result result, Integer preliminaryNumber, LocalDate winDate, RaffleMethod lotteryMethod, Yn cancelwinYn) {
        HouseMember = houseMember;
        this.houseName = houseName;
        this.supplyType = supplyType;
        this.specialSupplyType = specialSupplyType;
        this.housingType = housingType;
        this.ranking = ranking;
        this.result = result;
        this.preliminaryNumber = preliminaryNumber;
        this.winDate = winDate;
        this.lotteryMethod = lotteryMethod;
        this.cancelwinYn = cancelwinYn;
    }
}
