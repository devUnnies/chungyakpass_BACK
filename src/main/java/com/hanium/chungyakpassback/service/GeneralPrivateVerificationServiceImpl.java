//package com.hanium.chungyakpassback.service;
//
//import com.hanium.chungyakpassback.dto.UserBankbookDto;
//import com.hanium.chungyakpassback.entity.enumtype.HousingType;
//import com.hanium.chungyakpassback.entity.enumtype.Yn;
//import com.hanium.chungyakpassback.entity.standard.AptInfo;
//import com.hanium.chungyakpassback.repository.input.*;
//import com.hanium.chungyakpassback.repository.standard.AptInfoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//
//
//@Service
//public class GeneralPrivateVerificationServiceImpl {
//
//    @Autowired
//    HouseholdMemberRepository householdMemberRepository;//세대구성원
//    HouseholdRepository householdRepository;//세대 리포지토리
//    UserBankbookRepository userBankbookRepository;//청약통장
//    AptInfoRepository aptInfoRepository;//아파트 분양정보
//    HouseMemberApplicationDetailsRepository houseMemberApplicationDetailsRepository;//세대구성원_청약신청이력repository
//
////    // 객체 생성
////    회원_청약통장 청약통장 = 회원_청약통장.builder().청약통장종류(청약통장종류.청약저축).build();
////    세대 세대1 = 세대.builder().build();
////    세대구성원 구성원 = 세대구성원.builder().세대(세대1).세대주여부(여부.y).이름("이지은").생년월일(LocalDate.of(1998, 11, 28)).외국인여부(여부.y).장기복무군인여부(여부.n).build();
//        AptInfo aptInfo = AptInfo.builder().housingType(HousingType.국민주택).speculationOverheated(Yn.y).subscriptionOverheated(Yn.n).build();
//        //주택유형(주택유형.민영).투기과열지구(여부.y).청약과열지역(여부.n).build();
////    세대구성원_청약신청이력 신청이력 = 세대구성원_청약신청이력.builder().주택명("테스트").결과(결과.당첨).build();
//
////    @Transactional
////    public void 청약통장충족여부(Long id, UserBankbookDto UserBankbookdto) {// 주택유형이 국민일 경우 청약통장종류는 주택청약종합저축 or 청약저축이어야 true
////
////        if (aptInfoRepository.findAptInfoBynotificationNumber(UserBankbookdto.toEntity().).contains(HousingType.국민주택)) {
////            if (userBankbookRepository.findOneBysubscription_bankbook_type(userBankbookDto.toEntity().getBankbookType()).orElse(null) != null) {
////                throw new RuntimeException("유효한 청약통장이 아니다.");
////            }
////        }
////    }
//
//
//        if (aptInfo..equals(HousingType.국민주택)) { // 주택유형이 국민일 경우 청약통장종류는 주택청약종합저축 or 청약저축이어야 true
//            if (청약통장.get청약통장종류().equals(청약통장종류.주택청약종합저축) || 청약통장.get청약통장종류().euals(청약통장종류.청약저축)) {
//                return true;
//            }
//        }
//
//        if (분양정보.get주택유형().equals(주택유형.민영)) { // 주택유형이 민영일 경우 청약통장종류는 주택청약종합저축 or 청약예금 or 청약부금이어야 true
//            if (청약통장.get청약통장종류().equals(청약통장종류.주택청약종합저축) || 청약통장.get청약통장종류().equals(청약통장종류.청약예금) || 청약통장.get청약통장종류().equals(청약통장종류.청약부금)) {
//                return true;
//            }
//        }
//
//        return false; // 그 이외에는 false
//    }
//
//    @Override
//    public int 만나이계산() {
//        LocalDate now = LocalDate.now();
//        LocalDate parsedBirthDate = 구성원.get생년월일();
//
//        int americanAge = now.minusYears(parsedBirthDate.getYear()).getYear();
//
//        // 생일이 지났는지 여부를 판단
//        if (parsedBirthDate.plusYears(americanAge).isAfter(now)) {
//            americanAge = americanAge - 1;
//        }
//
//        return americanAge;
//    }
//
//    @Override
//    public boolean 세대주여부() {
//        if (구성원.get세대주여부().equals(여부.y)) {
//            return true; // 세대주여부 y 이면 true 출력
//        } else if (구성원.get세대주여부().equals(여부.n)) {
//            return false; // 세대주여부 n 이면 false 출력
//        }
//        return false;
//    }
//
//    @Override
//    public boolean 규제지역여부() {
//        if (분양정보.get투기과열지구().equals(여부.y) || 분양정보.get청약과열지역().equals(여부.y)) {
//            return true; // 투기과열지구 or 청약과열지역이면 규제지역
//        }
//        return false; // 그 외에는 false
//    }
//
//
////    @Override
////    public boolean 규제지역로직() {
////        if (규제지역여부()) { // 규제지역여부가 true 일 경우 세대주여부 메소드를 실행
////            System.out.println("세대주여부 : " + 세대주여부());
////        }
////    }
//
//    public List<세대> findAll() {
//        return 세대repository.findAll();
//    }
//
//    @Override
//    public boolean 전세대원5년이내당첨이력존재여부() {
//
//
//        return true;
//    }
//
//}
