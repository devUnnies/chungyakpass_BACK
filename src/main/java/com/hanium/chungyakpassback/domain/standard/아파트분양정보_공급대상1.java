package com.hanium.chungyakpassback.domain.standard;

import javassist.expr.NewArray;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
public class 아파트분양정보_공급대상1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "아파트분양정보_공급대상id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "아파트분양정보id")
    private 아파트분양정보 아파트분양정보;

    @Column
    private Integer 공고번호;

    @Column
    private String 주택형;

    @Column
    private double 주택공급면적;

    @Column
    private Integer 공급세대수_일반;

    @Column
    private Integer 공급세대수_특별;

    @Column
    private Integer 공급세대수_계;

    @Column
    private Integer 주택관리번호;

    @Builder
    public 아파트분양정보_공급대상1(아파트분양정보 아파트분양정보,Integer 공고번호, String 주택형, double 주택공급면적, Integer 공급세대수_일반, Integer 공급세대수_특별, Integer 공급세대수_계, Integer 주택관리번호) {
        this.아파트분양정보 = 아파트분양정보;
        this.공고번호 =공고번호;
        this.주택형 = 주택형;
        this.주택공급면적 = 주택공급면적;
        this.공급세대수_일반 = 공급세대수_일반;
        this.공급세대수_특별 = 공급세대수_특별;
        this.공급세대수_계 = 공급세대수_계;
        this.주택관리번호 = 주택관리번호;
    }
}
