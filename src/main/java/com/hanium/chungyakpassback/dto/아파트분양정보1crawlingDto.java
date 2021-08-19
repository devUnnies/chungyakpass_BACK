package com.hanium.chungyakpassback.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanium.chungyakpassback.domain.enumtype.여부;
import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
import lombok.Builder;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class 아파트분양정보1crawlingDto {
    public Integer 공고번호;
    public 여부 투기과열지구;
    public 여부 청약과열지역;
    public 여부 위축지역;
    public 여부 분양가상한제;
    public 여부 정비사업;
    public 여부 공공주택지구;
    public 여부 공공건설임대주택;
    public 여부 대규모택지개발지구;
    public 여부 공공주택특별법적용;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy.MM", timezone="Asia/Seoul")
    public YearMonth 입주예정월;

    public 아파트분양정보1 toEntity() {
        return 아파트분양정보1.builder()
                .투기과열지구(투기과열지구)
                .청약과열지역(청약과열지역)
                .위축지역(위축지역)
                .분양가상한제(분양가상한제)
                .정비사업(정비사업)
                .공공주택지구(공공주택지구)
                .공공건설임대주택(공공건설임대주택)
                .대규모택지개발지구(대규모택지개발지구)
                .build();
    }

    @Builder
    public 아파트분양정보1crawlingDto(String content, Integer number, String string) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy.MM");
        this.입주예정월 = YearMonth.parse(string, f);
        this.공고번호 = number;
        if (content.contains("특이사항")) {
            //System.out.println(numbers.get(a));
            String[] 지역_레벨 = content.split(":");
            String 여부 = 지역_레벨[1].trim();
            String[] 여부1 = 여부.split(",");
            List<String> 여부3 = new ArrayList<>();
            for (String 여부4 : 여부1) {
                여부3.add(여부4.trim());
            }
            if (여부3.contains("청약과열지역")) {
                this.청약과열지역 = com.hanium.chungyakpassback.domain.enumtype.여부.y;
            } else {
                this.청약과열지역 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            }
            if (여부3.contains("투기과열지구")) {
                this.투기과열지구 = com.hanium.chungyakpassback.domain.enumtype.여부.y;
            } else {
                this.투기과열지구 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            }
            if (여부3.contains("분양가상한제")) {
                this.분양가상한제 = com.hanium.chungyakpassback.domain.enumtype.여부.y;
            } else {
                this.분양가상한제 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            }
            if (여부3.contains("공공주택지구")) {
                this.공공주택지구 = com.hanium.chungyakpassback.domain.enumtype.여부.y;

            } else {
                this.공공주택지구 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            }
            if (여부3.contains("대규모택지개발지구")) {
                this.대규모택지개발지구 = com.hanium.chungyakpassback.domain.enumtype.여부.y;
            } else {
                this.대규모택지개발지구 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            }
            if (여부3.contains("정비사업")) {
                this.정비사업 = com.hanium.chungyakpassback.domain.enumtype.여부.y;
            } else {
                this.정비사업 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            }
            if (여부3.contains("위축지역")) {
                this.위축지역 = com.hanium.chungyakpassback.domain.enumtype.여부.y;
            } else {
                this.위축지역 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            }
            if (여부3.contains("공공건설임대주택")) {
                this.공공건설임대주택 = com.hanium.chungyakpassback.domain.enumtype.여부.y;
            } else {
                this.공공건설임대주택 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            }
            if (여부3.contains("대규모택지개발지구")) {
                this.대규모택지개발지구 = com.hanium.chungyakpassback.domain.enumtype.여부.y;
            } else {
                this.대규모택지개발지구 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            }
            if (여부3.contains("공공주택특별법적용")) {
                this.공공주택특별법적용 = com.hanium.chungyakpassback.domain.enumtype.여부.y;
            } else {
                this.공공주택특별법적용 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            }

//        if (여부3.contains("수도권내민영공공주택지구")) {
//            수도권내민영공공주택지구 = com.hanium.chungyakpassback.domain.enumtype.여부.y;
//        } else {
//            수도권내민영공공주택지구 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
//        }

        } else {
            this.정비사업 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            this.청약과열지역 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            this.투기과열지구 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            this.분양가상한제 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            this.공공주택지구 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            this.대규모택지개발지구 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            this.위축지역 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            this.공공건설임대주택 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
            this.공공주택특별법적용 = com.hanium.chungyakpassback.domain.enumtype.여부.n;
        }
    }

    public void PrintDate() {
        System.out.print("청약과열지역 " + 청약과열지역);
        System.out.print("투기과열지구 " + 투기과열지구);
        System.out.print("분양가상한제 " + 분양가상한제);
        System.out.print("대규모택지개발지구 " + 대규모택지개발지구);
        System.out.print("공공주택지구 " + 공공주택지구);
        System.out.print("정비사업 " + 정비사업);
        System.out.println();
    }
}
