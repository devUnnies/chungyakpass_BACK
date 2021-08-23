package com.hanium.chungyakpassback.dto;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;

@Getter
public class AptInfoTargetDto {
    private Integer notificationNumber;
    private String housingType;
    private Double supplyArea;
    private Integer supplyGeneral;
    private Integer supplySpecial;
    private Integer supplyTotal;
    private String managementNumber;

    @Builder
    public AptInfoTargetDto(JSONObject itemJson){
        this.notificationNumber = itemJson.getInt("pblancno");
        this.supplyArea = itemJson.getDouble("suplyar");
        this.supplyGeneral = itemJson.getInt("suplyhshldco");
        this.supplySpecial = itemJson.getInt("spsplyhshldco");
        this.supplyTotal =supplyGeneral+supplySpecial;
        if(itemJson.get("housety") instanceof Double) {
            this.housingType = String.valueOf(itemJson.get("housety")); //Double -> String 1번방식
        }
        else{
            this.housingType = itemJson.getString("housety");
        }
    }
}
