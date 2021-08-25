package com.hanium.chungyakpassback.service;

import com.hanium.chungyakpassback.entity.apt.AptInfoAmount;
import com.hanium.chungyakpassback.entity.enumtype.*;
import com.hanium.chungyakpassback.entity.input.*;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.standard.AreaLevel1;
import com.hanium.chungyakpassback.repository.input.*;
import com.hanium.chungyakpassback.repository.standard.AptInfoRepository;
import com.hanium.chungyakpassback.repository.standard.AreaLevel1Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static com.hanium.chungyakpassback.entity.enumtype.AddressLevel1.부산;
import static com.hanium.chungyakpassback.entity.enumtype.AddressLevel1.서울;

@RequiredArgsConstructor
@Service
public class GeneralPrivateVerificationServiceImpl implements GeneralPrivateVerificationService {

    @Autowired
    HouseholdMemberRepository householdMemberRepository;//세대구성원
    HouseholdRepository householdRepository;//세대 리포지토리
    UserBankbookRepository userBankbookRepository;//청약통장
    AptInfoRepository aptInfoRepository;//아파트 분양정보
    HouseMemberApplicationDetailsRepository houseMemberApplicationDetailsRepository;//세대구성원_청약신청이력repository
    AreaLevel1Repository areaLevel1Repository;

    // 객체 생성
    //회원_청약통장 청약통장 = 회원_청약통장.builder().청약통장종류(청약통장종류.청약저축).build();
    UserBankbook userBankbook = UserBankbook.builder().bankbookType(BankbookType.주택청약종합저축).deposit(500).validYn(Yn.y).joinDate(LocalDate.of(2021, 06, 28)).build();
    Address address = Address.builder().address_level1(서울).build();
    //세대 세대1 = 세대.builder().build();
    House house = House.builder().address(address).build();

    //세대구성원 구성원 = 세대구성원.builder().세대(세대1).세대주여부(여부.y).이름("이지은").생년월일(LocalDate.of(1998, 11, 28)).외국인여부(여부.y).장기복무군인여부(여부.n).build();
    HouseMember houseMember = HouseMember.builder().house(house).householderYn(Yn.y).name("이지은").birthDate(LocalDate.of(1998, 11, 28)).foreignerYn(Yn.y).soldierYn(Yn.n).build();
    HouseMember houseMember1 = HouseMember.builder().house(house).householderYn(Yn.n).name("김지호").birthDate(LocalDate.of(1998, 2, 12)).foreignerYn(Yn.n).soldierYn(Yn.n).build();


    User user = User.builder().houseMember(houseMember).email("kim41900@naver.com").password("kim41900").build();
    HouseMemberRelation houseMemberRelation = HouseMemberRelation.builder().user(user).opponent(houseMember).relation(Relation.본인).build();
    //HouseMemberRelation houseMemberRelation1 = HouseMemberRelation.builder().user(user).opponent(houseMember1).relation(Relation.배우자).build();
    List<HouseMemberProperty> houseMemberPropertyList = new ArrayList<>();
    List<HouseMemberLimitations> houseMemberLimitationsList = new ArrayList<>();


    // 아파트분양정보 분양정보 = 아파트분양정보.builder().주택유형(주택유형.민영).투기과열지구(여부.y).청약과열지역(여부.n).build();
    AptInfo aptInfo = AptInfo.builder().addressLevel1(AddressLevel1.제주).housingType(HousingType.국민).speculationOverheated(Yn.y).atrophyArea(Yn.y).subscriptionOverheated(Yn.n).publicHosingDistrict(Yn.n).publicRentalHousing(Yn.n).build();
    AptInfoAmount aptInfoAmount = AptInfoAmount.builder().aptInfo(aptInfo).housingType("046.9300A").build();
    AreaLevel1 areaLevel1 = AreaLevel1.builder().area_level1(서울).metropolitanArea(Yn.y).build();
    AreaLevel1 areaLevel2 = AreaLevel1.builder().area_level1(AddressLevel1.인천).metropolitanArea(Yn.y).build();
    AreaLevel1 areaLevel3 = AreaLevel1.builder().area_level1(AddressLevel1.경기).metropolitanArea(Yn.y).build();


    //주택유형(주택유형.민영).투기과열지구(여부.y).청약과열지역(여부.n).build();
    //HousingType housingType = HousingType.(HousingType.민영주택).
    //세대구성원_청약신청이력 신청이력 = 세대구성원_청약신청이력.builder().주택명("테스트").결과(결과.당첨).build();
    HouseMemberApplicationDetails houseMemberApplicationDetails = HouseMemberApplicationDetails.builder().houseName("테스트").result(Result.당첨).build();


