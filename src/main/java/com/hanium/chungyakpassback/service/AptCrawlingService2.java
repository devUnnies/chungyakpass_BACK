package com.hanium.chungyakpassback.service;//package com.hanium.chungyakpassback.service;
//
//import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class AptCrawlingService2 {
//
//    private static String APT_DATAS_URL = "https://www.applyhome.co.kr/ai/aia/selectAPTLttotPblancListView.do";
//
//    @PostConstruct
//    public List<아파트분양정보1.아파트분양정보1Builder> getAptDatas() throws IOException {
//        List<아파트분양정보1.아파트분양정보1Builder> aptDatasList = new ArrayList<>();
//        Document doc = Jsoup.connect(APT_DATAS_URL).get();
//        Elements contents = doc.select("table tbody tr");
//
//        for (Element content : contents) {
//            Elements tdContents = content.select("td");
//            아파트분양정보1.아파트분양정보1Builder aptDatas = 아파트분양정보1.builder()
//                    // .지역_레벨1(지역_레벨1.valueOf(tdContents.get(0).text()))//enum값
//                    .지역_레벨1(tdContents.get(0).text())
//                    .주택유형(tdContents.get(1).text())
//                    .주택명(tdContents.get(3).text())
//                    .건설업체(tdContents.get(4).text())
//                    .문의처(tdContents.get(5).text())
//                    .모집공고일(LocalDate.parse(tdContents.get(6).text()))
//                    .당첨자발표일(LocalDate.parse(tdContents.get(8).text()));
//            aptDatas.build();
//
//            aptDatasList.add(aptDatas);
//        }
//        for (int i = 0; i < aptDatasList.size(); i++) {
//            System.out.println(aptDatasList.get(i));
//        }
//        return aptDatasList;
//
//    }
//}
//
//
//
//
