package com.hanium.chungyakpassback.service;

import com.hanium.chungyakpassback.domain.enumtype.예치금액지역구분;
import com.hanium.chungyakpassback.domain.enumtype.지역_레벨1;
import com.hanium.chungyakpassback.domain.input.주소;
import com.hanium.chungyakpassback.domain.input.청약자격점검;
import com.hanium.chungyakpassback.domain.input.청약자격점검_다자녀가구;
import com.hanium.chungyakpassback.domain.input.회원;
import com.hanium.chungyakpassback.domain.standard.지역_레벨_1;
import com.hanium.chungyakpassback.sevice.다자녀검증service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class 다자녀검증serviceTest {
    @Autowired
    다자녀검증service 다자녀검증service;

    @Test
    public void get() {
        final 회원 회원1 = 회원.builder().이메일("980lje@naver.com").비밀번호("test123").build();
        final String 이메일 = 회원1.get이메일();
        assertEquals("980lje@naver.com", 이메일);
    }

    @Test
    public void get이메일() {
        final 회원 회원1 = 회원.builder().이메일("980lje@naver.com").비밀번호("test123").build();
        final String 이메일 = 회원1.get이메일();
        assertEquals("jejjang98@daum.net", 이메일);
    }

    @Test
    public void 자녀수() {
        final 청약자격점검_다자녀가구 점검1 = 청약자격점검_다자녀가구.builder().미성년자녀수(2).build();
        System.out.println(다자녀검증service.자녀수(점검1.get미성년자녀수())); // false 출력

        final 청약자격점검_다자녀가구 점검2 = 청약자격점검_다자녀가구.builder().미성년자녀수(3).build();
        System.out.println(다자녀검증service.자녀수(점검2.get미성년자녀수())); // true 출력
    }

    @Test
    public void 주택형변환() {
        청약자격점검 점검1 = 청약자격점검.builder().주택형("102.9838A").build();

        System.out.println(다자녀검증service.주택형변환(점검1.get주택형())); // . 기준으로 앞에 문자열만 남겨놓고 출력
        System.out.println(다자녀검증service.주택형변환(점검1.get주택형()) - 10); // int 형으로 형변환 됐는지 확인 위해서 연산 테스트
    }

    @Test
    public void 지역_레벨1비교() {
//        if (정보.get지역_레벨1()==지역_레벨1.강원도)

//        주소 주소1 = 주소.builder().지역_레벨1(지역_레벨1.서울시).build();
//        일순위_납입금 납입금 = 일순위_납입금.builder().예치금액지역구분(예치금액지역구분.서울부산).build();
//
//        System.out.println(다자녀검증service.지역_레벨1get(주소1, 납입금));
        주소 주소1 = 주소.builder().지역_레벨1(지역_레벨1.서울시).build();
        지역_레벨_1 주소2 = 지역_레벨_1.builder().지역_레벨1(지역_레벨1.서울시).예치금액지역구분(예치금액지역구분.서울부산).build();

        if (주소1.get지역_레벨1() == 주소2.get지역_레벨1()) {
            예치금액지역구분 대체 = 주소2.get예치금액지역구분();
        }

    }
}
