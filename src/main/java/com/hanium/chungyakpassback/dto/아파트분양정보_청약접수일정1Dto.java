package com.hanium.chungyakpassback.dto;
import lombok.Getter;
import java.time.LocalDate;
import org.json.JSONObject;


@Getter
public class 아파트분양정보_청약접수일정1Dto {
    private LocalDate 특별공급접수시작일;
    private LocalDate 특별공급접수종료일;
    private LocalDate 일순위접수일해당지역;
    private LocalDate 일순위접수일기타지역;
    private LocalDate 이순위접수일해당지역;
    private LocalDate 이순위접수일기타지역;
    private Integer 공급세대수_계;

    public 아파트분양정보_청약접수일정1Dto(JSONObject itemJson) {
        this.일순위접수일해당지역 = LocalDate.parse(itemJson.getString("gnrlrnk1crsparearceptpd"));
        this.일순위접수일기타지역 = LocalDate.parse(itemJson.getString("gnrlrnk1etcarearcptdepd"));
        this.이순위접수일해당지역 = LocalDate.parse(itemJson.getString("gnrlrnk2crsparearceptpd"));
        this.이순위접수일기타지역 = LocalDate.parse(itemJson.getString("gnrlrnk2etcarearcptdepd"));

        if(itemJson.has("spsplyrceptbgnde")) {
            this.특별공급접수시작일 = LocalDate.parse(itemJson.getString("spsplyrceptbgnde"));
            this.특별공급접수종료일 = LocalDate.parse(itemJson.getString("spsplyrceptendde"));
        }
    }

    public void PrintDate(){
        System.out.println("특별공급접수시작일 "+특별공급접수시작일);
        System.out.println("특별공급접수종료일 "+특별공급접수종료일);
        System.out.println("일순위접수일해당지역 "+일순위접수일해당지역);
        System.out.println("일순위접수일기타지역 "+일순위접수일기타지역);
        System.out.println("이순위접수일해당지역 "+이순위접수일해당지역);
        System.out.println("이순위접수일기타지역 "+이순위접수일기타지역);
        System.out.println();
    }

}