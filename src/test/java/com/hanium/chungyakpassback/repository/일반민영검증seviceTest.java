package com.hanium.chungyakpassback.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;

@SpringBootTest
class 일반민영검증seviceTest {

    @Autowired
    com.hanium.chungyakpassback.service.GeneralPrivateVerificationService GeneralPrivateVerificationservice;

    @Test
    public void 청약통장충족여부() {
        System.out.println("청약통장충족여부: "+GeneralPrivateVerificationservice.청약통장충족여부());
    }

    @Test
    public void 만나이계산() {
        System.out.println("청약통장충족여부: "+GeneralPrivateVerificationservice.만나이계산());
    }

    @Test
    public void 세대주여부() {
        System.out.println("세대주여부: "+GeneralPrivateVerificationservice.세대주여부());
    }

    @Test
    public void 규제지역여부() {
        System.out.println("규제지역여부: "+GeneralPrivateVerificationservice.규제지역여부());
    }

    @Test
    public void 주택소유() {
        System.out.println("주택소유: "+GeneralPrivateVerificationservice.주택소유());
    }

    //    @Test
//    public void 예치금액충족() {
//        System.out.println("예치금액충족: "+GeneralPrivateVerificationservice.예치금액충족());
//    }
    @Test
    public void 예치금액충족() {
        System.out.println("예치금액충족: "+GeneralPrivateVerificationservice.예치금액충족());
    }
    @Test
    public void 가입기간충족() {
        System.out.println("가입기간충족: "+GeneralPrivateVerificationservice.가입기간충족());
    }

    @Test
    public void 인근지역여부() {
        System.out.println("인근지역여부: "+GeneralPrivateVerificationservice.인근지역여부());
    }
    @Test
    public void 특이사항충족() {
        System.out.println("특이사항충족: "+GeneralPrivateVerificationservice.특이사항충족());
    }
    @Test
    public void 전세대원5년이내당첨이력존재여부() {
        System.out.println("전세대원5년이내당첨이력존재여부: "+GeneralPrivateVerificationservice.전세대원5년이내당첨이력존재여부());
    }
    public void 조회() {

    }



//    @Test
//    public void 규제지역로직() {
//        System.out.println(일반민영검증service.규제지역로직());
//    }

}
