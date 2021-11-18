package com.hanium.chungyakpassback.dto.record;

import com.hanium.chungyakpassback.dto.point.SpecialMinyeongPointOfNewMarriedResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserPointRecordDto {
    List<SpecialMinyeongPointOfNewMarriedResponseDto> specialMinyeongPointOfNewMarriedResponseDtos;

}
