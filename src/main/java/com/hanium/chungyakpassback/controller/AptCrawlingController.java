//package com.hanium.chungyakpassback.controller;//package com.hanium.chungyakpassback.controller;
//
//import com.hanium.chungyakpassback.domain.standard.아파트분양정보;
//import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
//import com.hanium.chungyakpassback.dto.아파트분양정보1Dto;
//import com.hanium.chungyakpassback.repository.standard.아파트분양정보1repository;
//import com.hanium.chungyakpassback.repository.아파트분양정보1repository;
//import com.hanium.chungyakpassback.service.ApiDetailExplorer;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class AptCrawlingController<ApiDetailExplorer> {
//    private final 아파트분양정보1repository 아파트분양정보1Repository;
//    private final ApiDetailExplorer apiDetailExplorer;
//
//    @PostMapping("/aptData")
//    public List<아파트분양정보1> aptData1() throws IOException {
//        List<아파트분양정보1> aptDatasList = aptCrawlingService3.getAptDatas();
//        for(int i = 0; i< aptDatasList.size(); i++){
//            아파트분양정보1Repository.save(aptDatasList.get(i));
//        }
//        return aptDatasList;
//    }
//
//    @PostMapping ("/aptData1")
//    public 아파트분양정보1 aptData1(@RequestBody 아파트분양정보1Dto 아파트분양정보1dto ){
//        아파트분양정보1 아파트분양정보 = new 아파트분양정보1(아파트분양정보1dto);
//            return 아파트분양정보1Repository.save(아파트분양정보);
//    }
//
//    @GetMapping("/aptData")
//    public  List<아파트분양정보1> aptData() throws IOException {
//        return 아파트분양정보1Repository.findAll();
//    }
//
//}
