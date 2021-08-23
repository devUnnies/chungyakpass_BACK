package com.hanium.chungyakpassback.dto;//package com.hanium.chungyakpassback.entity.dto;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;

@Getter
public class AptInfoAmountDto {
    private Integer notificationNumber;
    private String housingType;
    private String supplyAmount;

    @Builder
    public AptInfoAmountDto(JSONObject itemJson){
        this.notificationNumber = itemJson.getInt("pblancno");
        if(itemJson.get("housety") instanceof Double) {
            this.housingType = String.valueOf(itemJson.get("housety")); //Double -> String 1번방식
        }
        else{
            this.housingType = itemJson.getString("housety");
        }
        this.supplyAmount = itemJson.getString("lttottopamount");
    }
}
