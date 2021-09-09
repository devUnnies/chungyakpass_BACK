//package com.hanium.chungyakpassback.repository;
//
//import com.hanium.chungyakpassback.service.verification.PointCalculationService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class PointCalculationServiceTest {
//    @Autowired
//    PointCalculationService PointCalculationService;
//
////    House house = House.builder().build();
////
////    //세대구성원 구성원 = 세대구성원.builder().세대(세대1).세대주여부(여부.y).이름("이지은").생년월일(LocalDate.of(1998, 11, 28)).외국인여부(여부.y).장기복무군인여부(여부.n).build();
////    HouseMember houseMember = HouseMember.builder().house(house).name("이지은").birthDate(LocalDate.of(1998, 11, 28)).foreignerYn(Yn.y).soldierYn(Yn.n).build();
////    HouseMember houseMember1 = HouseMember.builder().house(house).name("김지호").birthDate(LocalDate.of(1998, 2, 12)).foreignerYn(Yn.n).soldierYn(Yn.n).build();
////
////    User user = User.builder().houseMember(houseMember).email("kim41900@naver.com").password("kim41900").build();
//
//    @Test
//    public void periodOfHomelessness(){
//        System.out.println("청약통장충족여부: "+PointCalculationService.periodOfHomelessness());
//    }
//
//    @Test
//    public void bankbookJoinPeriod(){
//        System.out.println("청약통장충족여부: "+PointCalculationService.bankbookJoinPeriod(user));
//    }
//
//    @Test
//    public void numberOfDependents(){
//        System.out.println("청약통장충족여부: "+PointCalculationService.numberOfDependents());
//    }
//
////    @Test
////    public void meetHouseHaving(){
////        System.out.println("청약통장충족여부: "+PointCalculationService.meetHouseHaving());
////    }
//
//
//}
