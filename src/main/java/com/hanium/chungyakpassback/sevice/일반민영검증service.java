package com.hanium.chungyakpassback.sevice;


import com.hanium.chungyakpassback.domain.input.세대;
import com.hanium.chungyakpassback.domain.input.세대구성원;

import java.util.List;
import java.util.Optional;


public interface 일반민영검증service {
    public boolean 청약통장충족여부();

    public int 만나이계산();

    public boolean 세대주여부();

    public boolean 규제지역여부();

    //    public boolean 규제지역로직();


    public boolean 전세대원5년이내당첨이력존재여부();

}
