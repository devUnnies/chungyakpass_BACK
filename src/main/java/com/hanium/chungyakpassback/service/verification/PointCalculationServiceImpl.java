package com.hanium.chungyakpassback.service.verification;

import com.hanium.chungyakpassback.dto.verification.GeneralMinyeongPointDto;
import com.hanium.chungyakpassback.entity.input.*;
import com.hanium.chungyakpassback.enumtype.*;
import com.hanium.chungyakpassback.repository.input.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PointCalculationServiceImpl implements PointCalculationService {
    final HouseMemberRepository houseMemberRepository;
    final HouseRepository houseRepository;
    final UserBankbookRepository userBankbookRepository;
    final GeneralPrivateVerificationServiceImpl generalPrivateVerificationServiceImpl;
    final HouseMemberPropertyRepository houseMemberPropertyRepository;
    final HouseMemberRelationRepository houseMemberRelationRepository;
    List<Boolean> resultOfHouseHavingList = new ArrayList<>();//무주택여부를 저장하는 리스트
    List<Boolean> bothParentsHouseHavingList = new ArrayList<>();//부모님 두분다 무주택자인지 확인하는 리스트
    List<LocalDate> lateDateList = new ArrayList<>();//배우자와 본인중 무주택시점이 늦은날을 저장하는 리스트
    int point = 0;
    int parents = 0;
    int numberOfFamily = 0;
    LocalDate lateDate;

    public List periodHomeless(HouseMemberRelation memberRelation) {
        if ((generalPrivateVerificationServiceImpl.calcAmericanAge(memberRelation.getOpponent().getBirthDay()) >= 30) || memberRelation.getOpponent().getMarriageDate() != null) {//만나이가 30세 이상이거나 결혼했으먼
            LocalDate birthDayAfter30Year = memberRelation.getOpponent().getBirthDay().plusYears(30);
            if (memberRelation.getOpponent().getMarriageDate() == null || ((generalPrivateVerificationServiceImpl.calcAmericanAge(memberRelation.getOpponent().getBirthDay()) >= 30) && memberRelation.getOpponent().getMarriageDate() != null)) {//(1)만30세이상 미혼이거나 만30세 이후 혼인신고 시 만 30세 생일과 무주택이 된 날짜중에 늦은 날
                if (birthDayAfter30Year.isAfter(memberRelation.getOpponent().getHomelessStartDate())) {
                    lateDate = birthDayAfter30Year;
                } else {
                    lateDate = memberRelation.getOpponent().getHomelessStartDate();
                }
            } else {//만30세 이전 혼인신고시 혼인신고일과 무주택된 날중 늦은 날짜
                if (memberRelation.getOpponent().getMarriageDate().isAfter(memberRelation.getOpponent().getHomelessStartDate())) {
                    lateDate = memberRelation.getOpponent().getMarriageDate();
                } else {
                    lateDate = memberRelation.getOpponent().getHomelessStartDate();
                }
            }
            lateDateList.add(lateDate);
        }
        return lateDateList;
    }

    @Override
    public Integer periodOfHomelessness(User user) {
        List<HouseMemberRelation> houseMemberRelationList = houseMemberRelationRepository.findAllByUser(user);
        for (HouseMemberRelation memberRelation : houseMemberRelationList) {
            // 배우자가 결혼전에 팔았을 때
            if (memberRelation.getRelation().getRelation().equals(Relation.배우자) && (memberRelation.getOpponent().getMarriageDate().isAfter(memberRelation.getOpponent().getHomelessStartDate()))) {
                // 본인정보로 등급을 매겨야 한다.
                for (HouseMemberRelation member : houseMemberRelationList) {
                    if (member.getRelation().getRelation().equals(Relation.본인)) {
                        if ((generalPrivateVerificationServiceImpl.calcAmericanAge(member.getOpponent().getBirthDay()) < 30 && member.getOpponent().getMarriageDate() == null) || member.getOpponent().getHomelessStartDate() == null || member.getOpponent().getForeignerYn().equals(Yn.y)) {//만30세미만 미혼이거나 무주택시작일이 없으면 0점
                            return point;
                        } else {
                            lateDateList = periodHomeless(member);
                            System.out.println(lateDateList);
                        }
                    }
                }


            }
            // 배우자가 결혼 후에 팔았을 때 또는 집판날과 결혼한날이 같을 때
            else {
                if (memberRelation.getRelation().getRelation().equals(Relation.배우자) || memberRelation.getRelation().getRelation().equals(Relation.본인)) {
                    System.out.println(memberRelation.getOpponent().getName());
                    if ((generalPrivateVerificationServiceImpl.calcAmericanAge(memberRelation.getOpponent().getBirthDay()) < 30 && memberRelation.getOpponent().getMarriageDate() == null) || memberRelation.getOpponent().getHomelessStartDate() == null || memberRelation.getOpponent().getForeignerYn().equals(Yn.y)) {//만30세미만 미혼이거나 무주택시작일이 없으면 0점
                        return point;
                    } else {
                        lateDateList = periodHomeless(memberRelation);
                        System.out.println(lateDateList);
                    }
                }
            }
        }
        //반환값을 가지고 늦은 순서대로 정렬
        lateDateList.sort(Collections.reverseOrder());
        LocalDate mostLateDate = lateDateList.get(0);
        //무주택기간을 기간으로 계산함
        int periodOfHomelessness = generalPrivateVerificationServiceImpl.calcAmericanAge(mostLateDate);
        for (int z = 1; z <= 15; z++) {
            if (periodOfHomelessness < z) {
                return point = z * 2;
            } else point = 32;
        }
        return point;
    }

    public boolean meetHouseHaving(HouseMember houseMember, GeneralMinyeongPointDto generalMinyeongPointDto) {
        int flag = 0;

        List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

        //무주택여부계산
        for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
            System.out.println(houseMemberProperty.getProperty());
            Optional<HouseMemberRelation> houseMemberRelation = houseMemberRelationRepository.findByOpponent(houseMember);
            if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//소유주택이 주거용이면
                if (houseMemberRelation.get().getRelation().getRelation().equals(Relation.부) || houseMemberRelation.get().getRelation().getRelation().equals(Relation.모) || houseMemberRelation.get().getRelation().getRelation().equals(Relation.조모)
                        || houseMemberRelation.get().getRelation().getRelation().equals(Relation.조부)) {
                    if (!(generalMinyeongPointDto.getTypeOfApplication().equals("노인부양"))) {
                        resultOfHouseHavingList.add(Boolean.TRUE);
                    } else {
                        resultOfHouseHavingList.add(Boolean.FALSE);
                    }
                }
                if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.오피스텔)) {
                    resultOfHouseHavingList.add(Boolean.TRUE);
                } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) {
                    resultOfHouseHavingList.add(Boolean.TRUE);
                } else if (houseMemberProperty.getExclusiveArea() <= 60 && houseMemberProperty.getAmount() < 80000000) {
                    flag++;
                    if (flag <= 1)
                        resultOfHouseHavingList.add(Boolean.TRUE);
                    else
                        resultOfHouseHavingList.add(Boolean.FALSE);
                }
            } else resultOfHouseHavingList.add(Boolean.FALSE);
        }
        System.out.println(resultOfHouseHavingList);
        //무주택리스트를 받아서 하나라도 false를 반환하면 false반환
        if (resultOfHouseHavingList.contains(Boolean.FALSE)) {
            return false;
        } else return true;
    }

    public Integer countOfDependents(HouseMemberRelation memberRelation, GeneralMinyeongPointDto generalMinyeongPointDto) {
        System.out.println(memberRelation.getRelation());
        //무주택여부를 확인하기 위해 meetHouseHaving메소드 실행 시 true반환하면 parents++하고 houseMemberList3에 true값 저장
        if (meetHouseHaving(memberRelation.getOpponent(), generalMinyeongPointDto)) {
            parents++;
            bothParentsHouseHavingList.add(Boolean.TRUE);
        } else {//아니면 flase저장
            bothParentsHouseHavingList.add(Boolean.FALSE);
        }
        //houseMemberList3가 하나라도 flase반환하면 0
        if (bothParentsHouseHavingList.contains(Boolean.FALSE)) {
            numberOfFamily = 0;
        } else {//아니면 parents반환
            numberOfFamily = parents;
        }
        return numberOfFamily;
    }

    @Override
    public Integer numberOfDependents(User user, GeneralMinyeongPointDto generalMinyeongPointDto) { //부양가족 산출하기
        List<HouseMemberRelation> houseMemberRelationList = houseMemberRelationRepository.findAllByUser(user);
        for (HouseMemberRelation memberRelation : houseMemberRelationList) {
            if (memberRelation.getRelation().getRelation().equals(Relation.배우자)) {//배우자는 무조건 포함
                numberOfFamily++;
            }
            if (user.getHouse() == user.getSpouseHouse() || user.getSpouseHouse() == null) { //배우자와 같은 세대이거나, 미혼일 경우
                if (memberRelation.getRelation().getRelation().equals(Relation.본인) && generalPrivateVerificationServiceImpl.isHouseholder(user)) //본인이 세대주일 때 무주택직계존속 포함
                {
                    for (HouseMemberRelation myParents : houseMemberRelationList) {
                        if ((myParents.getRelation().getRelation().equals(Relation.부) || myParents.getRelation().getRelation().equals(Relation.모)) && myParents.getUser().equals(user) && myParents.getOpponent().getForeignerYn().equals(Yn.n)) {
                            //외국인이 아닌 부또는 모의 무주택여부를 확인한다.
                            numberOfFamily = numberOfFamily + countOfDependents(myParents, generalMinyeongPointDto);
                        } else if ((myParents.getRelation().getRelation().equals(Relation.조모) || myParents.getRelation().getRelation().equals(Relation.조부)) && myParents.getUser().equals(user) && myParents.getOpponent().getForeignerYn().equals(Yn.n)) {
                            numberOfFamily = numberOfFamily + countOfDependents(myParents, generalMinyeongPointDto);
                        } else if ((myParents.getRelation().getRelation().equals(Relation.배우자의부) || myParents.getRelation().getRelation().equals(Relation.배우자의모)) && myParents.getUser().equals(user) && myParents.getOpponent().getForeignerYn().equals(Yn.n)) {
                            numberOfFamily = numberOfFamily + countOfDependents(myParents, generalMinyeongPointDto);
                        } else if ((myParents.getRelation().getRelation().equals(Relation.배우자의조부) || myParents.getRelation().getRelation().equals(Relation.배우자의조모)) && myParents.getUser().equals(user) && myParents.getOpponent().getForeignerYn().equals(Yn.n)) {
                            numberOfFamily = numberOfFamily + countOfDependents(myParents, generalMinyeongPointDto);
                        }

                    }
                }
            } else if (user.getHouse() != user.getSpouseHouse()) {
                if (memberRelation.getRelation().getRelation().equals(Relation.배우자) && user.getHouse().getHouseHolder().equals(user.getSpouseHouseMember())) { //배우자가 세대주일 때 무주택직계존속 포함
                    for (HouseMemberRelation wifeParents : houseMemberRelationList) {
                        if ((wifeParents.getRelation().getRelation().equals(Relation.부) || wifeParents.getRelation().getRelation().equals(Relation.모)) && wifeParents.getUser().equals(user) && wifeParents.getOpponent().getForeignerYn().equals(Yn.n)) {
                            //외국인이 아닌 부또는 모의 무주택여부를 확인한다.
                            numberOfFamily = numberOfFamily + countOfDependents(wifeParents, generalMinyeongPointDto);
                        } else if ((wifeParents.getRelation().getRelation().equals(Relation.조모) || wifeParents.getRelation().getRelation().equals(Relation.조부)) && wifeParents.getUser().equals(user) && wifeParents.getOpponent().getForeignerYn().equals(Yn.n)) {
                            numberOfFamily = numberOfFamily + countOfDependents(wifeParents, generalMinyeongPointDto);
                        }
                        if ((wifeParents.getRelation().getRelation().equals(Relation.배우자의부) || wifeParents.getRelation().getRelation().equals(Relation.배우자의모)) && wifeParents.getOpponent().getHouse().equals(user.getSpouseHouse()) && wifeParents.getOpponent().getForeignerYn().equals(Yn.n)) {
                            numberOfFamily = numberOfFamily + countOfDependents(wifeParents, generalMinyeongPointDto);
                        } else if ((wifeParents.getRelation().getRelation().equals(Relation.배우자의조부) || wifeParents.getRelation().getRelation().equals(Relation.배우자의조모)) && wifeParents.getOpponent().getHouse().equals(user.getSpouseHouse()) && wifeParents.getOpponent().getForeignerYn().equals(Yn.n)) {
                            numberOfFamily = numberOfFamily + countOfDependents(wifeParents, generalMinyeongPointDto);
                        }

                    }
                }
            }
            if (memberRelation.getOpponent().getForeignerYn().equals(Yn.n) && memberRelation.getRelation().getRelation().equals(Relation.자녀)) {
                if (memberRelation.getOpponent().getMarriageDate() == null) {//미혼 자녀
                    numberOfFamily++;
                }
            } else {
                if (memberRelation.getOpponent().getForeignerYn().equals(Yn.n) && memberRelation.getRelation().getRelation().equals(Relation.손자녀)) {//부모가 죽은 미혼 손자녀
                    numberOfFamily++;
                }
            }

        }
        System.out.println(numberOfFamily);
        for (int z = 1; z <= 6; z++) {
            if (numberOfFamily < z) {
                return point = z * 5;
            } else point = 35;
        }
        return point;
    }

    public int periodOfMonth(LocalDate joinDate) {
        LocalDate now = LocalDate.now();
        Period period = joinDate.until(now);
        return period.getYears() * 12 + period.getMonths();
    }

    @Override
    public Integer bankbookJoinPeriod(User user) {
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        int joinPeriodOfMonth = periodOfMonth(optUserBankbook.get().getJoinDate());
        int joinPeriodOfYear = generalPrivateVerificationServiceImpl.calcAmericanAge(optUserBankbook.get().getJoinDate());
        if (joinPeriodOfMonth < 12) {
            for (int z = 1; z <= 2; z++) {
                if (joinPeriodOfMonth < z * 6) {
                    point = z;
                }
            }
        } else {
            for (int z = 2; z <= 15; z++) {
                if (joinPeriodOfYear < z) {
                    return point = z + 1;
                } else point = 17;
            }
        }
        return point;
    }
}







