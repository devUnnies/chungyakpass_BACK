package com.hanium.chungyakpassback.domain.standard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanium.chungyakpassback.domain.enumtype.여부;
import com.hanium.chungyakpassback.domain.enumtype.주택유형;
import com.hanium.chungyakpassback.domain.enumtype.지역_레벨1;
import com.hanium.chungyakpassback.domain.enumtype.지역_레벨2;
import com.hanium.chungyakpassback.dto.아파트분양정보1Dto;
//import com.hanium.chungyakpassback.service.YearMonthDateAttributeConverter;
import com.hanium.chungyakpassback.service.YearAttributeConverter;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor // 기본 생성자를 대신 생성해줍니다.
@AllArgsConstructor
@Table
public class 아파트분양정보1 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "아파트분양정보id")
    private Long id;

    @Id
    //@Column(name = "공고번호")
    @Column(name = "아파트분양정보id")
    private Integer 공고번호;

    @Column
    @Enumerated(EnumType.STRING)
    private 지역_레벨1 지역_레벨1;

    @Column
    @Enumerated(EnumType.STRING)
    private 지역_레벨2 지역_레벨2;

    @Column
    private String 상세주소;

    @Column
    @Enumerated(EnumType.STRING)
    private 주택유형 주택유형;

    @Column
    private String 주택명;

    @Column
    private String 건설업체;

    @Column
    private LocalDate 모집공고일;

    @Column
    private LocalDate 당첨자발표일;

    @Column
    public Integer 공급세대수_계;

    @Column
    private LocalDate 계약시작일;

    @Column
    private LocalDate 계약종료일;


    @Convert(converter = YearAttributeConverter.class)
    @Column(name = "입주예정월")
    private YearMonth 입주예정월;

    @Column
    @Enumerated(EnumType.STRING)
    private 여부 공공주택특별법적용;

    @Column
    @Enumerated(EnumType.STRING)
    private 여부 투기과열지구;

    @Column
    @Enumerated(EnumType.STRING)
    private 여부 청약과열지역;

    @Column
    @Enumerated(EnumType.STRING)
    private 여부 위축지역;

    @Column
    @Enumerated(EnumType.STRING)
    private 여부 분양가상한제;

    @Column
    @Enumerated(EnumType.STRING)
    private 여부 정비사업;

    @Column
    @Enumerated(EnumType.STRING)
    private 여부 공공주택지구;

    @Column
    @Enumerated(EnumType.STRING)
    private 여부 공공건설임대주택;

    @Column
    @Enumerated(EnumType.STRING)
    private 여부 대규모택지개발지구;

    @Builder
    public 아파트분양정보1(String 상세주소, 지역_레벨1 지역_레벨1, 지역_레벨2 지역_레벨2, 주택유형 주택유형, String 주택명, String 건설업체, Integer 공급세대수_계, LocalDate 모집공고일, LocalDate 당첨자발표일, int 공고번호, LocalDate 계약시작일, LocalDate 계약종료일, 여부 투기과열지구, 여부 청약과열지역, 여부 위축지역,여부 공공주택특별법적용, 여부 분양가상한제, 여부 정비사업, 여부 공공주택지구, 여부 공공건설임대주택, 여부 대규모택지개발지구, YearMonth 입주예정월) {
        this.상세주소 = 상세주소;
        this.지역_레벨1 = 지역_레벨1;
        this.지역_레벨2 = 지역_레벨2;
        this.주택유형 = 주택유형;
        this.주택명 = 주택명;
        this.건설업체 = 건설업체;
        this.모집공고일 = 모집공고일;
        this.당첨자발표일 = 당첨자발표일;
        this.공고번호 = 공고번호;
        this.계약시작일 = 계약시작일;
        this.계약종료일 = 계약종료일;
        this.공급세대수_계 = 공급세대수_계;
        this.공공주택특별법적용 = 공공주택특별법적용;
        this.투기과열지구 = 투기과열지구;
        this.청약과열지역 = 청약과열지역;
        this.위축지역 = 위축지역;
        this.분양가상한제 = 분양가상한제;
        this.정비사업 = 정비사업;
        this.공공주택지구 = 공공주택지구;
        this.공공건설임대주택 = 공공건설임대주택;
        this.대규모택지개발지구 = 대규모택지개발지구;
        this.입주예정월 = 입주예정월;
    }

    public void PrintDate(){
        System.out.print("주택유형 " +주택유형);
        System.out.print("건설업체 " +건설업체);
        System.out.print("공고번호 "+공고번호);
        System.out.println();
    }
}