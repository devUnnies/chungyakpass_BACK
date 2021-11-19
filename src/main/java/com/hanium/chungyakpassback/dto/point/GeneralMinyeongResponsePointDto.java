package com.hanium.chungyakpassback.dto.point;

import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.recordPoint.RecordGeneralMinyeongPoint;
import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfOldParentsSupport;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralMinyeongResponsePointDto {
    Long id;
    Long houseMemberId;
    Yn parentsDeathYn;
    Yn divorceYn;
    Yn sameResidentRegistrationYn;
    Yn stayOverYn;
    Yn nowStayOverYn;
    Integer periodOfHomelessness;
    Integer bankbookJoinPeriod;
    Integer numberOfDependents;
    boolean bankBookVaildTf;
    Integer total;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    @Builder
    public GeneralMinyeongResponsePointDto(RecordGeneralMinyeongPoint recordGeneralMinyeongPoint){
        this.id = recordGeneralMinyeongPoint.getId();
        this.houseMemberId = recordGeneralMinyeongPoint.getHouseMemberId();
        this.parentsDeathYn = recordGeneralMinyeongPoint.getParentsDeathYn();
        this.divorceYn = recordGeneralMinyeongPoint.getDivorceYn();
        this.sameResidentRegistrationYn = recordGeneralMinyeongPoint.getSameResidentRegistrationYn();
        this.stayOverYn = recordGeneralMinyeongPoint.getStayOverYn();
        this.nowStayOverYn = recordGeneralMinyeongPoint.getNowStayOverYn();
        this.periodOfHomelessness = recordGeneralMinyeongPoint.getPeriodOfHomelessness();
        this.bankbookJoinPeriod = recordGeneralMinyeongPoint.getBankbookJoinPeriod();
        this.numberOfDependents = recordGeneralMinyeongPoint.getNumberOfDependents();
        this.bankBookVaildTf = recordGeneralMinyeongPoint.isBankBookValidTf();
        this.total = recordGeneralMinyeongPoint.getTotal();
        this.createdDate = recordGeneralMinyeongPoint.getCreatedDate();
        this.modifiedDate = recordGeneralMinyeongPoint.getModifiedDate();

    }
}
