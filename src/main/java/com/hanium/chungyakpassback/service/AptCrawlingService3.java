package com.hanium.chungyakpassback.service;//package com.hanium.chungyakpassback.service;
//
//
//import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
//import com.hanium.chungyakpassback.domain.standard.지역_레벨_1;
//import com.hanium.chungyakpassback.repository.아파트분양정보1repository;
//import lombok.RequiredArgsConstructor;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.XML;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class AptCrawlingService3 {
//    public static int INDENT_FACTOR = 4;
//    public static String jsonPrettyPrintString;
//    int count = 0;
//    private static String APT_DATA_URL = "https://www.applyhome.co.kr/ai/aia/selectAPTLttotPblancListView.do";
//
//    @PostConstruct
//    public List<아파트분양정보1> getAptDatas() throws IOException {
//        List<아파트분양정보1> 아파트분양정보List = new ArrayList<>();
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
//            List<String> urlList = new ArrayList<String>();
//            List<String> list = new ArrayList<String>();
//
//            for (Element trContent : trContent1) {
//                Elements tdContents = trContent.select("td");
//                    아파트분양정보1 aptDatas = new 아파트분양정보1(
//                            tdContents.get(0).text(),
//                            tdContents.get(1).text(),
//                            tdContents.get(3).text(),
//                            tdContents.get(4).text(),
//                            tdContents.get(5).text(),
//                            LocalDate.parse(tdContents.get(6).text()),
//                            LocalDate.parse(tdContents.get(8).text())
//                    );
//                            // .지역_레벨1(지역_레벨1.valueOf(tdContents.get(0).text()))//enum값
////                            .지역_레벨1(tdContents.get(0).text())
////                            .주택유형(tdContents.get(1).text())
////                            .주택명(tdContents.get(3).text())
////                            .건설업체(tdContents.get(4).text())
////                            .문의처(tdContents.get(5).text())
////                            .모집공고일(LocalDate.parse(tdContents.get(6).text()))
////                            .당첨자발표일(LocalDate.parse(tdContents.get(8).text()));
////                            aptDatas.build();
//                            아파트분양정보List.add(aptDatas);
//                //System.out.println(aptDatas.toString());
//
//            }
//
//            for (Element content : contents) {
//                Elements trContents = content.select("tr");
//                for (int j = 0; j < trContents.size(); j++) {
//                    String hmno = trContents.get(j).attr("data-hmno");
//                    list.add(hmno);
//                }
//                //System.out.println(trContents.size());
//            }
//
//            System.out.println(list);
//
//
//            for(int k =0;k<list.size();k++) {
//
//                StringBuilder urlBuilder = new StringBuilder("http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getAPTLttotPblancDetail"); /*URL*/
//                urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=BmqDlfkTCepN%2F%2B8XBveSElPppoonFGeljKmlxIZDZV589UGOa%2B3U3sHN5fCNuT2jBnOn1iTVWFQTdDMcRdnohA%3D%3D"); /*Service Key*/
//                urlBuilder.append("&" + URLEncoder.encode("houseManageNo","UTF-8") + "=" + URLEncoder.encode(list.get(k), "UTF-8")); /*주택관리번호*/
//                urlBuilder.append("&" + URLEncoder.encode("pblancNo","UTF-8") + "=" + URLEncoder.encode(list.get(k), "UTF-8")); /*공고번호*/
////                String spec = "https://www.applyhome.co.kr/ai/aia/selectAPTLttotPblancDetail.do?gvPgmId=AIA01M01"
////                        + "&houseManageNo="
////                        + list.get(k)
////                        + "&pblancNo="
////                        + list.get(k);
//                URL url = new URL(urlBuilder.toString());
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                conn.setRequestProperty("Content-type", "application/json");
//                //System.out.println("Response code: " + conn.getResponseCode());
//                BufferedReader rd;
//                if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                } else {
//                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//                }
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = rd.readLine()) != null) {
//                    sb.append(line);
//                }
//                rd.close();
//                conn.disconnect();
//                JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
//                jsonPrettyPrintString = xmlJSONObj.toString(INDENT_FACTOR);
//                JSONObject rjson = new JSONObject(jsonPrettyPrintString);
//                JSONObject response = (JSONObject)rjson.get("response");
//                JSONObject body= (JSONObject)response.get("body");
//                //JSONObject items = body.getJSONObject("items");
//                //JSONObject item = items.getJSONObject("item");
//
////                for(int z=0; z< items.length(); z++){
////                  JSONObject item =items.getJSONObject(z);
////                    System.out.println(item);
////                }
//               //System.out.println(body);
//
////        System.out.println(movie);
////       // JSONObject movieInfoResult = (JSONObject) rjson.get("body");
//
//
//                //System.out.println(sb.toString());
//            }
//        }
//
//           // }
////            Document getDetail = Jsoup.connect(urlList.get(0)).get();
////            Elements getContents = getDetail.select("table tbody tr");
//           //System.out.println(getContents);
//
//
//      //  };
//        System.out.println(아파트분양정보List);
//
//        return 아파트분양정보List;
//        }
//
//
//}
//
//
//
//
//
//
//
//
