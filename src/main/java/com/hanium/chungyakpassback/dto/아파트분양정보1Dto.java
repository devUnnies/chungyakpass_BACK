package com.hanium.chungyakpassback.dto;


import com.hanium.chungyakpassback.domain.standard.아파트분양정보;
import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.time.LocalDate;


@Setter
@Getter
public class 아파트분양정보1Dto {
    public Integer 공고번호;
    public String 주택유형;
    public String 건설업체;
    private String 지역_레벨1;
    private String 지역_레벨2;
    private String 상세주소;
    private String 주택명;
    private LocalDate 모집공고일;
    private LocalDate 당첨자발표일;
    private LocalDate 계약시작일;
    private LocalDate 계약종료일;
    public Integer 공급세대수_계;


    public 아파트분양정보1 toEntity() {
        return 아파트분양정보1.builder()
                .상세주소(상세주소)
                .지역_레벨1(지역_레벨1)
                .지역_레벨2(지역_레벨2)
                .주택명(주택명)
                .주택유형(주택유형)
                .건설업체(건설업체)
                .공고번호(공고번호)
                .모집공고일(모집공고일)
                .당첨자발표일(당첨자발표일)
                .계약시작일(계약시작일)
                .계약종료일(계약종료일)
                .build();
    }

    @Builder
    public 아파트분양정보1Dto(JSONObject itemJson) {
        this.상세주소 = itemJson.getString("hssplyadres");
        String[] 지역_레벨 = 상세주소.split(" ");
        this.지역_레벨1 = 지역_레벨[0];
        this.지역_레벨2 = 지역_레벨[1];
        if (itemJson.has("totsuplyhshldco")) {
            this.공급세대수_계 = itemJson.getInt("totsuplyhshldco");
        }
        this.공고번호 = itemJson.getInt("pblancno");
        this.주택명 = itemJson.getString("housenm");
        this.모집공고일 = LocalDate.parse(itemJson.getString("rcritpblancde"));
        this.계약시작일 = LocalDate.parse(itemJson.getString("przwnerpresnatnde"));
        this.계약종료일 = LocalDate.parse(itemJson.getString("cntrctcnclsbgnde"));
        this.당첨자발표일 = LocalDate.parse(itemJson.getString("cntrctcnclsendde"));
    }

    public void PrintDate() {
        System.out.print("지역_레벨1 " + 지역_레벨1);
        System.out.print("지역_레벨2 " + 지역_레벨2);
        System.out.print("공고번호 " + 공고번호);
        System.out.print("주택유형 " + 주택유형);
        System.out.print("건설업체 " + 건설업체);
        System.out.print("주택명 " + 주택명);
        System.out.print("공급세대수_계 " + 공급세대수_계);
        System.out.print("모집공고일 " + 모집공고일);
        System.out.print("계약시작일 " + 계약시작일);
        System.out.print("계약종료일 " + 계약종료일);
        System.out.print("당첨자발표일 " + 당첨자발표일);
        System.out.print("계약종료일 " + 계약종료일);
        System.out.print("당첨자발표일 " + 당첨자발표일);
        System.out.println();
    }
}