    String typeChange = aptInfoAmount.getHousingType().substring(0, aptInfoAmount.getHousingType().indexOf("."));
    int int_typeChange = Integer.parseInt(typeChange);
    int hasHouse = 0;
    LocalDate now = LocalDate.now();
    LocalDate parsedBirthDate = houseMember.getBirthDate();
    int americanAge = now.minusYears(parsedBirthDate.getYear()).getYear();

    @Override
    public boolean 청약통장충족여부() {
        if (aptInfo.equals(HousingType.국민)) { // 주택유형이 국민일 경우 청약통장종류는 주택청약종합저축 or 청약저축이어야 true
            if (userBankbook.getBankbookType().equals(BankbookType.주택청약종합저축) || userBankbook.getBankbookType().equals(BankbookType.청약저축)) {
                return true;
            }
        }

        if (aptInfo.equals(HousingType.민영)) {// 주택유형이 민영일 경우 청약통장종류는 주택청약종합저축 or 청약예금 or 청약부금이어야 true
            if (userBankbook.getBankbookType().equals(BankbookType.주택청약종합저축) || userBankbook.getBankbookType().equals(BankbookType.청약예금) || userBankbook.getBankbookType().equals(BankbookType.청약부금)) {
                return true;
            }
        }

        return false; // 그 이외에는 false
    }

    @Override
    public int 만나이계산() {
        // 생일이 지났는지 여부를 판단
        if (parsedBirthDate.plusYears(americanAge).isAfter(now)) {
            americanAge = americanAge - 1;
        }
        return americanAge;
    }


    @Override
    public Yn 세대주여부() {
        if (houseMember.getHouseholderYn().equals(Yn.y)) {
            return Yn.y; // 세대주여부 y 이면 true 출력
        } else if (houseMember.getHouseholderYn().equals(Yn.n)) {
            return Yn.n; // 세대주여부 n 이면 false 출력
        }
        return Yn.n;
    }

//    @Override
//    public boolean 인근지역여부() {//아파트 분양정보의 인근지역과 거주지의 인근지역이 같다면
//        AddressLevel1 userAdressLevel1 = user.getHouseMember().getHouse().getAddress().getAddress_level1();
//
//        List<AreaLevel1> areaLevel1List = areaLevel1Repository.;
//
//
//        // if(user.getHouseMember().getHouse().getAddress().getAddress_level1().)
//
//    }

    @Override
    public boolean 규제지역여부() {
        if (aptInfo.getSpeculationOverheated().equals(Yn.y) || aptInfo.getSubscriptionOverheated().equals(Yn.y)) {
            return true; // 투기과열지구 or 청약과열지역이면 규제지역
        }
        return false; // 그 외에는 false
    }

