package com.hanium.chungyakpassback.dto.point;

import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfOldParentsSupport;
import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfSingleParents;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SpecialMinyeongPointOfOldParentsSupportResponseDto {
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
    boolean bankBookVaildYn;
    Integer total;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    @Builder
    public SpecialMinyeongPointOfOldParentsSupportResponseDto (RecordSpecialMinyeongPointOfOldParentsSupport recordSpecialMinyeongPointOfOldParentsSupport){
        this.id = recordSpecialMinyeongPointOfOldParentsSupport.getId();
        this.houseMemberId = recordSpecialMinyeongPointOfOldParentsSupport.getHouseMemberId();
        this.parentsDeathYn = recordSpecialMinyeongPointOfOldParentsSupport.getParentsDeathYn();
        this.divorceYn = recordSpecialMinyeongPointOfOldParentsSupport.getDivorceYn();
        this.sameResidentRegistrationYn = recordSpecialMinyeongPointOfOldParentsSupport.getSameResidentRegistrationYn();
        this.stayOverYn = recordSpecialMinyeongPointOfOldParentsSupport.getStayOverYn();
        this.nowStayOverYn = recordSpecialMinyeongPointOfOldParentsSupport.getNowStayOverYn();
        this.periodOfHomelessness = recordSpecialMinyeongPointOfOldParentsSupport.getPeriodOfHomelessness();
        this.bankbookJoinPeriod = recordSpecialMinyeongPointOfOldParentsSupport.getBankbookJoinPeriod();
        this.numberOfDependents = recordSpecialMinyeongPointOfOldParentsSupport.getNumberOfDependents();
        this.bankBookVaildYn = recordSpecialMinyeongPointOfOldParentsSupport.isBankBookVaildYn();
        this.total = recordSpecialMinyeongPointOfOldParentsSupport.getTotal();
        this.createdDate = recordSpecialMinyeongPointOfOldParentsSupport.getCreatedDate();
        this.modifiedDate = recordSpecialMinyeongPointOfOldParentsSupport.getModifiedDate();

    }
}
