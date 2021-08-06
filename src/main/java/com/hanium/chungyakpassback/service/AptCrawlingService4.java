//package com.hanium.chungyakpassback.service;
//
//import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
//import lombok.RequiredArgsConstructor;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class AptCrawlingService4<아파트분양정보1Builder> {
//    int count = 0;
//    private static String APT_DATA_URL = "https://www.applyhome.co.kr/ai/aia/selectAPTLttotPblancListView.do";
//
//    @PostConstruct
//    public List<아파트분양정보1.아파트분양정보1Builder> getAptDatas() throws IOException {
//        List<아파트분양정보1.아파트분양정보1Builder> 아파트분양정보List = new ArrayList<>();
//        Document doc1 = Jsoup.connect(APT_DATA_URL).get();
//        String getTotalPost = doc1.select("b.color_blue").get(1).text();
//        int totalPost = Integer.parseInt(getTotalPost);
//        int totalPage = totalPost/10;
//        int totalPages = totalPost%10;
//        if(totalPages != 0){
//            totalPage = totalPage+1;
//        }
//
//        for(int i=1;i<=totalPage;i++) {
//            String APT_DATAS_URL = APT_DATA_URL + "?pageIndex=" + i;
//            Document doc = Jsoup.connect(APT_DATAS_URL).get();
//            Elements contents = doc.select("table tbody");
//            Elements trContent1 = contents.select("tr");
//            List<String> list = new ArrayList<String>();
//            List<String> urlList = new ArrayList<String>();
//
//            for (Element trContent : trContent1) {
//                Elements tdContents = trContent.select("td");
//                    아파트분양정보1.아파트분양정보1Builder aptDatas = 아파트분양정보1.builder()
//                            // .지역_레벨1(지역_레벨1.valueOf(tdContents.get(0).text()))//enum값
//                            .지역_레벨1(tdContents.get(0).text())
//                            .주택유형(tdContents.get(1).text())
//                            .주택명(tdContents.get(3).text())
//                            .건설업체(tdContents.get(4).text())
//                            .문의처(tdContents.get(5).text())
//                            .모집공고일(LocalDate.parse(tdContents.get(6).text()))
//                            .당첨자발표일(LocalDate.parse(tdContents.get(8).text()));
//                            aptDatas.build();
//                            아파트분양정보List.add(aptDatas);
//
//                    System.out.println(aptDatas);
//            }
//
//
//
//            for (Element content : contents) {
//                Elements trContents = content.select("tr");
//                for (int j = 0; j < trContents.size(); j++) {
//                    String hmno = trContents.get(j).attr("data-hmno");
//                    list.add(hmno);
//                }
//                //System.out.println(trContents.size());
//            }
//            //System.out.println(list);
//
//
//            for(int k =0;k<list.size();k++) {
//                String spec = "https://www.applyhome.co.kr/ai/aia/selectAPTLttotPblancDetail.do?gvPgmId=AIA01M01"
//                        + "&houseManageNo="
//                        + list.get(k)
//                        + "&pblancNo="
//                        + list.get(k);
//                urlList.add(spec);
//
//            }
//            Document getDetail = Jsoup.connect(urlList.get(0)).get();
//            Elements getContents = getDetail.select("table tbody tr");
//           //System.out.println(getContents);
//
//
//        };
//        for(int i=0;i<아파트분양정보List.size();i++){
//            System.out.println(아파트분양정보List.get(i));
//        }
//
//        return 아파트분양정보List;
//
//        }
//    }
//
//
//
//
//
//
//
//