    @Override
    public boolean 가입기간충족() {
        LocalDate now = LocalDate.now();
        LocalDate JoinDate = userBankbook.getJoinDate();
        Period period = JoinDate.until(now);
        int joinPeriod = period.getYears() * 12 + period.getMonths();
        if (userBankbook.getValidYn().equals(Yn.y)) {
            if (aptInfo.getSpeculationOverheated().equals(Yn.y) || aptInfo.getSubscriptionOverheated().equals(Yn.y)) {
                if (joinPeriod >= 24) {
                    return true;
                }
            } else if (aptInfo.getAtrophyArea().equals(Yn.y)) {
                if (joinPeriod >= 1)
                    return true;
            } else {
                if (aptInfo.getAddressLevel1().equals(areaLevel1Repository.findAllByMetropolitanArea(Yn.y))) {
                    if (joinPeriod >= 12) {
                        return true;
                    }
                } else {
                    if (joinPeriod >= 6) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public boolean 예치금액충족() {
        if ((user.getHouseMember().getHouse().getAddress().getAddress_level1() == 서울 || user.getHouseMember().getHouse().getAddress().getAddress_level1() == 부산)) {
            if (int_typeChange <= 85 && userBankbook.getDeposit() >= 300) {
                return true;
            } else if (int_typeChange <= 102 && userBankbook.getDeposit() >= 600) {
                return true;
            } else if (int_typeChange <= 135 && userBankbook.getDeposit() >= 1000) {
                return true;
            } else if (int_typeChange > 135 && userBankbook.getDeposit() >= 1500) {
                return true;
            }
        }
        if (user.getHouseMember().getHouse().getAddress().getAddress_level1() == AddressLevel1.인천 || user.getHouseMember().getHouse().getAddress().getAddress_level1() == AddressLevel1.대구 || user.getHouseMember().getHouse().getAddress().getAddress_level1() == AddressLevel1.울산 || user.getHouseMember().getHouse().getAddress().getAddress_level1() == AddressLevel1.대전 || user.getHouseMember().getHouse().getAddress().getAddress_level1() == AddressLevel1.광주) {
            if (int_typeChange <= 85 && userBankbook.getDeposit() >= 250) {
                return true;
            } else if (int_typeChange <= 102 && userBankbook.getDeposit() >= 400) {
                return true;
            } else if (int_typeChange <= 135 && userBankbook.getDeposit() >= 700) {
                return true;
            } else if (int_typeChange > 135 && userBankbook.getDeposit() >= 1000) {
                return true;
            }
        } else {
            if (int_typeChange <= 85 && userBankbook.getDeposit() >= 200) {
                return true;
            } else if (int_typeChange <= 102 && userBankbook.getDeposit() >= 300) {
                return true;
            } else if (int_typeChange <= 135 && userBankbook.getDeposit() >= 400) {
                return true;
            } else if (int_typeChange > 135 && userBankbook.getDeposit() >= 500) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean 특이사항충족() {
        if ((int_typeChange > 85 && aptInfo.getPublicRentalHousing().equals(Yn.y)) || (aptInfo.getHousingType().equals(HousingType.민영) && aptInfo.getPublicHosingDistrict().equals(Yn.y) && (aptInfo.getAddressLevel1().equals(서울) || aptInfo.getAddressLevel1().equals(AddressLevel1.인천) || aptInfo.getAddressLevel1().equals(AddressLevel1.경기)))) {
            return true;//진행
        }
        return false;//1순위
    }

    @Override
    public Integer 주택소유() {
        HouseMemberProperty houseMemberproperty =
                HouseMemberProperty.builder()
                        .houseMember(houseMember)
                        .propertyType(PropertyType.건물)
                        .rightSaleYn(Yn.y)
                        .residentialYn(Yn.y)
                        .residentialType(ResidentialType.오피스텔)
                        .nonResidentialType(NonResidentialType.건물)
                        .acquisitionDate(LocalDate.of(1998, 11, 28))
                        .dispositionDate(LocalDate.of(1998, 11, 28))
                        .dedicatedArea(21)
                        .amount(123400)
                        .taxBaseDate(LocalDate.of(1998, 11, 28))
                        .build();
        houseMemberPropertyList.add(houseMemberproperty);


        HouseMemberProperty houseMemberproperty1 =
                HouseMemberProperty.builder()
                        .houseMember(houseMember1)
                        .propertyType(PropertyType.건물)
                        .rightSaleYn(Yn.y)
                        .residentialYn(Yn.y)
                        .residentialType(ResidentialType.단독주택)
                        .nonResidentialType(NonResidentialType.건물)
                        .acquisitionDate(LocalDate.of(1998, 11, 28))
                        .dispositionDate(LocalDate.of(1998, 11, 28))
                        .dedicatedArea(21)
                        .amount(123400)
                        .taxBaseDate(LocalDate.of(1998, 11, 28))
                        .build();
        houseMemberPropertyList.add(houseMemberproperty1);

        for (int i = 0; i < houseMemberPropertyList.size(); i++) {

            if (houseMemberPropertyList.get(i).getResidentialYn().equals(Yn.y)) {//소유주택이 주거용이면
                if (houseMemberPropertyList.get(i).getDedicatedArea() <= 20 || houseMemberPropertyList.get(i).getResidentialType().equals(ResidentialType.오피스텔) || (houseMemberRelation.getRelation().equals(Relation.부모) && americanAge >= 60)) {
                    hasHouse = hasHouse;
                } else {
                    hasHouse = hasHouse + 1;
                }
            }
            hasHouse = hasHouse;

        }
        return hasHouse;
    }


//    //    @Override
//    public boolean 규제지역로직() {
//        if (규제지역여부()) { // 규제지역여부가 true 일 경우 세대주여부 메소드를 실행
//            System.out.println("세대주여부 : " + 세대주여부());
//        }
//    }


    //    public List<세대> findAll() {
//        return 세대repository.findAll();
//    }
//
//    @Override
//    public boolean 전세대원5년이내당첨이력존재여부() {
//        LocalDate now = LocalDate.now();
//        HouseMemberLimitations houseMemberLimitations = HouseMemberLimitations.builder().houseMember(houseMember).windDate(LocalDate.of(1998, 2, 12)).build();
//        houseMemberLimitationsList.add(houseMemberLimitations);
//        HouseMemberLimitations houseMemberLimitations1 = HouseMemberLimitations.builder().houseMember(houseMember1).windDate(LocalDate.of(1998, 2, 12)).build();
//        houseMemberLimitationsList.add(houseMemberLimitations1);
//        for(int i=0;i<houseMemberLimitationsList.size();i++){
//            int reWinning restriction = now.minusYears(houseMemberLimitationsList.get(i).getWindDate().getYear()).getYear();
//            if(houseMemberLimitationsList.get(i).getWindDate())
//                houseMemberLimitationsList.get(i).getHouseMember().getWindDate().
//        }
//        return true;
//    }
}
