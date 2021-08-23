package com.hanium.chungyakpassback.dto;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;

import java.time.LocalDate;

@Getter
public class AptInfoReceiptDto {
    private Integer notificationNumber;
    private LocalDate specialReceptionStartDate;//특별공급접수시작일
    private LocalDate specialReceptionEndDate;//특별공급접수종료일
    private LocalDate priorityApplicableArea;//일순위접수일해당지역
    private LocalDate priorityGyeonggiArea;//일순위접수일경기지역
    private LocalDate priorityOtherArea;//일순위접수일기타지역
    private LocalDate secondApplicableArea;//이순위접수일해당지역
    private LocalDate secondGyeonggiArea;//이순위접수일경기지역
    private LocalDate secondOtherArea;//이순위접수일기타지역
    private String homepage;//홈페이지

    @Builder
    public AptInfoReceiptDto(JSONObject itemJson) {
        this.notificationNumber = itemJson.getInt("pblancno");
        if(itemJson.has("spsplyrceptbgnde")) {
            this.specialReceptionStartDate = LocalDate.parse(itemJson.getString("spsplyrceptbgnde"));
            this.specialReceptionEndDate = LocalDate.parse(itemJson.getString("spsplyrceptendde"));
        }
        this.priorityApplicableArea = LocalDate.parse(itemJson.getString("gnrlrnk1crsparearceptpd"));
        if(itemJson.has("gnrlrnk1etcggrcptdepd")){
            this.priorityGyeonggiArea = LocalDate.parse(itemJson.getString("gnrlrnk1etcggrcptdepd"));
        }
        this.priorityOtherArea = LocalDate.parse(itemJson.getString("gnrlrnk1etcarearcptdepd"));
        this.secondApplicableArea = LocalDate.parse(itemJson.getString("gnrlrnk2crsparearceptpd"));
        if(itemJson.has("gnrlrnk2etcggrcptdepd")){
            this.secondGyeonggiArea = LocalDate.parse(itemJson.getString("gnrlrnk2etcggrcptdepd"));
        }
        this.secondOtherArea = LocalDate.parse(itemJson.getString("gnrlrnk2etcarearcptdepd"));
        this.homepage = itemJson.getString("hmpgadres");


    }
}
