package com.hanium.chungyakpassback.service;//package com.hanium.chungyakpassback.service;
//
//import com.hanium.chungyakpassback.domain.standard.아파트분양정보;
//import com.hanium.chungyakpassback.dto.아파트분양정보1Dto;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.XML;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ApiExplorer {
//    public static int INDENT_FACTOR = 4;
//    public static String jsonPrettyPrintString;
//    public static int i = 1;
//
//    @PostConstruct
//    public String AptApiData(int i){
//        try {
//            StringBuilder urlBuilder = new StringBuilder("http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getLttotPblancList"); /*URL*/
//            urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=BmqDlfkTCepN%2F%2B8XBveSElPppoonFGeljKmlxIZDZV589UGOa%2B3U3sHN5fCNuT2jBnOn1iTVWFQTdDMcRdnohA%3D%3D"); /*Service Key*/
//            urlBuilder.append("&" + URLEncoder.encode("startmonth","UTF-8") + "=" + URLEncoder.encode("202107", "UTF-8")); /*월 단위 모집공고일 (검색시작월)*/
//            urlBuilder.append("&" + URLEncoder.encode("endmonth","UTF-8") + "=" + URLEncoder.encode("202108", "UTF-8")); /*월 단위 모집공고일 (검색종료월, 최대 12개월)*/
//            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(i), "UTF-8")); /*월 단위 모집공고일 (검색종료월, 최대 12개월)*/
//            URL url = new URL(urlBuilder.toString());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Content-type", "application/json");
//            //System.out.println("Response code: " + conn.getResponseCode());
//            BufferedReader rd;
//            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            } else {
//                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
//            }
//            StringBuilder sb = new StringBuilder();
//            String line;
//            String result = sb.toString();
//            while ((line = rd.readLine()) != null) {
//                sb.append(line);
//            }
//            rd.close();
//            conn.disconnect();
//            JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
//            jsonPrettyPrintString = xmlJSONObj.toString(INDENT_FACTOR);
//
//        } catch (Exception e){
//            e.printStackTrace();
//            System.out.println("!!!!!!!!");
//        }
//        //System.out.println(jsonPrettyPrintString);
//
//        return jsonPrettyPrintString;
//    }
//
//    @PostConstruct
//    public List<아파트분양정보1Dto> fromJSONtoItems(String jsonPrettyPrintString){
//        JSONObject rjson = new JSONObject(jsonPrettyPrintString);
//        JSONObject response = rjson.getJSONObject("response");
//        JSONObject body = response.getJSONObject("body");
//        int totalCount = body.getInt("totalCount");
//        int pageNo = totalCount / 10;
//        int pagesNo = totalCount%10;
//        if(pagesNo !=0){
//            pageNo = pageNo+1;
//        }
//        while ( i < 4) {
//            ApiExplorer apiExplorer = new ApiExplorer();
//            apiExplorer.AptApiData(i);
//            i++;
//        }
//        JSONObject items = body.getJSONObject("items");
//        JSONArray item = items.getJSONArray("item");
//       List<아파트분양정보1Dto> 아파트분양정보1DtoList = new ArrayList<>();
//        for (int j = 0; j < item.length(); j++) {
//            JSONObject itemJson =item.getJSONObject(j);
//            System.out.println(itemJson);
//            아파트분양정보1Dto 아파트분양정보1dto = new 아파트분양정보1Dto(itemJson);
//            아파트분양정보1DtoList.add(아파트분양정보1dto);
//        }
//       return 아파트분양정보1DtoList;
//    }
//}
