package com.hanium.chungyakpassback.sevice;

import com.hanium.chungyakpassback.domain.input.주소;
import com.hanium.chungyakpassback.domain.standard.일순위_납입금;

public interface 다자녀검증service {
    public boolean 자녀수(int 미성년자녀수);

    public int 주택형변환(String 주택형);

//    public boolean 지역_레벨1get(주소 주소, 일순위_납입금 납입금);
}
