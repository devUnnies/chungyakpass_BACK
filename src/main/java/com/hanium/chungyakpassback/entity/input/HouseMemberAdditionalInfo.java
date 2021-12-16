package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.dto.input.HouseMemberAdditionalInfoResponseDto;
import com.hanium.chungyakpassback.dto.input.HouseMemberAdditionalInfoUpdateDto;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "inp_house_member_additional_info")
public class HouseMemberAdditionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_member_additional_info_id")
    private Long id; //세대구성원추가정보id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_member_id")
    private HouseMember houseMember; //세대구성원

    @Column
    Yn parentsDeathYn; //부모사망여부

    @Column
    Yn divorceYn; //이혼 여부

    @Column
    private LocalDate startDateOfSameResident; //회원 세대 거주 여부

    @Column
    private LocalDate startDateOfStayOver;

    @Column
    private LocalDate endDateOfStayOver;

    @Column
    Yn nowStayOverYn;



    public HouseMemberAdditionalInfo updateHouseMemberAdditionalInfo(HouseMember houseMember, HouseMemberAdditionalInfoUpdateDto houseMemberAdditionalInfoUpdateDto){
        this.houseMember = houseMember;
        this.parentsDeathYn = houseMemberAdditionalInfoUpdateDto.getParentsDeathYn();
        this.divorceYn = houseMemberAdditionalInfoUpdateDto.getDivorceYn();
        this.startDateOfSameResident = houseMemberAdditionalInfoUpdateDto.getStartDateOfSameResident();
        this.startDateOfStayOver = houseMemberAdditionalInfoUpdateDto.getStartDateOfStayOver();
        this.endDateOfStayOver = houseMemberAdditionalInfoUpdateDto.getEndDateOfStayOver();
        this.nowStayOverYn = houseMemberAdditionalInfoUpdateDto.getNowStayOverYn();
        return this;
    }

}
