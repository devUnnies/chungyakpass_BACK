package com.hanium.chungyakpassback.dto.verification;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialKookminPublicMultiChildDto {

    @NotNull
    public Integer notificationNumber;

    @NotNull
    public String housingType;

}
