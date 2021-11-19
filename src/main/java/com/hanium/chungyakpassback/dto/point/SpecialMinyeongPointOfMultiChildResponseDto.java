package com.hanium.chungyakpassback.dto.point;

import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfMultiChild;
import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfSingleParents;
import com.hanium.chungyakpassback.enumtype.MultiChildHouseholdType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialMinyeongPointOfMultiChildResponseDto {
    Long id;
    Integer aptNotificationNumber;
    MultiChildHouseholdType multiChildHouseholdType;
    Integer numberOfChild;
    Integer numberOfChildUnder6Year;
    Integer bankbookJoinPeriod;
    Integer periodOfApplicableAreaResidence;
    Integer periodOfHomelessness;
    Integer generationComposition;
    Integer total;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    @Builder
    public SpecialMinyeongPointOfMultiChildResponseDto (RecordSpecialMinyeongPointOfMultiChild recordSpecialMinyeongPointOfMultiChild){
        this.id = recordSpecialMinyeongPointOfMultiChild.getId();
        this.aptNotificationNumber = recordSpecialMinyeongPointOfMultiChild.getAptInfo().getNotificationNumber();
        this.multiChildHouseholdType = recordSpecialMinyeongPointOfMultiChild.getMultiChildHouseholdType();
        this.numberOfChild =recordSpecialMinyeongPointOfMultiChild.getNumberOfChild();
        this.numberOfChildUnder6Year = recordSpecialMinyeongPointOfMultiChild.getNumberOfChildUnder6Year();
        this.bankbookJoinPeriod = recordSpecialMinyeongPointOfMultiChild.getBankbookJoinPeriod();
        this.periodOfApplicableAreaResidence = recordSpecialMinyeongPointOfMultiChild.getPeriodOfApplicableAreaResidence();
        this.periodOfHomelessness = recordSpecialMinyeongPointOfMultiChild.getPeriodOfHomelessness();
        this.generationComposition = recordSpecialMinyeongPointOfMultiChild.getGenerationComposition();
        this.total = recordSpecialMinyeongPointOfMultiChild.getTotal();
        this.createdDate = recordSpecialMinyeongPointOfMultiChild.getCreatedDate();
        this.modifiedDate = recordSpecialMinyeongPointOfMultiChild.getModifiedDate();
    }
}
