package com.hanium.chungyakpassback.dto;


import com.hanium.chungyakpassback.domain.standard.아파트분양정보_공급금액1;
import com.hanium.chungyakpassback.domain.standard.아파트분양정보_공급대상1;
import lombok.Getter;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;


@Getter
public class 아파트분양정보_공급대상1Dto {
    private String 주택형;
    private double 주택공급면적;
    private Integer 공급세대수_일반;
    private Integer 공급세대수_특별;
    private Integer 주택관리번호;
    private Integer 공급세대수_계 ;
    private  Integer 공고번호;
    private List<아파트분양정보_공급대상1Dto> 아파트분양정보_공급대상1DtoList;

    public List<아파트분양정보_공급대상1> ListDtoToListEntity(List<아파트분양정보_공급대상1Dto> 아파트분양정보_공급대상1DtoList ){
        return 아파트분양정보_공급대상1DtoList.stream()
        .map(아파트분양정보_공급대상1Dto::toEntity)
        .collect(Collectors.toList());
    }

    public 아파트분양정보_공급대상1 toEntity() {
        return 아파트분양정보_공급대상1.builder()
                .공고번호(공고번호)
                .주택형(주택형)
                .주택공급면적(주택공급면적)
                .공급세대수_일반(공급세대수_일반)
                .공급세대수_특별(공급세대수_특별)
                .주택관리번호(주택관리번호)
                .공급세대수_계(공급세대수_계)
                .build();
    }

    public 아파트분양정보_공급대상1Dto(JSONObject itemJson){
        this.공고번호 = itemJson.getInt("pblancno");
        this.주택공급면적 = itemJson.getDouble("suplyar");
        this.공급세대수_일반 = itemJson.getInt("suplyhshldco");
        this.공급세대수_특별 = itemJson.getInt("spsplyhshldco");
        this.공급세대수_계 =공급세대수_일반+공급세대수_특별;
        if(itemJson.get("housety") instanceof Double) {
            this.주택형 = String.valueOf(itemJson.get("housety")); //Double -> String 1번방식
        }
            else{
                this.주택형 = itemJson.getString("housety");
        }
    }
    public void PrintDate(){
        System.out.print("주택형 " +주택형);
        System.out.print("주택공급면적 "+주택공급면적);
        System.out.print("공급세대수_일반 "+공급세대수_일반);
        System.out.print("공급세대수_특별 "+공급세대수_특별);
        System.out.print("주택관리번호 "+주택관리번호);
        System.out.print("공급세대수_계 "+공급세대수_계);
        System.out.println();
    }
}



