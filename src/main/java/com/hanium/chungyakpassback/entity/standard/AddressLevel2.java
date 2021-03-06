package com.hanium.chungyakpassback.entity.standard;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "std_address_level2")
public class AddressLevel2 {
    @Id
    @Column(name = "address_level2_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_level1_id")
    private AddressLevel1 addressLevel1;

    @Column
    @Enumerated(EnumType.STRING)
    private com.hanium.chungyakpassback.enumtype.AddressLevel2 addressLevel2;

}
