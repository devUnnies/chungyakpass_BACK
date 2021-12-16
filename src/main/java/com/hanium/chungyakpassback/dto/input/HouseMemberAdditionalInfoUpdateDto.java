package com.hanium.chungyakpassback.dto.input;

import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HouseMemberAdditionalInfoUpdateDto {

    @NotBlank
    private Long houseMemberId; //세대구성원 id

    private Yn parentsDeathYn; //부모 사망 여부

    private Yn divorceYn; //이혼 여부

    private LocalDate startDateOfSameResident; //회원 세대 거주 여부

    private LocalDate startDateOfStayOver;

    private LocalDate endDateOfStayOver;

    private Yn nowStayOverYn; //이혼 여부

}
