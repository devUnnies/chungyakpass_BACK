package com.hanium.chungyakpassback.entity.standard;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "std_aptinfo_receipt")
public class AptInfoReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aptinfo_receipt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_number_id")
    private AptInfo AptInfo;

    @Column
    private LocalDate specialReceptionStartDate;//특별공급접수시작일

    @Column
    private LocalDate specialReceptionEndDate;//특별공급접수종료일

    @Column
    private LocalDate priorityApplicableArea;//일순위접수일해당지역

    @Column
    private LocalDate priorityGyeonggiArea;//일순위접수일경기지역

    @Column
    private LocalDate priorityOtherArea;//일순위접수일기타지역

    @Column
    private LocalDate secondApplicableArea;//이순위접수일해당지역

    @Column
    private LocalDate secondGyeonggiArea;//이순위접수일경기지역

    @Column
    private LocalDate secondOtherArea;//이순위접수일기타지역

    @Column
    private String homepage;//홈페이지

    @Builder
    public AptInfoReceipt(AptInfo aptInfo, LocalDate specialReceptionStartDate, LocalDate specialReceptionEndDate, LocalDate priorityApplicableArea, LocalDate priorityGyeonggiArea, LocalDate priorityOtherArea, LocalDate secondApplicableArea, LocalDate secondGyeonggiArea, LocalDate secondOtherArea, String homepage) {
        AptInfo = aptInfo;
        this.specialReceptionStartDate = specialReceptionStartDate;
        this.specialReceptionEndDate = specialReceptionEndDate;
        this.priorityApplicableArea = priorityApplicableArea;
        this.priorityGyeonggiArea = priorityGyeonggiArea;
        this.priorityOtherArea = priorityOtherArea;
        this.secondApplicableArea = secondApplicableArea;
        this.secondGyeonggiArea = secondGyeonggiArea;
        this.secondOtherArea = secondOtherArea;
        this.homepage = homepage;
    }
}
