package com.hanium.chungyakpassback.dto.verification;

import com.hanium.chungyakpassback.enumtype.Ranking;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralKookminUpdateDto {

    private Yn sibilingSupportYn; //형제자매부양여부

    private Yn twentiesSoleHouseHolderYn; //20대단독세대주여부

    private Ranking ranking; //순위

}