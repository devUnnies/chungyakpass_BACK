package com.hanium.chungyakpassback.dto;


import com.hanium.chungyakpassback.domain.enumtype.주택유형;
import lombok.Getter;
import org.json.JSONObject;

import java.time.LocalDate;


@Getter
public class 아파트분양정보2Dto {
    public int 공고번호;
    public String 주택유형;
    public String 건설업체;
}



