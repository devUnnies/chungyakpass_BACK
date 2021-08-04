package com.hanium.chungyakpassback.dto;


import lombok.Getter;
import org.json.JSONObject;

import javax.persistence.Column;


@Getter
public class 아파트분양정보_특별공급대상1Dto {

    private String 주택형;
    private int 공급세대수_다자녀가구;
    private int 공급세대수_신혼부부;
    private int 공급세대수_생애최초;
    private int 공급세대수_노부모부양;
    private int 공급세대수_기관추천;
    private int 공급세대수_이전기관;
    private int 공급세대수_기관추천기타;


    public 아파트분양정보_특별공급대상1Dto(JSONObject itemJson){

        if(itemJson.get("housety") instanceof Double) {
            this.주택형 = String.valueOf(itemJson.get("housety")); //Double -> String 1번방식
        }
        else{
            this.주택형 = itemJson.getString("housety");
        }
        this.공급세대수_다자녀가구 = itemJson.getInt("mnychhshldco");
        this.공급세대수_신혼부부 = itemJson.getInt("nwwdshshldco");
        this.공급세대수_생애최초 = itemJson.getInt("lfefrsthshldco");
        this.공급세대수_노부모부양 = itemJson.getInt("oldparentssuporthshldco");
        this.공급세대수_기관추천 = itemJson.getInt("insttrecomendhshldco");
        this.공급세대수_기관추천기타 = itemJson.getInt("etchshldco");
        this.공급세대수_이전기관 = itemJson.getInt("transrinsttenfsnhshldco");
    }

    public void PrintDate(){
        System.out.print("주택형" +주택형);
        System.out.print("공급세대수_다자녀가구" +공급세대수_다자녀가구);
        System.out.print("공급세대수_신혼부부"+공급세대수_신혼부부);
        System.out.print("공급세대수_생애최초"+공급세대수_생애최초);
        System.out.print("공급세대수_노부모부양"+공급세대수_노부모부양);
        System.out.print("공급세대수_기관추천"+공급세대수_기관추천);
        System.out.print("공급세대수_기관추천기타"+공급세대수_기관추천기타);
        System.out.print("공급세대수_이전기관"+공급세대수_이전기관);
        System.out.println();
    }
}



