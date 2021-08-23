package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.entity.enumtype.NonResidentialType;
import com.hanium.chungyakpassback.entity.enumtype.Yn;
import com.hanium.chungyakpassback.entity.enumtype.PropertyType;
import com.hanium.chungyakpassback.entity.enumtype.ResidentialType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "inp_house_member_property")
public class HouseMemberProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_member_property_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_member_id")
    private HouseMember HouseMember;


    @Column
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn rightSaleYn;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn residentialYn;

    @Column
    @Enumerated(EnumType.STRING)
    private ResidentialType residentialType;

    @Column
    @Enumerated(EnumType.STRING)
    private NonResidentialType nonResidentialType;

    @Column
    private LocalDate acquisitionDate;

    @Column
    private LocalDate dispositionDate;

    @Column
    private Integer dedicatedArea;

    @Column
    private Integer amount;

    @Column
    private LocalDate taxBaseDate;

    @Builder
    public HouseMemberProperty(com.hanium.chungyakpassback.entity.input.HouseMember houseMember, PropertyType propertyType, Yn rightSaleYn, Yn residentialYn, ResidentialType residentialType, NonResidentialType nonResidentialType, LocalDate acquisitionDate, LocalDate dispositionDate, Integer dedicatedArea, Integer amount, LocalDate taxBaseDate) {
        HouseMember = houseMember;
        this.propertyType = propertyType;
        this.rightSaleYn = rightSaleYn;
        this.residentialYn = residentialYn;
        this.residentialType = residentialType;
        this.nonResidentialType = nonResidentialType;
        this.acquisitionDate = acquisitionDate;
        this.dispositionDate = dispositionDate;
        this.dedicatedArea = dedicatedArea;
        this.amount = amount;
        this.taxBaseDate = taxBaseDate;
    }
}
