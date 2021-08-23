package com.hanium.chungyakpassback.service;//package com.hanium.chungyakpassback.service;
//
//import com.hanium.chungyakpassback.domain.input.청약자격점검;
//import com.hanium.chungyakpassback.domain.input.청약자격점검_다자녀가구;
//import com.hanium.chungyakpassback.repository.input.주소repository;
//import com.hanium.chungyakpassback.repository.input.청약자격점검_다자녀가구repository;
//import com.hanium.chungyakpassback.repository.input.청약자격점검repository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class 다자녀검증serviceImpl implements 다자녀검증service {
//
//    @Autowired
//    청약자격점검repository 청약자격점검repository;
//    청약자격점검_다자녀가구repository 청약자격점검_다자녀가구repository;
//    주소repository 주소repository;
//
//    @Override
//    public boolean 자녀수(int 미성년자녀수) { // 미성년자녀가 3명 이상일 경우 true, 그렇지 않으면 false 출력
//        청약자격점검_다자녀가구 점검 = 청약자격점검_다자녀가구.builder().미성년자녀수(미성년자녀수).build();
//        if (점검.get미성년자녀수() >= 3) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public int 주택형변환(String 주택형) { // . 기준으로 주택형 자른후 <일순위_납입금> 에서 면적 비교를 위해서 int 형으로 형변환
//        청약자격점검 점검 = 청약자격점검.builder().주택형(주택형).build();
//        String typeChange = 점검.get주택형().substring(0, 점검.get주택형().indexOf("."));
//        int int_typeChange = Integer.parseInt(typeChange);
//
//        return int_typeChange;
//    }
////
////    @Override
////    public boolean 지역_레벨1get(주소 주소, 일순위_납입금 납입금) {
//////        주소 주소1 = 주소.get지역_레벨1()
//////        일순위_납입금 납입금1 = 일순위_납입금.builder().build();
////
////        if (주소.get지역_레벨1().equals(지역_레벨1.서울시)) {
////
////        }
////
////        if (주소.get지역_레벨1().equals(납입금.get예치금액지역구분())) {
////            return true;
////        }
////        return false;
////
////    }
//}