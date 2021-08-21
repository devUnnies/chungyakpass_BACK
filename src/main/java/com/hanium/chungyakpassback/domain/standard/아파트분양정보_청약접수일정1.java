package com.hanium.chungyakpassback.domain.standard;

import com.hanium.chungyakpassback.domain.enumtype.공급유형;
import com.hanium.chungyakpassback.domain.enumtype.공급장소;
import com.hanium.chungyakpassback.domain.enumtype.순위;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
public class 아파트분양정보_청약접수일정1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "아파트분양정보_청약접수일정id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "아파트분양정보id")
    private 아파트분양정보1 아파트분양정보1;

    @Column
    private LocalDate 특별공급접수시작일;

    @Column
    private LocalDate 특별공급접수종료일;

    @Column
    private LocalDate 일순위접수일해당지역;

    @Column
    private LocalDate 일순위접수일경기지역;

    @Column
    private LocalDate 일순위접수일기타지역;

    @Column
    private LocalDate 이순위접수일해당지역;

    @Column
    private LocalDate 이순위접수일경기지역;

    @Column
    private LocalDate 이순위접수일기타지역;

    @Column
    private String 홈페이지;

    @Builder
    public 아파트분양정보_청약접수일정1(아파트분양정보1 아파트분양정보1,LocalDate 특별공급접수시작일, LocalDate 특별공급접수종료일, LocalDate 일순위접수일해당지역, LocalDate 일순위접수일경기지역, LocalDate 일순위접수일기타지역, LocalDate 이순위접수일해당지역,LocalDate 이순위접수일경기지역, LocalDate 이순위접수일기타지역, String 홈페이지)
    {
        this.아파트분양정보1 = 아파트분양정보1;
        this.특별공급접수시작일 = 특별공급접수시작일;
        this.특별공급접수종료일 = 특별공급접수종료일;
        this.일순위접수일해당지역 = 일순위접수일해당지역;
        this.일순위접수일경기지역 = 일순위접수일경기지역;
        this.일순위접수일기타지역 = 일순위접수일기타지역;
        this.이순위접수일해당지역 = 이순위접수일해당지역;
        this.이순위접수일경기지역 = 이순위접수일경기지역;
        this.이순위접수일기타지역 = 이순위접수일기타지역;
        this.홈페이지 = 홈페이지;
    }

}
