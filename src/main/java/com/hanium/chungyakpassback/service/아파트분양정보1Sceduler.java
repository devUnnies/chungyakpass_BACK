package com.hanium.chungyakpassback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
public class 아파트분양정보1Sceduler {
    //private final ApiDetailExplorer4 apiDetailExplorer4;
    //private final ApiDetailExplorer3 apiDetailExplorer3;
    private final ApiDetailExplorer5 apiDetailExplorer5;
    @Scheduled(cron = "*/10 * * * * *")
    public void saveData(){
       //apiDetailExplorer3.apiDetailExplorer3();
        //apiDetailExplorer4.apiDetailExplorer4();
       apiDetailExplorer5.apiDetailExplorer5();
    }
}
