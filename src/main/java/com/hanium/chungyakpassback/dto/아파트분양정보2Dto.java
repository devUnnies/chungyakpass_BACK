package com.hanium.chungyakpassback.dto;

import com.hanium.chungyakpassback.domain.enumtype.주택유형;
import com.hanium.chungyakpassback.domain.enumtype.지역_레벨1;
import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;



@Getter
public class 아파트분양정보2Dto {
    public Integer 공고번호;
    public com.hanium.chungyakpassback.domain.enumtype.주택유형 주택유형;
    public String 건설업체;
    public com.hanium.chungyakpassback.domain.enumtype.지역_레벨1 지역_레벨1;


    public 아파트분양정보1 toEntity(){
        return 아파트분양정보1.builder()
                .주택유형(주택유형)
                .건설업체(건설업체)
                .공고번호(공고번호)
                .build();
    }

    @Builder
    public 아파트분양정보2Dto(JSONObject itemJson){
        this.지역_레벨1 = com.hanium.chungyakpassback.domain.enumtype.지역_레벨1.valueOf(itemJson.getString("sido"));
        this.공고번호 = itemJson.getInt("pblancNo");//주택관리번호 뽑아냄
        this.주택유형 = com.hanium.chungyakpassback.domain.enumtype.주택유형.valueOf(itemJson.getString("houseDtlSecdNm"));
        this.건설업체 = itemJson.getString("bsnsMbyNm");
    }
}


