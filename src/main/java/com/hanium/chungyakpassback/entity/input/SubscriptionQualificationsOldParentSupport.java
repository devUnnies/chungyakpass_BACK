package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.entity.enumtype.Yn;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "inp_subscription_qualifications_oldParentSupport")
public class SubscriptionQualificationsOldParentSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_qualifications_oldParentSupport_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_qualifications_id")
    private SubscriptionQualifications subscriptionQualifications;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn oldParentSupportYn;


    @Builder
    public SubscriptionQualificationsOldParentSupport(com.hanium.chungyakpassback.entity.input.SubscriptionQualifications subscriptionQualifications, Yn oldParentSupportYn) {
        this.subscriptionQualifications = subscriptionQualifications;
        this.oldParentSupportYn = oldParentSupportYn;
    }
}
