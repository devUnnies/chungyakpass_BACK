package com.hanium.chungyakpassback.sevice;

import com.hanium.chungyakpassback.domain.enumtype.결과;
import com.hanium.chungyakpassback.domain.enumtype.여부;
import com.hanium.chungyakpassback.domain.enumtype.주택유형;
import com.hanium.chungyakpassback.domain.enumtype.청약통장종류;
import com.hanium.chungyakpassback.domain.input.세대;
import com.hanium.chungyakpassback.domain.input.세대구성원;
import com.hanium.chungyakpassback.domain.input.세대구성원_청약신청이력;
import com.hanium.chungyakpassback.domain.input.회원_청약통장;
import com.hanium.chungyakpassback.domain.standard.아파트분양정보;
import com.hanium.chungyakpassback.repository.input.세대repository;
import com.hanium.chungyakpassback.repository.input.세대구성원_청약신청이력repository;
import com.hanium.chungyakpassback.repository.input.세대구성원repository;
import com.hanium.chungyakpassback.repository.input.회원_청약통장repository;
import com.hanium.chungyakpassback.repository.standard.아파트분양정보repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class 일반민영검증serviceImpl implements 일반민영검증service {
    @Autowired
    세대구성원repository 세대구성원repository;
    세대repository 세대repository;
    회원_청약통장repository 회원_청약통장repository;
    아파트분양정보repository 아파트분양정보repository;
    세대구성원_청약신청이력repository 세대구성원_청약신청이력repository;

    // 객체 생성
    회원_청약통장 청약통장 = 회원_청약통장.builder().청약통장종류(청약통장종류.청약저축).build();
    세대 세대1 = 세대.builder().build();
    세대구성원 구성원 = 세대구성원.builder().세대(세대1).세대주여부(여부.y).이름("이지은").생년월일(LocalDate.of(1998, 11, 28)).외국인여부(여부.y).장기복무군인여부(여부.n).build();
    아파트분양정보 분양정보 = 아파트분양정보.builder().주택유형(주택유형.민영).투기과열지구(여부.y).청약과열지역(여부.n).build();
    세대구성원_청약신청이력 신청이력 = 세대구성원_청약신청이력.builder().주택명("테스트").결과(결과.당첨).build();

    @Override
    public boolean 청약통장충족여부() {
        if (분양정보.get주택유형().equals(주택유형.국민)) { // 주택유형이 국민일 경우 청약통장종류는 주택청약종합저축 or 청약저축이어야 true
            if (청약통장.get청약통장종류().equals(청약통장종류.주택청약종합저축) || 청약통장.get청약통장종류().equals(청약통장종류.청약저축)) {
                return true;
            }
        }

        if (분양정보.get주택유형().equals(주택유형.민영)) { // 주택유형이 민영일 경우 청약통장종류는 주택청약종합저축 or 청약예금 or 청약부금이어야 true
            if (청약통장.get청약통장종류().equals(청약통장종류.주택청약종합저축) || 청약통장.get청약통장종류().equals(청약통장종류.청약예금) || 청약통장.get청약통장종류().equals(청약통장종류.청약부금)) {
                return true;
            }
        }

        return false; // 그 이외에는 false
    }

    @Override
    public int 만나이계산() {
        LocalDate now = LocalDate.now();
        LocalDate parsedBirthDate = 구성원.get생년월일();

        int americanAge = now.minusYears(parsedBirthDate.getYear()).getYear();

        // 생일이 지났는지 여부를 판단
        if (parsedBirthDate.plusYears(americanAge).isAfter(now)) {
            americanAge = americanAge - 1;
        }

        return americanAge;
    }

    @Override
    public boolean 세대주여부() {
        if (구성원.get세대주여부().equals(여부.y)) {
            return true; // 세대주여부 y 이면 true 출력
        } else if (구성원.get세대주여부().equals(여부.n)) {
            return false; // 세대주여부 n 이면 false 출력
        }
        return false;
    }

    @Override
    public boolean 규제지역여부() {
        if (분양정보.get투기과열지구().equals(여부.y) || 분양정보.get청약과열지역().equals(여부.y)) {
            return true; // 투기과열지구 or 청약과열지역이면 규제지역
        }
        return false; // 그 외에는 false
    }


//    @Override
//    public boolean 규제지역로직() {
//        if (규제지역여부()) { // 규제지역여부가 true 일 경우 세대주여부 메소드를 실행
//            System.out.println("세대주여부 : " + 세대주여부());
//        }
//    }

    public List<세대> findAll() {
        return 세대repository.findAll();
    }

    @Override
    public boolean 전세대원5년이내당첨이력존재여부() {


        return true;
    }

}
