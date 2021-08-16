package com.hanium.chungyakpassback.domain.standard;

import com.hanium.chungyakpassback.dto.아파트분양정보1Dto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
public class 아파트분양정보_공급금액1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "아파트분양정보_공급금액id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "아파트분양정보id")
    private 아파트분양정보1 아파트분양정보1;

    @Column
    private String 주택형;

    @Column
    private Integer 공고번호;

    @Column
    private String 공급금액;

    @Builder
    public 아파트분양정보_공급금액1(아파트분양정보1 아파트분양정보1, String 주택형, String 공급금액, Integer 공고번호) {
        this.아파트분양정보1 = 아파트분양정보1;
        this.주택형 = 주택형;
        this.공고번호 =공고번호;
        this.공급금액 = 공급금액;
    }
}
