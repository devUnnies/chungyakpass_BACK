package com.hanium.chungyakpassback.dto.verification;

import com.hanium.chungyakpassback.enumtype.Ranking;
import com.hanium.chungyakpassback.enumtype.Yn;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralMinyeongUpdateDto {

    @NotBlank
    public Long verificationGeneralMinyeongId;

    @NotBlank
    public Yn sibilingSupportYn; //형제자매부양여부

    @NotBlank
    public Ranking ranking; //순위

}
