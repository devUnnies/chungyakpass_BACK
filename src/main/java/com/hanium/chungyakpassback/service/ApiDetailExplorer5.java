package com.hanium.chungyakpassback.service;//package com.hanium.chungyakpassback.service;


import com.hanium.chungyakpassback.domain.standard.*;
import com.hanium.chungyakpassback.dto.*;
import com.hanium.chungyakpassback.repository.standard.*;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ApiDetailExplorer5 {
    private final 아파트분양정보1repository 아파트분양정보1repository;
    private final 아파트분양정보_공급금액1repository 아파트분양정보_공급금액1repository;
    private final 아파트분양정보_공급대상1repository 아파트분양정보_공급대상1repository;
    private final 아파트분양정보_청약접수일정1repository 아파트분양정보_청약접수일정1repository;
    private final 아파트분양정보_특별공급대상1repository 아파트분양정보_특별공급대상1repository;
    public static int INDENT_FACTOR = 4;
    public static String jsonPrettyPrintString;
    public static String 분양정보조회 = "http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getLttotPblancList";
    public static String 주택형별상세조회 = "http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getAPTLttotPblancMdl";
    public static String 분양정보상세조회 = "http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getAPTLttotPblancDetail";
    public static int cout;
    String date = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

    // manageNo가 없을 때는 '0' 값을 넣어주어야 한다.
    public String GetAptApi(String aptUrl, int pageNo, int manageNo) {
        try {
            StringBuilder urlBuilder = new StringBuilder(aptUrl); /*URL*///주택번호와 url을 받는다.
            urlBuilder.append("?").append(URLEncoder.encode("ServiceKey", StandardCharsets.UTF_8)).append("=BmqDlfkTCepN%2F%2B8XBveSElPppoonFGeljKmlxIZDZV589UGOa%2B3U3sHN5fCNuT2jBnOn1iTVWFQTdDMcRdnohA%3D%3D"); /*Service Key*/

            urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8));

            if (manageNo == 0) {
                urlBuilder.append("&").append(URLEncoder.encode("startmonth", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("202108", StandardCharsets.UTF_8)); /*월 단위 모집공고일 (검색시작월)*/
                urlBuilder.append("&").append(URLEncoder.encode("endmonth", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(date, StandardCharsets.UTF_8)); /*월 단위 모집공고일 (검색종료월, 최대 12개월)*/
            } else {
                urlBuilder.append("&").append(URLEncoder.encode("houseManageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(manageNo), StandardCharsets.UTF_8)); /*주택관리번호*/
                urlBuilder.append("&").append(URLEncoder.encode("pblancNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(manageNo), StandardCharsets.UTF_8)); /*공고번호*/
            }

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            //System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());//xml을 json으로 변환한다.
            jsonPrettyPrintString = xmlJSONObj.toString(INDENT_FACTOR);//json을 예쁘게 변환


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("!!!!!!!!");
        }
        return jsonPrettyPrintString;
    }

    public void apiDetailExplorer5() throws IOException {
        // 아래의 전체 코드가 일정한 시간간격을 두고 실행되어야한다.
        List<Integer> numbers = new ArrayList<>();//주택관리번호만 따로 리스트에 저장
        List<아파트분양정보_청약접수일정1Dto> 아파트분양정보_청약정보일정1DtoList = new ArrayList<>();
        List<아파트분양정보_공급대상1Dto> 아파트분양정보_공급대상1DtoList = new ArrayList<>();

        List<아파트분양정보_특별공급대상1Dto> 아파트분양정보_특별공급대상1DtoList = new ArrayList<>();
        List<아파트분양정보1Dto> 아파트분양정보1DtoList = new ArrayList<>();
        List<아파트분양정보_공급금액1Dto> 아파트분양정보_공급금액1DtoList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();
        List<아파트분양정보1crawlingDto> 아파트분양정보1crawlingDtoList = new ArrayList<>();

        // 아파트분양정보 2Dto 정보를 리스트로 담는다.
        List<아파트분양정보2Dto> 아파트분양정보2DtoList = new ArrayList<>();


        int pageNo = 1;
        // 페이지 수를 구해야한다.
        //String result = apiExplorer.AptApiData(pageNo);
        String result = GetAptApi(분양정보조회, pageNo, 0);
        JSONObject rjson = new JSONObject(result);
        JSONObject response = (JSONObject) rjson.get("response");//받아온 json에서 원하는 정보를 필터링한다.
        JSONObject body = (JSONObject) response.get("body");
        //System.out.println(body);
        int totalCount = body.getInt("totalCount");
        pageNo = totalCount / 10;
        int pagesNo = totalCount % 10;
        if (pagesNo != 0) {
            pageNo = pageNo + 1;
        }

        // 공고번호만 뽑았는데
        // 문의처, 주택유형, 건설업체도 뽑아야함
        // 아파트분양정보1DTO에다가 넣어주면된다.
        for (int i = 1; i <= pageNo; i++) {
            // apiExplorer.AptApiData(i);/페이지수만큼 AptApiData를 호출
            GetAptApi(분양정보조회, i, 0);
            rjson = new JSONObject(jsonPrettyPrintString);
            response = (JSONObject) rjson.get("response");//받아온 json에서 원하는 정보를 필터링한다.
            body = (JSONObject) response.get("body");
            JSONObject items = body.getJSONObject("items");
            //System.out.println(items);

            if (items.get("item") instanceof JSONArray) {//item이 array형식으로 들어올때
                JSONArray item = (JSONArray) items.get("item");
                int a = item.length();

                for (int j = 0; j < item.length(); j++) {//item의 크기만큼 반복
                    JSONObject itemJson = item.getJSONObject(j);
                    // houseManageNo 공고번호이다.
                    // 공고번호, 주택유형, 건설업체 정보를 가져온다.
                    아파트분양정보2Dto 아파트분양정보2dto = new 아파트분양정보2Dto(itemJson);
                    아파트분양정보2DtoList.add(아파트분양정보2dto);

                    numbers.add(아파트분양정보2dto.공고번호);

                    //System.out.println(numbers);
                }
            } else {//item이 object형식으로 들어올때
                JSONObject itemJson = items.getJSONObject("item");
                //int houseManageNo = itemJson.getInt("houseManageNo");//주택관리번호 뽑아냄
                아파트분양정보2Dto 아파트분양정보2dto = new 아파트분양정보2Dto(itemJson);
                아파트분양정보2DtoList.add(아파트분양정보2dto);

                numbers.add(아파트분양정보2dto.공고번호);

                //System.out.println(numbers);
            }


        }
        for (Integer number : numbers) {
            String spec = "https://www.applyhome.co.kr/ai/aia/selectAPTLttotPblancDetail.do?gvPgmId=AIA01M01"
                    + "&houseManageNo="
                    + number
                    + "&pblancNo="
                    + number;
            urlList.add(spec);
        }

        for (int a = 0; a < numbers.size(); a++) {
            Document getDetail = Jsoup.connect(urlList.get(a)).get();
            Elements getContents = getDetail.select("table tbody tr td");
            String content = getDetail.select("ul[class=inde_txt] li").get(0).text();
            String content1 = getDetail.select("ul[class=inde_txt]").get(2).text();
            String[] content2 = content1.split(" ");
            String string = content2[3];
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM");
//            LocalDate date = LocalDate.parse(string, formatter);

            Integer number = numbers.get(a);

            아파트분양정보1crawlingDto 아파트분양정보1crawlingdto = new 아파트분양정보1crawlingDto(content, number, string);
            아파트분양정보1crawlingDtoList.add(아파트분양정보1crawlingdto);

        }


        // 공고번호를 가지고 API에서 데이터를 가져온다.
        for (Integer number : numbers) {//주택관리번호 리스트를 하나씩 반복
            //String value = apiExplorer.GetAptDetail(numbers.get(i), 분양정보상세조회);//주택관리번호와 분양정보 상세 조회url을 GetAptDetail메소드에 보내서 실행
            String value = GetAptApi(분양정보상세조회, 1, number);
            rjson = new JSONObject(value);//반환값을 필터링
            response = (JSONObject) rjson.get("response");
            body = (JSONObject) response.get("body");

            if (body.get("items") instanceof JSONObject) {//주택번호와 url은 있는데 값이 안들어온 경우 값이 object형식으로 들어왔나 확인
                JSONObject items = (JSONObject) body.get("items");
                JSONObject itemJson = items.getJSONObject("item");//item크기만큼 dto에 저장
                //System.out.println(items);

                아파트분양정보_청약접수일정1Dto 아파트분양정보_청약정보일정1dto = new 아파트분양정보_청약접수일정1Dto(itemJson);//item크기만큼 청약접수일정1Dto에 저장
                아파트분양정보_청약정보일정1DtoList.add(아파트분양정보_청약정보일정1dto);

                아파트분양정보1Dto 아파트분양정보1dto = new 아파트분양정보1Dto(itemJson);

                for (아파트분양정보1crawlingDto 아파트분양정보1crawlingDto : 아파트분양정보1crawlingDtoList) {
                    if (아파트분양정보1crawlingDto.공고번호.equals(아파트분양정보1dto.공고번호)) {
                        아파트분양정보1dto.투기과열지구 = 아파트분양정보1crawlingDto.투기과열지구;
                        아파트분양정보1dto.청약과열지역 = 아파트분양정보1crawlingDto.청약과열지역;
                        아파트분양정보1dto.위축지역 = 아파트분양정보1crawlingDto.위축지역;
                        아파트분양정보1dto.분양가상한제 = 아파트분양정보1crawlingDto.분양가상한제;
                        아파트분양정보1dto.공공주택지구 = 아파트분양정보1crawlingDto.공공주택지구;
                        아파트분양정보1dto.공공건설임대주택 = 아파트분양정보1crawlingDto.공공건설임대주택;
                        아파트분양정보1dto.대규모택지개발지구 = 아파트분양정보1crawlingDto.대규모택지개발지구;
                        아파트분양정보1dto.정비사업 = 아파트분양정보1crawlingDto.정비사업;
                        아파트분양정보1dto.입주예정월 = 아파트분양정보1crawlingDto.입주예정월;
                        아파트분양정보1dto.공공주택특별법적용 = 아파트분양정보1crawlingDto.공공주택특별법적용;
                    }
                }
                for (com.hanium.chungyakpassback.dto.아파트분양정보2Dto 아파트분양정보2Dto : 아파트분양정보2DtoList) {
                    if (아파트분양정보2Dto.공고번호.equals(아파트분양정보1dto.공고번호)) {
                        아파트분양정보1dto.주택유형 = 아파트분양정보2Dto.주택유형;
                        아파트분양정보1dto.건설업체 = 아파트분양정보2Dto.건설업체;
                        아파트분양정보1dto.지역_레벨1 = 아파트분양정보2Dto.지역_레벨1;
                    }
                }
                아파트분양정보1DtoList.add(아파트분양정보1dto);
            }
        }

        System.out.println("아파트분양정보List: " + 아파트분양정보1DtoList.size());


        // 주택관리번호 리스트만큼 반복 - 13개
        for (Integer number : numbers) {
            //String value = apiExplorer.GetAptDetail(numbers.get(i), 주택형별상세조회);//주택관리번호와 url을 GetAptDetail에 넣는다.
            String value = GetAptApi(주택형별상세조회, 1, number);

            rjson = new JSONObject(value);
            response = (JSONObject) rjson.get("response");
            body = (JSONObject) response.get("body");
            cout++;

            // item들이 담기는 JSONObject
            List<JSONObject> objects = new ArrayList<>();
            if (body.get("items") instanceof JSONObject) {//주택번호와 url은 있는데 값이 안들어온 경우 값이 object형식으로 들어왔나 확인
                JSONObject items = body.getJSONObject("items");

                if (items.get("item") instanceof JSONArray) {//item이 array형식인지 확인
                    JSONArray item = (JSONArray) items.get("item");

                    // 아파트분양정보1DtoList에 있는 아파트분양정보1Dto를 순서대로 전부 불러온다.
                    for (int j = 0; j < item.length(); j++) {//item크기만큼 dto에 저장
                        JSONObject itemJson = item.getJSONObject(j);
                        objects.add(itemJson);
                    }

                    // Array 타입이 아닌 Item
                } else {
                    JSONObject itemJson = items.getJSONObject("item");//item크기만큼 dto에 저장
                    objects.add(itemJson);
                }

                // 각 주택관리 번호별 총 item개수
                int totalCount1 = body.getInt("totalCount");
                // totalCount가 10개 이상이면 페이지 넘버를 1개 추가한다
                if (totalCount1 > 10) {
                    // 페이지 넘버를 1개 추가해서 값을 가져온다.
                    String value2 = GetAptApi(주택형별상세조회, 2, number);
                    rjson = new JSONObject(value2);
                    response = (JSONObject) rjson.get("response");
                    body = (JSONObject) response.get("body");
                    cout++;

                    if (body.get("items") instanceof JSONObject) {//주택번호와 url은 있는데 값이 안들어온 경우 값이 object형식으로 들어왔나 확인
                        JSONObject items2 = body.getJSONObject("items");

                        if (items.get("item") instanceof JSONArray) {//item이 array형식인지 확인
                            JSONArray item = (JSONArray) items2.get("item");

                            // 아파트분양정보1DtoList에 있는 아파트분양정보1Dto를 순서대로 전부 불러온다.
                            for (int j = 0; j < item.length(); j++) {//item크기만큼 dto에 저장
                                JSONObject itemJson = item.getJSONObject(j);
                                objects.add(itemJson);
                            }
                        } else {
                            JSONObject itemJson = items.getJSONObject("item");//item크기만큼 dto에 저장
                            objects.add(itemJson);
                        }
                    }
                }
            }
            // 아파트분양정보1DtoList의 첫번째 값을 objects 개수 만큼 돌린다.
            for (아파트분양정보1Dto 분양정보1 : 아파트분양정보1DtoList) {
                if (분양정보1.공고번호.equals(number)) {
                    for (JSONObject item : objects) {
                        // ID만 받는다.
                        //아파트분양정보_공급대상1Dto 아파트분양정보_공급대상1dto = new 아파트분양정보_공급대상1Dto(분양정보1.공고번호, item);

                        아파트분양정보_공급대상1Dto 아파트분양정보_공급대상1dto = new 아파트분양정보_공급대상1Dto(item);
                        아파트분양정보_공급대상1DtoList.add(아파트분양정보_공급대상1dto);

                        아파트분양정보_공급금액1Dto 아파트분양정보_공급금액1dto = new 아파트분양정보_공급금액1Dto(item);
                        아파트분양정보_공급금액1DtoList.add(아파트분양정보_공급금액1dto);

                        아파트분양정보_특별공급대상1Dto 아파트분양정보_특별공급대상1dto = new 아파트분양정보_특별공급대상1Dto(item);
                        아파트분양정보_특별공급대상1DtoList.add(아파트분양정보_특별공급대상1dto);
                    }
                }
            }
        }

        // 아파트분양정보 테이블에 공고번호가 중복되는 값이 들어가면 안된다.
        for (아파트분양정보1Dto 분양정보1 : 아파트분양정보1DtoList) {
            List<아파트분양정보1> 아파트분양정보1List = 아파트분양정보1DtoList.stream()
                    .map(아파트분양정보1Dto::toEntity)
                    .collect(Collectors.toList());

            아파트분양정보1repository.findById(분양정보1.공고번호).orElseGet(() -> {
                아파트분양정보1repository.saveAll(아파트분양정보1List);
                for (아파트분양정보_공급금액1Dto 공급금액1 : 아파트분양정보_공급금액1DtoList) {
                    아파트분양정보1 아파트분양정보1 = 아파트분양정보1repository.findById(공급금액1.get공고번호()).get();
                    아파트분양정보_공급금액1 아파트분양정보_공급금액11 = 아파트분양정보_공급금액1.builder()
                            .아파트분양정보1(아파트분양정보1)
                            .공급금액(공급금액1.get공급금액())
                            .주택형(공급금액1.get주택형())
                            .build();
                    아파트분양정보_공급금액1repository.save(아파트분양정보_공급금액11);
                }
                for (아파트분양정보_청약접수일정1Dto 청약접수일정1 : 아파트분양정보_청약정보일정1DtoList) {
                    아파트분양정보1 아파트분양정보1 = 아파트분양정보1repository.findById(청약접수일정1.get공고번호()).get();
                    아파트분양정보_청약접수일정1 아파트분양정보_청약접수일정11 = 아파트분양정보_청약접수일정1.builder()
                            .아파트분양정보1(아파트분양정보1)
                            .특별공급접수시작일(청약접수일정1.get특별공급접수시작일())
                            .특별공급접수종료일(청약접수일정1.get특별공급접수종료일())
                            .일순위접수일해당지역(청약접수일정1.get일순위접수일해당지역())
                            .일순위접수일경기지역(청약접수일정1.get일순위접수일경기지역())
                            .일순위접수일기타지역(청약접수일정1.get일순위접수일기타지역())
                            .이순위접수일해당지역(청약접수일정1.get이순위접수일해당지역())
                            .이순위접수일경기지역(청약접수일정1.get이순위접수일경기지역())
                            .이순위접수일기타지역(청약접수일정1.get이순위접수일기타지역())
                            .홈페이지(청약접수일정1.get홈페이지())
                            .build();
                    아파트분양정보_청약접수일정1repository.save(아파트분양정보_청약접수일정11);
                }
                for (아파트분양정보_특별공급대상1Dto 특별공급대상1 : 아파트분양정보_특별공급대상1DtoList) {
            아파트분양정보1 아파트분양정보1 = 아파트분양정보1repository.findById(특별공급대상1.get공고번호()).get();
            아파트분양정보_특별공급대상1 아파트분양정보_특별공급대상11 = 아파트분양정보_특별공급대상1.builder()
                    .아파트분양정보1(아파트분양정보1)
                    .주택형(특별공급대상1.get주택형())
                    .공급세대수_다자녀가구(특별공급대상1.get공급세대수_다자녀가구())
                    .공급세대수_신혼부부(특별공급대상1.get공급세대수_신혼부부())
                    .공급세대수_생애최초(특별공급대상1.get공급세대수_생애최초())
                    .공급세대수_노부모부양(특별공급대상1.get공급세대수_노부모부양())
                    .공급세대수_기관추천(특별공급대상1.get공급세대수_기관추천())
                    .공급세대수_이전기관(특별공급대상1.get공급세대수_이전기관())
                    .공급세대수_기타(특별공급대상1.get공급세대수_기타())
                    .build();
                아파트분양정보_특별공급대상1repository.save(아파트분양정보_특별공급대상11);
        }

        // 아파트분양정보 테이블에 공고번호가 이미 있다면 공급대상 테이블에 해당공고번호와 관련된 공급대상은 추가하지 않는다.
        for (아파트분양정보_공급대상1Dto 공급대상1 : 아파트분양정보_공급대상1DtoList) {
            아파트분양정보1 아파트분양정보1 = 아파트분양정보1repository.findById(공급대상1.get공고번호()).get();
            아파트분양정보_공급대상1 아파트분양정보_공급대상11 = 아파트분양정보_공급대상1.builder()
                    .아파트분양정보1(아파트분양정보1)
                    .주택형(공급대상1.get주택형())
                    .주택공급면적(공급대상1.get주택공급면적())
                    .공급세대수_일반(공급대상1.get공급세대수_일반())
                    .공급세대수_특별(공급대상1.get공급세대수_특별())
                    .공급세대수_계(공급대상1.get공급세대수_계())
                    .build();
            아파트분양정보_공급대상1repository.save(아파트분양정보_공급대상11);

        }




                return null;
            });
        }




    }
}

