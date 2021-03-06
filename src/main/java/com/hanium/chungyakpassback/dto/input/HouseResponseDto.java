package com.hanium.chungyakpassback.dto.input;

import com.hanium.chungyakpassback.entity.input.House;
import com.hanium.chungyakpassback.enumtype.AddressLevel1;
import com.hanium.chungyakpassback.enumtype.AddressLevel2;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HouseResponseDto {
    private Long id; //세대 id

    @Setter
    private Long houseHolderId; //세대주 세대구성원 id

    private AddressLevel1 addressLevel1; //지역레벨1

    private AddressLevel2 addressLevel2; //지역레벨2

    private String addressDetail; //상세주소

    private String zipcode; //우편번호

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @Builder
    public HouseResponseDto(House house){
        this.id = house.getId();
        this.addressLevel1 = house.getAddressLevel1().getAddressLevel1();
        this.addressLevel2 = house.getAddressLevel2().getAddressLevel2();
        this.addressDetail = house.getAddressDetail();
        this.zipcode = house.getZipcode();
        this.createdDate = house.getCreatedDate();
        this.modifiedDate = house.getModifiedDate();
    }
}
