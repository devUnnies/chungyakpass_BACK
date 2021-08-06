package com.hanium.chungyakpassback.dto;



import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;



@Getter
public class 아파트분양정보2Dto {
    public Integer 공고번호;
    public String 주택유형;
    public String 건설업체;

    public 아파트분양정보1 toEntity(){
        아파트분양정보1 build = 아파트분양정보1.builder()
                .주택유형(주택유형)
                .건설업체(건설업체)
                .공고번호(공고번호)
                .build();
        return build;
    }

    @Builder
    public 아파트분양정보2Dto(JSONObject itemJson){
        this.공고번호 = itemJson.getInt("pblancNo");//주택관리번호 뽑아냄
        this.주택유형 = itemJson.getString("houseDtlSecdNm");
        this.건설업체 = itemJson.getString("bsnsMbyNm");
    }
}



