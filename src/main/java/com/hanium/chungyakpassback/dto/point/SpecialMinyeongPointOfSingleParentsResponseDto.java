package com.hanium.chungyakpassback.dto.point;

import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfMultiChild;
import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfNewMarried;
import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfSingleParents;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialMinyeongPointOfSingleParentsResponseDto {
    Long id;
    Integer aptNotificationNumber;
    Integer numberOfMinors;
    Integer ageOfMostYoungChild;
    Integer bankbookPaymentsCount;
    Integer periodOfApplicableAreaResidence;
    Integer monthOfAverageIncome;
    Integer total;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    @Builder
    public SpecialMinyeongPointOfSingleParentsResponseDto (RecordSpecialMinyeongPointOfSingleParents recordSpecialMinyeongPointOfSingleParents){
        this.id = recordSpecialMinyeongPointOfSingleParents.getId();
        this.aptNotificationNumber = recordSpecialMinyeongPointOfSingleParents.getAptInfo().getNotificationNumber();
        this.numberOfMinors = recordSpecialMinyeongPointOfSingleParents.getNumberOfMinors();
        this.ageOfMostYoungChild = recordSpecialMinyeongPointOfSingleParents.getAgeOfMostYoungChild();
        this.bankbookPaymentsCount = recordSpecialMinyeongPointOfSingleParents.getBankbookPaymentsCount();
        this.periodOfApplicableAreaResidence = recordSpecialMinyeongPointOfSingleParents.getPeriodOfApplicableAreaResidence();
        this.monthOfAverageIncome = recordSpecialMinyeongPointOfSingleParents.getMonthOfAverageIncome();
        this.total = recordSpecialMinyeongPointOfSingleParents.getTotal();
        this.createdDate = recordSpecialMinyeongPointOfSingleParents.getUser().getCreatedDate();
        this.modifiedDate = recordSpecialMinyeongPointOfSingleParents.getUser().getModifiedDate();
    }

}
