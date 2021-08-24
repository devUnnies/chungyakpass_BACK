//package com.hanium.chungyakpassback.service;
//
//import com.hanium.chungyakpassback.dto.UserBankbookDto;
//import com.hanium.chungyakpassback.entity.enumtype.HousingType;
//import com.hanium.chungyakpassback.repository.input.*;
//import com.hanium.chungyakpassback.repository.standard.AptInfoRepository;
//import com.hanium.chungyakpassback.repository.standard.AreaLevel1Repository;
//import com.hanium.chungyakpassback.repository.standard.PriorityDepositAmountRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@RequiredArgsConstructor
//@Service
//public class Minyoung  implements Minyoungservice{
//    final HouseholdMemberRepository householdMemberRepository;//세대구성원
//    final HouseholdRepository householdRepository;//세대 리포지토리
//    final UserBankbookRepository userBankbookRepository;//청약통장
//    final PriorityDepositAmountRepository priorityDepositAmountRepository;
//    final AreaLevel1Repository areaLevel1Repository;
//    final UserRepository userRepository;//청약통장
//    final AptInfoRepository aptInfoRepository;//아파트 분양정보
//    final HouseMemberApplicationDetailsRepository houseMemberApplicationDetailsRepository;//세대구성원_청약신청이력repository
//
//    @Override
//    public boolean 청약통장충족여부() {// 주택유형이 국민일 경우 청약통장종류는 주택청약종합저축 or 청약저축이어야 true
//        if (aptInfoRepository.findById(2021000580).get().getHousingType().equals(HousingType.민영)) {
//            return true;
//        }
//        return false;
//    }
//    @Override
//    public boolean 예치금액충족() {
//        if (priorityDepositAmountRepository.findById(1L).get().) {
//        }
//        return false;
//    }
//
////    @Override
////    public boolean 청약통장충족여부() {// 주택유형이 국민일 경우 청약통장종류는 주택청약종합저축 or 청약저축이어야 true
////        if (userRepository.findById(1L).get().getEmail().equals("admin@gmail.com")) {
////            return true;
////        }
////        return false;
////    }
//
//
//}
