package com.hanium.chungyakpassback.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanium.chungyakpassback.domain.enumtype.여부;
import com.hanium.chungyakpassback.domain.enumtype.주택유형;
import com.hanium.chungyakpassback.domain.enumtype.지역_레벨1;
import com.hanium.chungyakpassback.domain.enumtype.지역_레벨2;
import com.hanium.chungyakpassback.domain.standard.아파트분양정보;
import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.yaml.snakeyaml.util.EnumUtils;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Getter
public class 아파트분양정보1Dto {
    public Integer 공고번호;
    public com.hanium.chungyakpassback.domain.enumtype.주택유형 주택유형;
    public String 건설업체;
    public com.hanium.chungyakpassback.domain.enumtype.지역_레벨1 지역_레벨1;
    private 지역_레벨2 지역_레벨2;
    private String 상세주소;
    private String 주택명;
    private LocalDate 모집공고일;
    private LocalDate 당첨자발표일;
    private LocalDate 계약시작일;
    private LocalDate 계약종료일;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM", timezone = "Asia/Seoul")
    public YearMonth 입주예정월;
    public Integer 공급세대수_계;
    public 여부 투기과열지구;
    public 여부 청약과열지역;
    public 여부 위축지역;
    public 여부 분양가상한제;
    public 여부 정비사업;
    public 여부 공공주택지구;
    public 여부 공공건설임대주택;
    public 여부 대규모택지개발지구;
    public 여부 공공주택특별법적용;


    public 아파트분양정보1 toEntity() {
        return 아파트분양정보1.builder()
                .공고번호(공고번호)
                .지역_레벨1(지역_레벨1)
                .지역_레벨2(지역_레벨2)
                .상세주소(상세주소)
                .주택명(주택명)
                .주택유형(주택유형)
                .건설업체(건설업체)
                .모집공고일(모집공고일)
                .당첨자발표일(당첨자발표일)
                .계약시작일(계약시작일)
                .계약종료일(계약종료일)
                .공급세대수_계(공급세대수_계)
                .투기과열지구(투기과열지구)
                .청약과열지역(청약과열지역)
                .위축지역(위축지역)
                .분양가상한제(분양가상한제)
                .정비사업(정비사업)
                .공공주택지구(공공주택지구)
                .공공건설임대주택(공공건설임대주택)
                .대규모택지개발지구(대규모택지개발지구)
                .입주예정월(입주예정월)
                .공공주택특별법적용(공공주택특별법적용)
                .build();
    }

    @Builder
    public 아파트분양정보1Dto(JSONObject itemJson) {
        this.상세주소 = itemJson.getString("hssplyadres");
        String[] 지역_레벨 = 상세주소.split(" ");
        String 지역_레벨_2 = 지역_레벨[1];
        if (Arrays.stream(com.hanium.chungyakpassback.domain.enumtype.지역_레벨2.values()).anyMatch(v -> v.name().equals(지역_레벨_2))) {
            this.지역_레벨2 = com.hanium.chungyakpassback.domain.enumtype.지역_레벨2.valueOf(지역_레벨_2);
        }
        else{
            this.지역_레벨2 = null;
        }


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
