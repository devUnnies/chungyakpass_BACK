package com.hanium.chungyakpassback.dto.verification;

import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.enumtype.SpecialSupply;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralMinyeongPointDto {
    Long houseMemberId;
    Yn parentsDeathYn;
    Yn divorceYn;
    Yn sameResidentRegistrationYn;
    Yn stayOverYn;
    Yn nowStayOverYn;

    private List<GeneralMinyeongPointDto> generalMinyeongPointDtoList;

    public List<GeneralMinyeongPointDto> getGeneralMinyeongPointDtoList() {
        return generalMinyeongPointDtoList;
    }


}
