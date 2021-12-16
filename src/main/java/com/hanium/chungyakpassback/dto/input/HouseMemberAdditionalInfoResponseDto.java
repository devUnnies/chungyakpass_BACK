package com.hanium.chungyakpassback.dto.input;

import com.hanium.chungyakpassback.entity.input.HouseMember;
import com.hanium.chungyakpassback.entity.input.HouseMemberAdditionalInfo;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HouseMemberAdditionalInfoResponseDto {

    private Long houseMemberAdditionalInfoId; //세대구성원 추가정보 id

    private Long houseMemberId; //세대구성원 id

    private Yn parentsDeathYn; //부모 사망 여부

    private Yn divorceYn; //이혼 여부

    private LocalDate startDateOfSameResident; //회원 세대 거주 시작일

    private LocalDate startDateOfStayOver; //해외체류시작일

    private LocalDate endDateOfStayOver; //해외체류종료일

    private Yn nowStayOverYn;


    public HouseMemberAdditionalInfoResponseDto(HouseMemberAdditionalInfo houseMemberAdditionalInfo) {
        this.houseMemberAdditionalInfoId = houseMemberAdditionalInfo.getId();
        this.houseMemberId = houseMemberAdditionalInfo.getHouseMember().getId();
        this.parentsDeathYn = houseMemberAdditionalInfo.getParentsDeathYn();
        this.divorceYn = houseMemberAdditionalInfo.getDivorceYn();
        this.startDateOfSameResident = houseMemberAdditionalInfo.getStartDateOfSameResident();
        this.startDateOfStayOver = houseMemberAdditionalInfo.getStartDateOfStayOver();
        this.endDateOfStayOver = houseMemberAdditionalInfo.getEndDateOfStayOver();
        this.nowStayOverYn = houseMemberAdditionalInfo.getNowStayOverYn();
    }
}