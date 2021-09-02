package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.dto.input.HouseMemberDto;
import com.hanium.chungyakpassback.dto.input.HouseMemberResponseDto;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="inp_house_member")
public class HouseMember {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "house_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house; //세대

//    @Column
//    @Enumerated(EnumType.STRING)
//    private Yn isHouseholderYn; //세대주여부

    @Column
    private String name; //이름

    @Column
    private LocalDate birthDay; //생년월일

    @Column
    @Enumerated(EnumType.STRING)
    private Yn foreignerYn; //외국인여부

    @Column
    @Enumerated(EnumType.STRING)
    private Yn soldierYn; //장기복무중인군인여부

    @Column
    private LocalDate marriageDate; //혼인신고일

    @Column
    private LocalDate homelessStartDate; //무주택시작일

    @Column
    private LocalDate transferDate; //전입신고일

    @Column
    private Integer income; //월평균소득


    @Builder
    public HouseMember(House house, String name, LocalDate birthDay, Yn foreignerYn, Yn soldierYn, LocalDate marriageDate, LocalDate homelessStartDate, LocalDate transferDate, Integer income) {
        this.house = house;
        this.name = name;
        this.birthDay = birthDay;
        this.foreignerYn = foreignerYn;
        this.soldierYn = soldierYn;
        this.marriageDate = marriageDate;
        this.homelessStartDate = homelessStartDate;
        this.transferDate = transferDate;
        this.income = income;
    }

    public HouseMember updateHouseMember(HouseMemberDto houseMemberDto){
        this.name = houseMemberDto.getName();
        this.birthDay = houseMemberDto.getBirthDay();
        this.foreignerYn = houseMemberDto.getForeignerYn();
        this.soldierYn = houseMemberDto.getSoldierYn();
        this.marriageDate = houseMemberDto.getMarriageDate();
        this.homelessStartDate = houseMemberDto.getHomelessStartDate();
        this.transferDate = houseMemberDto.getTransferDate();
        this.income = houseMemberDto.getIncome();
        return this;
    }


}