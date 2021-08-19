package com.hanium.chungyakpassback.service;

import com.hanium.chungyakpassback.domain.enumtype.여부;
import com.hanium.chungyakpassback.domain.input.세대;
import com.hanium.chungyakpassback.domain.input.세대구성원;
import com.hanium.chungyakpassback.domain.standard.아파트분양정보;
import com.hanium.chungyakpassback.repository.input.세대repository;
import com.hanium.chungyakpassback.sevice.일반민영검증service;
import com.hanium.chungyakpassback.sevice.일반민영검증serviceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class 일반민영검증seviceTest {
    @Autowired
    일반민영검증service 일반민영검증service;

    @Test
    public void 청약통장충족여부() {
        System.out.println(일반민영검증service.청약통장충족여부());
    }

    @Test
    public void 만나이계산() {
        System.out.println(일반민영검증service.만나이계산());
    }

    @Test
    public void 세대주여부() {
        System.out.println(일반민영검증service.세대주여부());
    }

    @Test
    public void 규제지역여부() {
        System.out.println(일반민영검증service.규제지역여부());
    }

    @Test
    public void 조회() {

    }


//    @Test
//    public void 규제지역로직() {
//        System.out.println(일반민영검증service.규제지역로직());
//    }

}