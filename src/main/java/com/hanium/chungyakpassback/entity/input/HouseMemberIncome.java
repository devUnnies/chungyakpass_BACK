package com.hanium.chungyakpassback.entity.input;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "inp_house_member_income")
public class HouseMemberIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_member_income_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_member_id")
    private HouseMember HouseMember;

    @Column
    private int averageIncome;

    @Builder
    public HouseMemberIncome(com.hanium.chungyakpassback.entity.input.HouseMember houseMember, int averageIncome) {
        HouseMember = houseMember;
        this.averageIncome = averageIncome;
    }
}
