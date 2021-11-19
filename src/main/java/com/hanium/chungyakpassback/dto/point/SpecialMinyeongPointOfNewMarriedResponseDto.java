package com.hanium.chungyakpassback.dto.point;

import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfNewMarried;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialMinyeongPointOfNewMarriedResponseDto{

    Long id;
    Integer aptNotificationNumber;
    Integer numberOfMinors;
    Integer periodOfMarriged;
    Integer bankbookPaymentsCount;
    Integer periodOfApplicableAreaResidence;
    Integer monthOfAverageIncome;
    Integer total;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    @Builder
    public SpecialMinyeongPointOfNewMarriedResponseDto (RecordSpecialMinyeongPointOfNewMarried recordSpecialMinyeongPointOfNewMarried){
        this.id = recordSpecialMinyeongPointOfNewMarried.getId();
        this.aptNotificationNumber = recordSpecialMinyeongPointOfNewMarried.getAptInfo().getNotificationNumber();
        this.numberOfMinors = recordSpecialMinyeongPointOfNewMarried.getNumberOfMinors();
        this.periodOfMarriged = recordSpecialMinyeongPointOfNewMarried.getPeriodOfMarriged();
        this.bankbookPaymentsCount = recordSpecialMinyeongPointOfNewMarried.getBankbookPaymentsCount();
        this.periodOfApplicableAreaResidence = recordSpecialMinyeongPointOfNewMarried.getPeriodOfApplicableAreaResidence();
        this.monthOfAverageIncome = recordSpecialMinyeongPointOfNewMarried.getMonthOfAverageIncome();
        this.total = recordSpecialMinyeongPointOfNewMarried.getTotal();
        this.createdDate =recordSpecialMinyeongPointOfNewMarried.getUser().getCreatedDate();
        this.modifiedDate = recordSpecialMinyeongPointOfNewMarried.getUser().getModifiedDate();
    }

}
//package com.hanium.chungyakpassback.dto.point;
//
//import com.hanium.chungyakpassback.entity.apt.AptInfo;
//import com.hanium.chungyakpassback.entity.input.UserBankbook;
//import com.hanium.chungyakpassback.entity.point.RecordSpecialMinyeongPointOfNewMarried;
//import lombok.*;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class SpecialMinyeongPointOfNewMarriedResponseDto {
//    Integer numberOfMinors;
//    Integer periodOfMarriged;
//    Integer bankbookPaymentsCount;
//    Integer periodOfApplicableAreaResidence;
//    Integer monthOfAverageIncome;
//
//    @Builder
//    public SpecialMinyeongPointOfNewMarriedResponseDto (RecordSpecialMinyeongPointOfNewMarried recordSpecialMinyeongPointOfNewMarried){
//        this.numberOfMinors = recordSpecialMinyeongPointOfNewMarried.getNumberOfMinors();
//        this.periodOfMarriged = recordSpecialMinyeongPointOfNewMarried.getPeriodOfMarriged();
//        this.bankbookPaymentsCount = recordSpecialMinyeongPointOfNewMarried.getBankbookPaymentsCount();
//        this.periodOfApplicableAreaResidence = recordSpecialMinyeongPointOfNewMarried.getPeriodOfApplicableAreaResidence();
//        this.monthOfAverageIncome = recordSpecialMinyeongPointOfNewMarried.getMonthOfAverageIncome();
//    }
//
//    public RecordSpecialMinyeongPointOfNewMarried toEntity() {
//        return RecordSpecialMinyeongPointOfNewMarried.builder()
//                .numberOfMinors(numberOfMinors)
//                .periodOfMarriged(periodOfMarriged)
//                .bankbookPaymentsCount(bankbookPaymentsCount)
//                .periodOfApplicableAreaResidence(periodOfApplicableAreaResidence)
//                .monthOfAverageIncome(monthOfAverageIncome)
//                .build();
//    }
//
//}
