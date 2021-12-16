package com.hanium.chungyakpassback.dto.point;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointOfSpecialMinyeongOldParentsSupportDto {

    @NotBlank
    private Integer notificationNumber;//공고번호

}
