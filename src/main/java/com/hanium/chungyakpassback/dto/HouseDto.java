package com.hanium.chungyakpassback.dto;

import com.hanium.chungyakpassback.entity.enumtype.AddressLevel1;
import com.hanium.chungyakpassback.entity.enumtype.AddressLevel2;
import com.hanium.chungyakpassback.entity.enumtype.Yn;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseDto {

    private Yn spouseHouseYn; //배우자분리세대여부

    private AddressLevel1 addressLevel1;

    private AddressLevel2 addressLevel2;

    private String addressDetail;

    private String zipcode;
}
