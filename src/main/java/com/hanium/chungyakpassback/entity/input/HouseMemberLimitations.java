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
    private LocalDate reWinningRestriction;

    @Column
    @Enumerated(EnumType.STRING)
    private SpecialSupplyLimit specialSupplyLimit;

    @Column
    private LocalDate unqualifiedSubscriberLimit;

    @Column
    private LocalDate firstPlaceLimit;

    @Column
    private LocalDate pointSystemWinningLimit;

    @Builder
    public HouseMemberLimitations(com.hanium.chungyakpassback.entity.input.HouseMember houseMember, com.hanium.chungyakpassback.entity.input.HouseMemberApplicationDetails houseMemberApplicationDetails, LocalDate windDate, LocalDate reWinningRestriction, SpecialSupplyLimit specialSupplyLimit, LocalDate unqualifiedSubscriberLimit, LocalDate firstPlaceLimit, LocalDate pointSystemWinningLimit) {
        HouseMember = houseMember;
        HouseMemberApplicationDetails = houseMemberApplicationDetails;
        this.windDate = windDate;
        this.reWinningRestriction = reWinningRestriction;
        this.specialSupplyLimit = specialSupplyLimit;
        this.unqualifiedSubscriberLimit = unqualifiedSubscriberLimit;
        this.firstPlaceLimit = firstPlaceLimit;
        this.pointSystemWinningLimit = pointSystemWinningLimit;
    }
}
