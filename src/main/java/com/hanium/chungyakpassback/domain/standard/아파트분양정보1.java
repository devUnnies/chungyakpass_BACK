package com.hanium.chungyakpassback.domain.standard;

import com.hanium.chungyakpassback.dto.아파트분양정보1Dto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private String 지역_레벨1;

    @Column
    private String 지역_레벨2;

    @Column
    private String 상세주소;

    @Column
    private String 주택유형;

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


    @Builder
    public 아파트분양정보1(String 상세주소, String 지역_레벨1,String 지역_레벨2, String 주택유형, String 주택명, String 건설업체, Integer 공급세대수_계, LocalDate 모집공고일, LocalDate 당첨자발표일, int 공고번호, LocalDate 계약시작일, LocalDate 계약종료일) {
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
    }

    public void PrintDate(){
        System.out.print("주택유형 " +주택유형);
        System.out.print("건설업체 " +건설업체);
        System.out.print("공고번호 "+공고번호);
        System.out.println();
    }
}