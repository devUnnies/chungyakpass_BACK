package com.hanium.chungyakpassback.entity.point;

import com.hanium.chungyakpassback.entity.base.BaseTime;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_of_general_minyeong")
public class PointOfGeneralMinyeong extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_of_general_minyeong_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    Integer periodOfHomelessness;

    @Column
    Integer bankbookJoinPeriod;

    @Column
    Integer numberOfDependents;

    @Column
    boolean bankBookValidTf;

    @Column
    Integer total;

    @Builder
    public PointOfGeneralMinyeong(User user, Integer periodOfHomelessness, Integer bankbookJoinPeriod, Integer numberOfDependents, boolean bankBookVaildYn, Integer total) {
        this.user = user;
        this.periodOfHomelessness = periodOfHomelessness;
        this.bankbookJoinPeriod = bankbookJoinPeriod;
        this.numberOfDependents = numberOfDependents;
        this.bankBookValidTf = bankBookVaildYn;
        this.total = total;
    }
}
