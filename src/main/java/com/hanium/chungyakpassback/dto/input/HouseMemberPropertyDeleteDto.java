package com.hanium.chungyakpassback.dto.input;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseMemberPropertyDeleteDto {
    List<HouseMemberPropertyDeleteDto> houseMemberPropertyDeleteDtoList;

    Long houseMemberPropertyId;
}