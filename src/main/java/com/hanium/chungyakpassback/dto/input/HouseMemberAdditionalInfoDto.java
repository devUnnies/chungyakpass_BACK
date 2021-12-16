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
public class HouseMemberAdditionalInfoDto {

    @NotBlank
    private Long houseMemberId;  //세대구성원 id

    private Yn parentsDeathYn; //부모 사망 여부

    private Yn divorceYn; //이혼 여부

    private LocalDate startDateOfSameResident; //회원 세대 거주 시작일

    private LocalDate startDateOfStayOver;

    private LocalDate endDateOfStayOver;

    private Yn nowStayOverYn;

    public HouseMemberAdditionalInfo toEntity(HouseMember houseMember){
        return HouseMemberAdditionalInfo.builder()
                .houseMember(houseMember)
                .parentsDeathYn(parentsDeathYn)
                .divorceYn(divorceYn)
                .startDateOfSameResident(startDateOfSameResident)
                .startDateOfStayOver(startDateOfStayOver)
                .endDateOfStayOver(endDateOfStayOver)
                .nowStayOverYn(nowStayOverYn)
                .build();
    }
}