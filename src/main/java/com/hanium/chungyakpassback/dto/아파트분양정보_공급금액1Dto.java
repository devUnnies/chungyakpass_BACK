package com.hanium.chungyakpassback.dto;


import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
import com.hanium.chungyakpassback.domain.standard.아파트분양정보_공급금액1;
import com.hanium.chungyakpassback.repository.standard.아파트분양정보1repository;
import com.hanium.chungyakpassback.repository.standard.아파트분양정보_공급금액1repository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import javax.persistence.Column;

@Getter
public class 아파트분양정보_공급금액1Dto {
    private String 주택형;
    private String 공급금액;
    private Integer 공고번호;

//    public 아파트분양정보_공급금액1 toEntity(아파트분양정보_공급금액1Dto 아파트분양정보_공급금액1dto) {
//        return 아파트분양정보_공급금액1.builder()
//                .주택형(아파트분양정보_공급금액1dto.get주택형())
//                .공급금액(아파트분양정보_공급금액1dto.get공급금액())
//                .아파트분양정보1(아파트분양정보1)
//                .build();
//    }

    @Builder
    public 아파트분양정보_공급금액1Dto(JSONObject itemJson){
        this.공고번호 = itemJson.getInt("pblancno");
        if(itemJson.get("housety") instanceof Double) {
            this.주택형 = String.valueOf(itemJson.get("housety")); //Double -> String 1번방식
        }
        else{
            this.주택형 = itemJson.getString("housety");
        }
        this.공급금액 = itemJson.getString("lttottopamount");
    }

    public void PrintDate(){
        System.out.print("주택형" +주택형);
        System.out.print("공급금액"+공급금액);
        System.out.println();
    }
}
