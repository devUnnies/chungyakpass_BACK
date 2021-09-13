package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.enumtype.Ranking;
import com.hanium.chungyakpassback.enumtype.SpecialSupply;
import com.hanium.chungyakpassback.enumtype.Supply;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inp_chungyak_qualification")
public class ChungyakQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chungyak_qualification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_number_id")
    private com.hanium.chungyakpassback.entity.apt.AptInfo AptInfo; //아파트분양정보


    @Column
    @Enumerated(EnumType.STRING)
    private Supply supply; //공급유형

    @Column
    @Enumerated(EnumType.STRING)
    private SpecialSupply specialSupply; //특별공급유형

    @Column
    @Enumerated(EnumType.STRING)
    private Ranking ranking; //순위

    @Column
    private String housingType; //주택형

    @Column
    @Enumerated(EnumType.STRING)
    private Yn housingPrescription; //주택처분서약

    @Column
    private int dependent; //부양가족수

    @Column
    private int subscriptionPeriod; //청약지역거주기간

    @Column
    private Yn resultQualification; //결과_청약자격

    @Column
    private int resultScore; //결과_점수

    @Builder
    public ChungyakQualification(User user, com.hanium.chungyakpassback.entity.apt.AptInfo aptInfo, Supply supply, SpecialSupply specialSupply, Ranking ranking, String housingType, Yn housingPrescription, int dependent, int subscriptionPeriod, Yn resultQualification, int resultScore) {
        this.user = user;
        AptInfo = aptInfo;
        this.supply = supply;
        this.specialSupply = specialSupply;
        this.ranking = ranking;
        this.housingType = housingType;
        this.housingPrescription = housingPrescription;
        this.dependent = dependent;
        this.subscriptionPeriod = subscriptionPeriod;
        this.resultQualification = resultQualification;
        this.resultScore = resultScore;
    }
}
