package com.hanium.chungyakpassback.dto.verification;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialPrivateFirstLifeDto {

    @NotNull
    public Integer notificationNumber;

    @NotNull
    public String housingType;
}
