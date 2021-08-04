//package com.hanium.chungyakpassback.service;
//
//
//
//import com.hanium.chungyakpassback.dto.*;
//import com.hanium.chungyakpassback.repository.standard.아파트분양정보1repository;
//import lombok.RequiredArgsConstructor;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.XML;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class ApiDetailExplorer1 {
//    //private final 아파트분양정보1repository 아파트분양정보1repository;
//    private static 아파트분양정보1repository 아파트분양정보1repository;
//    public static int INDENT_FACTOR = 4;
//    public static String jsonPrettyPrintString;
//    public static String 분양정보조회 = "http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getLttotPblancList";
//    public static String 주택형별상세조회 = "http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getAPTLttotPblancMdl";
//    public static String 분양정보상세조회 = "http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getAPTLttotPblancDetail";
//    public static int cout;
//
//    @PostConstruct
//    public String GetAptApi(String aptUrl, int pageNoOrMangeNo, boolean isPageNo) {
//
//        try {
//            StringBuilder urlBuilder = new StringBuilder(aptUrl); /*URL*///주택번호와 url을 받는다.
//            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=BmqDlfkTCepN%2F%2B8XBveSElPppoonFGeljKmlxIZDZV589UGOa%2B3U3sHN5fCNuT2jBnOn1iTVWFQTdDMcRdnohA%3D%3D"); /*Service Key*/
//
//            if (isPageNo == true) {
//                urlBuilder.append("&" + URLEncoder.encode("startmonth", "UTF-8") + "=" + URLEncoder.encode("202107", "UTF-8")); /*월 단위 모집공고일 (검색시작월)*/
//                urlBuilder.append("&" + URLEncoder.encode("endmonth", "UTF-8") + "=" + URLEncoder.encode("202108", "UTF-8")); /*월 단위 모집공고일 (검색종료월, 최대 12개월)*/
//                urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNoOrMangeNo), "UTF-8"));
//            } else {
//                urlBuilder.append("&" + URLEncoder.encode("houseManageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNoOrMangeNo), "UTF-8")); /*주택관리번호*/
//                urlBuilder.append("&" + URLEncoder.encode("pblancNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNoOrMangeNo), "UTF-8")); /*공고번호*/
//            }
//
//            URL url = new URL(urlBuilder.toString());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Content-type", "application/json");
//            //System.out.println("Response code: " + conn.getResponseCode());
//            BufferedReader rd;
//            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            } else {
//                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//            }
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                sb.append(line);
//            }
//            rd.close();
//            conn.disconnect();
//            JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());//xml을 json으로 변환한다.
//            jsonPrettyPrintString = xmlJSONObj.toString(INDENT_FACTOR);//json을 예쁘게 변환
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("!!!!!!!!");
//        }
//        return jsonPrettyPrintString;
//    }
//
//
//    @PostConstruct
//    //@Scheduled(cron = "0 * * * * *")
//    public static void main(String[] args) throws InterruptedException {
//        // 아래의 전체 코드가 일정한 시간간격을 두고 실행되어야한다.
//        List<Integer> numbers = new ArrayList<Integer>();//주택관리번호만 따로 리스트에 저장
//        List<아파트분양정보_청약접수일정1Dto> 아파트분양정보_청약정보일정1DtoList = new ArrayList<>();
//        List<아파트분양정보_공급대상1Dto> 아파트분양정보_공급대상1DtoList = new ArrayList<>();
//        List<아파트분양정보_특별공급대상1Dto> 아파트분양정보_특별공급대상1DtoList = new ArrayList<>();
//        List<아파트분양정보1Dto> 아파트분양정보1DtoList = new ArrayList<>();
//        List<아파트분양정보_공급금액1Dto> 아파트분양정보_공급금액1DtoList = new ArrayList<>();
//
//        // 아파트분양정보 2Dto 정보를 리스트로 담는다.
//        List<아파트분양정보2Dto> 아파트분양정보2DtoList = new ArrayList<>();
//
//        ApiDetailExplorer1 apiExplorer = new ApiDetailExplorer1();
//
//        int pageNo = 1;
//        // 페이지 수를 구해야한다.
//        //String result = apiExplorer.AptApiData(pageNo);
//        String result = apiExplorer.GetAptApi(분양정보조회, pageNo, true);
//        JSONObject rjson = new JSONObject(result);
//        JSONObject response = (JSONObject) rjson.get("response");//받아온 json에서 원하는 정보를 필터링한다.
//        JSONObject body = (JSONObject) response.get("body");
//        //System.out.println(body);
//        int totalCount = body.getInt("totalCount");
//        pageNo = totalCount / 10;
//        int pagesNo = totalCount % 10;
//        if (pagesNo != 0) {
//            pageNo = pageNo + 1;
//        }
//
//        // 공고번호만 뽑았는데
//        // 문의처, 주택유형, 건설업체도 뽑아야함
//        // 아파트분양정보1DTO에다가 넣어주면된다.
//        for (int i = 1; i <= pageNo; i++) {
//            // apiExplorer.AptApiData(i);/페이지수만큼 AptApiData를 호출
//            apiExplorer.GetAptApi(분양정보조회, i, true);
//            rjson = new JSONObject(jsonPrettyPrintString);
//            response = (JSONObject) rjson.get("response");//받아온 json에서 원하는 정보를 필터링한다.
//            body = (JSONObject) response.get("body");
//            JSONObject items = body.getJSONObject("items");
//            // System.out.println(items);
//
//            if (items.get("item") instanceof JSONArray == true) {//item이 array형식으로 들어올때
//                JSONArray item = (JSONArray) items.get("item");
//                int a = item.length();
//
//                for (int j = 0; j < item.length(); j++) {//item의 크기만큼 반복
//                    JSONObject itemJson = item.getJSONObject(j);
//
//                    // houseManageNo 공고번호이다.
//                    // 공고번호, 주택유형, 건설업체 정보를 가져온다.
//                    아파트분양정보2Dto 아파트분양정보2Dto = new 아파트분양정보2Dto();
//                    아파트분양정보2Dto.공고번호 = itemJson.getInt("houseManageNo");//주택관리번호 뽑아냄
//                    아파트분양정보2Dto.주택유형 = itemJson.getString("houseDtlSecdNm");
//                    아파트분양정보2Dto.건설업체 = itemJson.getString("bsnsMbyNm");
//                    아파트분양정보2DtoList.add(아파트분양정보2Dto);
//                    // = itemJson.getInt("houseManageNo");//주택관리번호 뽑아냄
//
//                    numbers.add(아파트분양정보2Dto.공고번호);
//
//                    //System.out.println(itemJson);
//                }
//            } else {//item이 object형식으로 들어올때
//                JSONObject itemJson = items.getJSONObject("item");
//                //int houseManageNo = itemJson.getInt("houseManageNo");//주택관리번호 뽑아냄
//                아파트분양정보2Dto 아파트분양정보2Dto = new 아파트분양정보2Dto();
//                아파트분양정보2Dto.공고번호 = itemJson.getInt("houseManageNo");//주택관리번호 뽑아냄
//                아파트분양정보2Dto.주택유형 = itemJson.getString("houseDtlSecdNm");
//                아파트분양정보2Dto.건설업체 = itemJson.getString("bsnsMbyNm");
//                아파트분양정보2DtoList.add(아파트분양정보2Dto);
//
//                numbers.add(아파트분양정보2Dto.공고번호);
//
//                //System.out.println(itemJson);
//            }
//
//
//        }
//
//        // 공고번호를 가지고 API에서 데이터를 가져온다.
//        for (int i = 0; i < numbers.size(); i++) {//주택관리번호 리스트를 하나씩 반복
//            //String value = apiExplorer.GetAptDetail(numbers.get(i), 분양정보상세조회);//주택관리번호와 분양정보 상세 조회url을 GetAptDetail메소드에 보내서 실행
//            String value = apiExplorer.GetAptApi(분양정보상세조회, numbers.get(i), false);
//            rjson = new JSONObject(value);//반환값을 필터링
//            response = (JSONObject) rjson.get("response");
//            body = (JSONObject) response.get("body");
//
//            if (body.get("items") instanceof JSONObject == true) {//주택번호와 url은 있는데 값이 안들어온 경우 값이 object형식으로 들어왔나 확인
//                JSONObject items = (JSONObject) body.get("items");
//                JSONObject itemJson = items.getJSONObject("item");//item크기만큼 dto에 저장
//
//                아파트분양정보_청약접수일정1Dto 아파트분양정보_청약정보일정1dto = new 아파트분양정보_청약접수일정1Dto(itemJson);//item크기만큼 청약접수일정1Dto에 저장
//                아파트분양정보_청약정보일정1DtoList.add(아파트분양정보_청약정보일정1dto);
//
//                아파트분양정보1Dto 아파트분양정보1dto = new 아파트분양정보1Dto(itemJson);
//
//                for (int z = 0; z < 아파트분양정보2DtoList.size(); z++) {
//                    if (아파트분양정보2DtoList.get(z).공고번호 == 아파트분양정보1dto.공고번호) {
//                        아파트분양정보1dto.주택유형 = 아파트분양정보2DtoList.get(z).주택유형;
//                        아파트분양정보1dto.건설업체 = 아파트분양정보2DtoList.get(z).건설업체;
//                    }
//                }
//                아파트분양정보1DtoList.add(아파트분양정보1dto);
//            }
//        }
//
//
//        for (int i = 0; i < numbers.size(); i++) {//주택관리번호 리스트 크기만큼 반복
//            //String value = apiExplorer.GetAptDetail(numbers.get(i), 주택형별상세조회);//주택관리번호와 url을 GetAptDetail에 넣는다.
//            String value = apiExplorer.GetAptApi(주택형별상세조회, numbers.get(i), false);
//            rjson = new JSONObject(value);
//            response = (JSONObject) rjson.get("response");
//            body = (JSONObject) response.get("body");
//            cout++;
//
//            if (body.get("items") instanceof JSONObject == true) {//주택번호와 url은 있는데 값이 안들어온 경우 값이 object형식으로 들어왔나 확인
//                JSONObject items = body.getJSONObject("items");
//                if (items.get("item") instanceof JSONArray == true) {//item이 array형식인지 확인
//                    JSONArray item = (JSONArray) items.get("item");
//
//                    for (int j = 0; j < item.length(); j++) {//item크기만큼 dto에 저장
//                        JSONObject itemJson = item.getJSONObject(j);
//                        아파트분양정보_공급대상1Dto 아파트분양정보_공급대상1dto = new 아파트분양정보_공급대상1Dto(itemJson);
//                        아파트분양정보_공급대상1DtoList.add(아파트분양정보_공급대상1dto);
//
//                        아파트분양정보_특별공급대상1Dto 아파트분양정보_특별공급대상1dto = new 아파트분양정보_특별공급대상1Dto(itemJson);
//                        아파트분양정보_특별공급대상1DtoList.add(아파트분양정보_특별공급대상1dto);
//
//                        아파트분양정보_공급금액1Dto 아파트분양정보_공급금액1dto = new 아파트분양정보_공급금액1Dto(itemJson);
//                        아파트분양정보_공급금액1DtoList.add(아파트분양정보_공급금액1dto);
//
//
//                    }
//                } else {
//                    JSONObject itemJson = items.getJSONObject("item");//item크기만큼 dto에 저장
//                    for (int k = 0; k < itemJson.length(); k++) {
//                        아파트분양정보_공급대상1Dto 아파트분양정보_공급대상1dto = new 아파트분양정보_공급대상1Dto(itemJson);
//                        아파트분양정보_공급대상1DtoList.add(아파트분양정보_공급대상1dto);
//
//                        아파트분양정보_특별공급대상1Dto 아파트분양정보_특별공급대상1dto = new 아파트분양정보_특별공급대상1Dto(itemJson);
//                        아파트분양정보_특별공급대상1DtoList.add(아파트분양정보_특별공급대상1dto);
//
//                        아파트분양정보_공급금액1Dto 아파트분양정보_공급금액1dto = new 아파트분양정보_공급금액1Dto(itemJson);
//                        아파트분양정보_공급금액1DtoList.add(아파트분양정보_공급금액1dto);
//                    }
//                }
//            }
//
//
////            for (int z = 0; z < 아파트분양정보_공급대상1DtoList.size(); z++) {//아파트분양정보_청약정보일정1Dto에서 가져온 공급세대수_계를 공급대상1Dto로 보냄
////                아파트분양정보_공급대상1DtoList.get(z).공급세대수_계 = 아파트분양정보_청약정보일정1DtoList.get(z).get공급세대수_계();
////            }
//
////        }
////        for (int z = 0; z <아파트분양정보1DtoList.size(); z++) {
////            아파트분양정보1DtoList.get(z).PrintDate();
////        }
//            ////        List<아파트분양정보1> aptDatasList = aptCrawlingService3.getAptDatas();
//////        아파트분양정보1 a = new 아파트분양정보1();
//////        for(int i = 0; i< aptDatasList.size(); i++){
//////            아파트분양정보1Repository.save(aptDatasList.get(i));
//////        }
//
////            for(int z= 0; z<아파트분양정보1DtoList.size();z++) {
////                아파트분양정보1Dto 아파트분양정보1dto = 아파트분양정보1DtoList.get(z);
////                System.out.println(아파트분양정보1dto);
////                //아파트분양정보1repository.findById(아파트분양정보1dto.공고번호).orElse(아파트분양정보1repository.save(아파트분양정보1dto));
////            }
//            //아파트분양정보1Dto dto = 아파트분양정보1repository.findById(aptDatas.get(k).공고번호).orElse(아파트분양정보1repository.save(dto.aptDatas));
//
//        }
//
////            for (int z = 0; z < 아파트분양정보1DtoList.size(); z++) {
////                아파트분양정보1Dto data = 아파트분양정보1DtoList.get(z);
////                System.out.println(data.공고번호);
//////                아파트분양정보1repository.findById(data.공고번호).orElse(아파트분양정보1repository.save(data));
////            }
//        }
//    }
//
//
//
