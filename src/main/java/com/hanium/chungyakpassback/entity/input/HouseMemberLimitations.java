package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.entity.enumtype.SpecialSupplyLimit;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "inp_house_member_limitations")
public class HouseMemberLimitations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_member_limitations_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_member_id")
    private HouseMember HouseMember;

    @ManyToOne
    @JoinColumn(name = "house_member_application_details_id")
    private HouseMemberApplicationDetails HouseMemberApplicationDetails;

    @Column
    private LocalDate windDate;

    @Column
    private LocalDate reWinning_restriction;

    @Column
    @Enumerated(EnumType.STRING)
    private SpecialSupplyLimit special_supply_limit;

    @Column
    private LocalDate unqualified_subscriber_limit;

    @Column
    private LocalDate firstPlaceLimit;

    @Column
    private LocalDate additionalPointSystem_winningLimit;

    @Builder
    public HouseMemberLimitations(com.hanium.chungyakpassback.entity.input.HouseMember houseMember, com.hanium.chungyakpassback.entity.input.HouseMemberApplicationDetails houseMemberApplicationDetails, LocalDate windDate, LocalDate reWinning_restriction, SpecialSupplyLimit special_supply_limit, LocalDate unqualified_subscriber_limit, LocalDate firstPlaceLimit, LocalDate additionalPointSystem_winningLimit) {
        HouseMember = houseMember;
        HouseMemberApplicationDetails = houseMemberApplicationDetails;
        this.windDate = windDate;
        this.reWinning_restriction = reWinning_restriction;
        this.special_supply_limit = special_supply_limit;
        this.unqualified_subscriber_limit = unqualified_subscriber_limit;
        this.firstPlaceLimit = firstPlaceLimit;
        this.additionalPointSystem_winningLimit = additionalPointSystem_winningLimit;
    }
}
