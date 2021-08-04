package com.hanium.chungyakpassback.domain.standard;

import com.hanium.chungyakpassback.domain.enumtype.여부;
import com.hanium.chungyakpassback.domain.enumtype.주택유형;
import com.hanium.chungyakpassback.dto.아파트분양정보1Dto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor // 기본 생성자를 대신 생성해줍니다.
@AllArgsConstructor
@Table
public class 아파트분양정보1 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "아파트분양정보id")
    private Long id;

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
    private String 문의처;

    @Column
    private LocalDate 모집공고일;

    @Column
    private LocalDate 당첨자발표일;

    @Column
    public Integer 공급세대수_계;

    @Id
    @Column
    private Integer 공고번호;

    @Column
    private LocalDate 계약시작일;

    @Column
    private LocalDate 계약종료일;

    public 아파트분양정보1(아파트분양정보1Dto 아파트분양정보1dto){
        this.상세주소 =아파트분양정보1dto.get상세주소();
        this.지역_레벨1 =아파트분양정보1dto.get지역_레벨1();
        this.지역_레벨2 =아파트분양정보1dto.get지역_레벨2();
        this.공급세대수_계 =아파트분양정보1dto.get공급세대수_계();
        this.공고번호 =아파트분양정보1dto.get공고번호();
        this.주택명 =아파트분양정보1dto.get주택명();
        this.모집공고일 =아파트분양정보1dto.get모집공고일();
        this.계약시작일 =아파트분양정보1dto.get계약시작일();
        this.계약종료일 =아파트분양정보1dto.get계약종료일();
        this.당첨자발표일 =아파트분양정보1dto.get당첨자발표일();
    }

    @Builder
    public 아파트분양정보1(String 지역_레벨1,String 주택유형,String 주택명,String 건설업체, String 문의처, LocalDate 모집공고일, LocalDate 당첨자발표일, int 공고번호,LocalDate 계약시작일, LocalDate 계약종료일 )
    {
        this.지역_레벨1 = 지역_레벨1;
        this.주택유형 = 주택유형;
        this.주택명 = 주택명;
        this.건설업체 = 건설업체;
        this.문의처 = 문의처;
        this.모집공고일 = 모집공고일;
        this.당첨자발표일 = 당첨자발표일;
        this.공고번호 = 공고번호;
        this.계약시작일 = 계약시작일;
        this.계약종료일 = 계약종료일;
    }

}