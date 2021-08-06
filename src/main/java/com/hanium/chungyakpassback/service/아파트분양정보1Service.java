//package com.hanium.chungyakpassback.service;//package com.hanium.chungyakpassback.service;
//
//import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
//import com.hanium.chungyakpassback.dto.아파트분양정보1Dto;
//
//import com.hanium.chungyakpassback.repository.standard.아파트분양정보1repository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@RequiredArgsConstructor
//@Service
//public class 아파트분양정보1Service {
//
//    private final 아파트분양정보1repository 아파트분양정보1Repository;
////private static 아파트분양정보1repository 아파트분양정보1Repository;
//
//
//    //private final ApiDetailExplorer2 apiDetailExplorer2;
//   // private final ApiDetailExplorer1 apiDetailExplorer1;
//
//    //빌더패턴
//    @Transactional
//    public void saveData(Integer 공고번호, 아파트분양정보1 apt) {
//        //System.out.println("!!!!!!!!!!!"+공고번호);
//        아파트분양정보1Repository.save(apt);
//        //아파트분양정보1Repository.findById(공고번호).orElseGet(() -> 아파트분양정보1Repository.save(apt));
//    }
//
//        //생성자패턴
////        @Transactional
////    public void saveData1(Integer 공고번호, 아파트분양정보1Dto 아파트분양정보1dto) {
////        System.out.println(공고번호);
////        아파트분양정보1Repository.findById(공고번호).orElseGet(() -> 아파트분양정보1Repository.save(아파트분양정보1dto));
////    }
//
////    //빌드패턴
////    @Transactional
////    public 아파트분양정보1.아파트분양정보1Builder create1(아파트분양정보1.아파트분양정보1Builder apt) {
////       아파트분양정보1 아파트분양정보3 = new 아파트분양정보1.아파트분양정보1Builder(apt);
////        return 아파트분양정보1Repository.save(apt);
////    }
//
////    //생성자패턴
////    @Transactional
////    public 아파트분양정보1 create(아파트분양정보1Dto 아파트분양정보1dto) {
////        return 아파트분양정보1Repository.save(아파트분양정보1dto);
////    }
//
//
//}
//
