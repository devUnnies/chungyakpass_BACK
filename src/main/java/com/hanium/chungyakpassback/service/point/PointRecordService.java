package com.hanium.chungyakpassback.service.point;

import com.hanium.chungyakpassback.dto.point.*;
import com.hanium.chungyakpassback.dto.record.UserPointRecordDto;

import java.util.List;

public interface PointRecordService {

    UserPointRecordDto readAllUserPointRecord();
    List<GeneralMinyeongResponsePointDto>  recordGeneralMinyeongResponsePoint();
    List<SpecialMinyeongPointOfNewMarriedResponseDto> recordSpecialMinyeongPointOfNewMarried();
    List<SpecialMinyeongPointOfSingleParentsResponseDto> recordSpecialMinyeongPointOfSingleParents();
    List<SpecialMinyeongPointOfMultiChildResponseDto> recordSpecialMinyeongPointOfMultiChild();
    List<SpecialMinyeongPointOfOldParentsSupportResponseDto> recordSpecialMinyeongPointOfOldParentsSupport();




}

