package com.hanium.chungyakpassback.dto.record;

import com.hanium.chungyakpassback.dto.point.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserPointRecordDto {
    List<GeneralMinyeongResponsePointDto> generalMinyeongResponsePointDtos;
    List<SpecialMinyeongPointOfNewMarriedResponseDto> specialMinyeongPointOfNewMarriedResponseDtos;
    List<SpecialMinyeongPointOfSingleParentsResponseDto> specialMinyeongPointOfSingleParentsResponseDtos;
    List<SpecialMinyeongPointOfMultiChildResponseDto> specialMinyeongPointOfMultiChildResponseDtos;
    List<SpecialMinyeongPointOfOldParentsSupportResponseDto> specialMinyeongPointOfOldParentsSupportResponseDtos;
}
