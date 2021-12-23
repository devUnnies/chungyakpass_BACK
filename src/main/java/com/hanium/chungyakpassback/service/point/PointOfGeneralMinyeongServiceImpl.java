package com.hanium.chungyakpassback.service.point;

import com.hanium.chungyakpassback.dto.point.PointOfGeneralMinyeongDto;
import com.hanium.chungyakpassback.dto.point.PointOfGeneralMinyeongResponseDto;
import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.input.*;
import com.hanium.chungyakpassback.entity.point.PointOfGeneralMinyeong;
import com.hanium.chungyakpassback.entity.standard.Bankbook;
import com.hanium.chungyakpassback.enumtype.ErrorCode;
import com.hanium.chungyakpassback.enumtype.Relation;
import com.hanium.chungyakpassback.enumtype.ResidentialBuilding;
import com.hanium.chungyakpassback.enumtype.Yn;
import com.hanium.chungyakpassback.handler.CustomException;
import com.hanium.chungyakpassback.repository.apt.AptInfoRepository;
import com.hanium.chungyakpassback.repository.input.*;
import com.hanium.chungyakpassback.repository.point.PointOfGeneralMinyeongRepository;
import com.hanium.chungyakpassback.repository.standard.BankbookRepository;
import com.hanium.chungyakpassback.service.verification.VerificationOfGeneralMinyeongServiceImpl;
import com.hanium.chungyakpassback.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PointOfGeneralMinyeongServiceImpl implements PointOfGeneralMinyeongService {
    final HouseMemberRepository houseMemberRepository;
    final BankbookRepository bankbookRepository;
    final HouseRepository houseRepository;
    final UserBankbookRepository userBankbookRepository;
    final VerificationOfGeneralMinyeongServiceImpl generalPrivateVerificationServiceImpl;
    final HouseMemberPropertyRepository houseMemberPropertyRepository;
    final HouseMemberRelationRepository houseMemberRelationRepository;
    final UserRepository userRepository;
    final PointOfGeneralMinyeongRepository pointOfGeneralMinyeongRepository;
    final HouseMemberAdditionalInfoRepository houseMemberAdditionalInfoRepository;
    final AptInfoRepository aptInfoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PointOfGeneralMinyeongResponseDto> readGeneralMinyeongResponsePointCalculations() {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();

        List<PointOfGeneralMinyeongResponseDto> pointOfGeneralMinyeongResponseDtos = new ArrayList<>();
        for (PointOfGeneralMinyeong pointOfGeneralMinyeong : pointOfGeneralMinyeongRepository.findAllByUser(user)) {
            PointOfGeneralMinyeongResponseDto pointOfGeneralMinyeongResponseDto = new PointOfGeneralMinyeongResponseDto(pointOfGeneralMinyeong);
            pointOfGeneralMinyeongResponseDtos.add(pointOfGeneralMinyeongResponseDto);
        }

        return pointOfGeneralMinyeongResponseDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PointOfGeneralMinyeongResponseDto createGeneralMinyeongPointCalculation(PointOfGeneralMinyeongDto pointOfGeneralMinyeongDto) {
        User user = userRepository.findOneWithAuthoritiesByEmail(SecurityUtil.getCurrentEmail().get()).get();
        AptInfo aptInfo = aptInfoRepository.findById(pointOfGeneralMinyeongDto.getNotificationNumber()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_APT));

        Integer periodOfHomelessness = periodOfHomelessness(user);
        Integer bankbookJoinPeriod = bankbookJoinPeriod(user);
        Integer numberOfDependents = numberOfDependents(user, aptInfo);
        boolean bankBookVaildYn = bankBookVaildYn(user);
        Integer total = periodOfHomelessness + bankbookJoinPeriod + numberOfDependents;

        PointOfGeneralMinyeong pointOfGeneralMinyeong = new PointOfGeneralMinyeong(user, aptInfo, periodOfHomelessness, bankbookJoinPeriod, numberOfDependents, bankBookVaildYn, total);
        pointOfGeneralMinyeongRepository.save(pointOfGeneralMinyeong);

        return new PointOfGeneralMinyeongResponseDto(pointOfGeneralMinyeong);
    }


    //세대구성원별 무주택기간을 구하는 메소드
    public List periodHomeless(HouseMember houseMember, List lateDateList, LocalDate lateDate) {

        //만 나이가 30세이상이거나 결혼한경우
        if ((generalPrivateVerificationServiceImpl.calcAmericanAge(houseMember.getBirthDay()) >= 30) || houseMember.getMarriageDate() != null) {
            LocalDate birthDayAfter30Year = houseMember.getBirthDay().plusYears(30);

            //만30세이상 미혼이거나 만30세 이후 혼인신고 시 만 30세 생일과 무주택이 된 날짜중에 늦은 날
            if (houseMember.getMarriageDate() == null || birthDayAfter30Year.isBefore(houseMember.getMarriageDate())) {
                if (birthDayAfter30Year.isAfter(houseMember.getHomelessStartDate())) {
                    lateDate = birthDayAfter30Year;
                } else {
                    lateDate = houseMember.getHomelessStartDate();
                }
            } else if (birthDayAfter30Year.isAfter(houseMember.getMarriageDate())) {
                //만30세 이전 혼인신고시 혼인신고일과 무주택된 날중 늦은 날짜
                if (houseMember.getMarriageDate().isAfter(houseMember.getHomelessStartDate())) {
                    lateDate = houseMember.getMarriageDate();
                } else {
                    lateDate = houseMember.getHomelessStartDate();
                }
            }
            lateDateList.add(lateDate);
        }
        for (int i = 0; i < lateDateList.size(); i++) {
            System.out.println("lateDateList" + lateDateList.get(i));
        }
        return lateDateList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer periodOfHomelessness(User user) {
        int point = 0;
        LocalDate lateDate = null;

        if (user.getHouseMember() == null)
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER);

        List<LocalDate> lateDateList = new ArrayList<>();//배우자와 본인중 무주택시점이 늦은날을 저장하는 리스트
        List<HouseMember> houseMemberList = houseMemberRepository.findAllByHouse(user.getHouseMember().getHouse());


        int houseCount = 0;
        Integer totalHouseCount = generalPrivateVerificationServiceImpl.countHouseHaving(user, houseMemberList, houseCount);

        //무주택일경우
        if (totalHouseCount != 0 || (generalPrivateVerificationServiceImpl.calcAmericanAge(user.getHouseMember().getBirthDay()) < 30 && user.getSpouseHouseMember() == null) || user.getHouseMember().getForeignerYn().equals(Yn.y)) {//만30세미만 미혼이거나 무주택시작일이 없으면 0점
            return point = 0;
        }
        //배우자가 있는데 혼인신고일이 null일경우 에러 호출
        else {
            if (user.getSpouseHouseMember() != null && user.getSpouseHouseMember().getMarriageDate() == null) {
                throw new CustomException(ErrorCode.NOT_FOUND_MARRIAGES);
            }
            //배우자가 null 이거나 신청자의 배우자의 무주택 시작일이 혼인신고일 전이면 신청자의 무주택기간만 산정
            else if (user.getSpouseHouseMember() == null || (user.getSpouseHouseMember().getMarriageDate().isAfter(user.getSpouseHouseMember().getHomelessStartDate()))) {
                lateDateList = periodHomeless(user.getHouseMember(), lateDateList, lateDate);
            }
            //아니면 신청자와 배우자의 무주택기간 산정
            else {
                periodHomeless(user.getHouseMember(), lateDateList, lateDate);
                periodHomeless(user.getSpouseHouseMember(), lateDateList, lateDate);
            }
        }
        lateDateList.sort(Collections.reverseOrder());
        LocalDate mostLateDate = lateDateList.get(0);
        System.out.println("mostLateDate" + mostLateDate);
        int periodOfHomelessness = periodOfYear(mostLateDate);
        //무주택기간을 기간으로 계산함
        for (int z = 1; z <= 15; z++) {
            if (periodOfHomelessness < z) {
                return point = z * 2;
            } else point = 32;
        }
        return point;
    }

    public int periodOfYear(LocalDate joinDate) {
        LocalDate now = LocalDate.now();
        int periodOfYear = now.minusYears(joinDate.getYear()).getYear();

        if (joinDate.plusYears(periodOfYear).isAfter(now))
            periodOfYear = periodOfYear - 1;
        return periodOfYear;
    }

    public boolean homelessYn(HouseMember houseMember, int houseCount) {
        List<HouseMemberProperty> houseMemberPropertyList = houseMemberPropertyRepository.findAllByHouseMember(houseMember);

        int flag = 0;
        int specialCase = 0;
        for (HouseMemberProperty houseMemberProperty : houseMemberPropertyList) {
            if (houseMemberProperty.getResidentialBuildingYn().equals(Yn.y)) {//소유주택이 주거용이면
                Optional<HouseMemberRelation> houseMemberRelation = houseMemberRelationRepository.findByOpponent(houseMember);
                if (houseMemberProperty.getResidentialBuilding().equals(ResidentialBuilding.오피스텔)) { //주거용건물유형이 오피스텥일 경우 예외적용
                    specialCase++;
                    continue;
                } else if (houseMemberProperty.getSaleRightYn().equals(Yn.y) && houseMemberProperty.getAcquisitionDate().isBefore(LocalDate.parse("2018-12-11"))) { //2018.12.11 이전에 취득한 분양권일 경우
                    specialCase++;
                    continue;
                } else if (houseMemberProperty.getExceptionHouseYn().equals(Yn.y)) {//예외주택에 해당하는 경우 예외적용
                    specialCase++;
                    continue;
                } else {
                    //면적60이하 수도권 8000만원이하 비수도권 1억3천 이하의 소형주택 일시 예외적용
                    if (houseMemberProperty.getExclusiveArea() <= 60 && ((houseMemberProperty.getMetropolitanBuildingYn().equals(Yn.y) && houseMemberProperty.getAmount() <= 80000000) || (houseMemberProperty.getMetropolitanBuildingYn().equals(Yn.y) && houseMemberProperty.getAmount() <= 130000000))) {
                        flag++;
                        //위의 예외에 하나라도 해당하면 소형주택 특례적용 불가
                        if (specialCase <= 0)
                            continue;
                        else
                            houseCount = flag;
                        if (flag <= 1) // 단, 2호 또는 2세대 이상의 주택 또는 분양권은 제외. 즉, 하나까진 count 안 한다는 의미.
                            continue;
                        else
                            houseCount = flag;
                    }
                }
                houseCount++;
            }
        }
        if (houseCount == 0) // 주택수가 0일 경우 무주택세대구성원으로 판별
            return true;
        else // 아닐 경우 유주택세대구성원으로 판별
            return false;
    }

    public Integer countOfDependents(HouseMemberRelation memberRelation, int parents, List bothParentsIsHomelessYnList) {
        //무주택여부를 확인하기 위해 meetHouseHaving메소드 실행 시 true반환하면 parents++하고 houseMemberList3에 true값 저장
        int houseCount = 0;
        if (homelessYn(memberRelation.getOpponent(), houseCount)) {
            parents++;
            bothParentsIsHomelessYnList.add(Boolean.TRUE);
        } else {//아니면 flase저장
            bothParentsIsHomelessYnList.add(Boolean.FALSE);
        }
        //houseMemberList3가 하나라도 flase반환하면 0
        int numberOfFamily;
        if (bothParentsIsHomelessYnList.contains(Boolean.FALSE)) {
            return numberOfFamily = 0;
        } else {//아니면 parents반환
            numberOfFamily = parents;
        }
        return numberOfFamily;
    }

    //직계존속과 배우자중 한명이라도 무주택자인지 판별하는 메소드
    public Integer numberOfFamily(User user, HouseMemberRelation houseMemberRelation, HouseMemberAdditionalInfo houseMemberAdditionalInfo, int numberOfFamily, int parents, List bothParentsIsHomelessYnList, AptInfo aptInfo) {
        if (houseMemberAdditionalInfo.getStartDateOfSameResident() == null) //청약통장이 null이면 에러 발생
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_STARTDATEOFSAMERESIDENT);
        //일정기간 해외 체류 or요양원 거주하고 있지 않거나 같은 세대에 속하면서 내국인인 경우 직계존속과 그 배우자가 무주택자라면 부양가족에 포함한다.

        if (houseMemberAdditionalInfo.getStartDateOfStayOver() == null) //청약통장이 null이면 에러 발생
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_STARTDATEOFSTAYOVER);

        if (houseMemberAdditionalInfo.getEndDateOfStayOver() == null) //청약통장이 null이면 에러 발생
            throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_ENDDATEOFSTAYOVER);

        if (!(periodOfStayOver(3, houseMemberAdditionalInfo.getStartDateOfStayOver(), houseMemberAdditionalInfo.getEndDateOfStayOver(), aptInfo) > 90) && periodOfYear(houseMemberAdditionalInfo.getStartDateOfSameResident()) >= 3 && houseMemberRelation.getOpponent().getForeignerYn().equals(Yn.n)) {

            if ((houseMemberRelation.getRelation().getRelation().equals(Relation.부) || houseMemberRelation.getRelation().getRelation().equals(Relation.모)) && houseMemberRelation.getUser().equals(user)) {
                numberOfFamily = numberOfFamily + countOfDependents(houseMemberRelation, parents, bothParentsIsHomelessYnList);
            }
            if ((houseMemberRelation.getRelation().getRelation().equals(Relation.조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.조모)) && houseMemberRelation.getUser().equals(user)) {
                numberOfFamily = numberOfFamily + countOfDependents(houseMemberRelation, parents, bothParentsIsHomelessYnList);
            }
            if ((houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조모)) && houseMemberRelation.getUser().equals(user)) {
                numberOfFamily = numberOfFamily + countOfDependents(houseMemberRelation, parents, bothParentsIsHomelessYnList);
            }

            if ((houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의모)) && houseMemberRelation.getUser().equals(user)) {
                numberOfFamily = numberOfFamily + countOfDependents(houseMemberRelation, parents, bothParentsIsHomelessYnList);
            }
        }
        return numberOfFamily;

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer numberOfDependents(User user, AptInfo aptInfo) { //부양가족 산출하기
        int numberOfFamily = 0;
        int point = 0;
        int parents = 0;

        List<Boolean> bothParentsIsHomelessYnList = new ArrayList<>();//부모님 두분다 무주택자인지 확인하는 리스트

        List<HouseMemberRelation> houseMemberRelations = houseMemberRelationRepository.findAllByUser(user);
        for (int i = 0; i < houseMemberRelations.size(); i++) {
            HouseMemberRelation houseMemberRelation = houseMemberRelations.get(i);
            if (houseMemberRelation.getRelation().getRelation().equals(Relation.본인))//본인은 부양가족에 포함하지 않는다.
                continue;
            if (houseMemberRelation.getRelation().getRelation().equals(Relation.배우자)) { //배우자는 부양가족에 무조건 포함
                numberOfFamily++;
                continue;
            }
            HouseMember houseMember = houseMemberRelation.getOpponent();
            HouseMemberAdditionalInfo houseMemberAdditionalInfo = houseMemberAdditionalInfoRepository.findByHouseMember(houseMember);

            if (user.getSpouseHouse() == null) { //배우자와 같은 세대이거나, 미혼일 경우
                if (user.getHouse().getHouseHolder() == null) {
                    throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_HOLDER);
                } else {
                    if (user.getHouse().getHouseHolder().getId().equals(user.getHouseMember().getId())) //본인이 세대주일 때 무주택직계존속 포함
                    {
                        if (houseMemberRelation.getRelation().getRelation().equals(Relation.부) || houseMemberRelation.getRelation().getRelation().equals(Relation.모) || houseMemberRelation.getRelation().getRelation().equals(Relation.조모) || houseMemberRelation.getRelation().getRelation().equals(Relation.조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조부)) {
                            numberOfFamily = numberOfFamily(user, houseMemberRelation, houseMemberAdditionalInfo, numberOfFamily, parents, bothParentsIsHomelessYnList, aptInfo);
                        } else {
                            if (houseMemberRelation.getRelation().getRelation().equals(Relation.손자녀)&&houseMemberAdditionalInfo.getParentsDeathYn() == null)
                                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_PARENTSDEATHYN);

                            if (houseMemberRelation.getOpponent().getForeignerYn().equals(Yn.n) && ((houseMemberRelation.getRelation().getRelation().equals(Relation.손자녀) && houseMemberAdditionalInfo.getParentsDeathYn().equals(Yn.y)) || houseMemberRelation.getRelation().getRelation().equals(Relation.자녀_일반))) {//부모가 죽은 미혼 손자녀
                                if (houseMemberRelation.getOpponent().getMarriageDate() == null) {//미혼 직계비속이

                                    if (houseMemberAdditionalInfo.getDivorceYn() == null)
                                        throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_DIVORCEYN);
                                    if (houseMemberAdditionalInfo.getDivorceYn().equals(Yn.n)) { //이혼하지 않았고
                                        if (generalPrivateVerificationServiceImpl.calcAmericanAge(houseMemberRelation.getOpponent().getBirthDay()) < 30) {
                                            if (houseMemberAdditionalInfo.getStartDateOfStayOver() == null)
                                                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_STARTDATEOFSTAYOVER);

                                            if (houseMemberAdditionalInfo.getNowStayOverYn() == null)
                                                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_NOWSTAYOVERYN);

                                            if (!(houseMemberAdditionalInfo.getNowStayOverYn().equals(Yn.y) && nowPeriodOfStayOver(houseMemberAdditionalInfo.getStartDateOfStayOver(), aptInfo) > 90)) {//현재 체류여부
                                                numberOfFamily++;
                                            }
                                        } else { //만 30세 이상일 경우
                                            if (houseMemberAdditionalInfo.getStartDateOfStayOver() == null)
                                                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_STARTDATEOFSTAYOVER);

                                            if (houseMemberAdditionalInfo.getEndDateOfStayOver() == null)
                                                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_ENDDATEOFSTAYOVER);

                                            if (!(periodOfStayOver(1, houseMemberAdditionalInfo.getStartDateOfStayOver(), houseMemberAdditionalInfo.getEndDateOfStayOver(), aptInfo) > 90)) {// 체류여부
                                                if (houseMemberAdditionalInfo.getStartDateOfSameResident() == null)
                                                    throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_STARTDATEOFSAMERESIDENT);

                                                if (periodOfYear(houseMemberAdditionalInfo.getStartDateOfSameResident()) >= 1) {
                                                    numberOfFamily++;
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            } else {
                if (user.getSpouseHouse().getHouseHolder() == null) {
                    throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_HOLDER);
                } else {
                    if (user.getSpouseHouse().getHouseHolder().getId().equals(user.getSpouseHouseMember().getId())) { //배우자가 세대주일 때 무주택직계존속 포함
                        if (houseMemberRelation.getRelation().getRelation().equals(Relation.부) || houseMemberRelation.getRelation().getRelation().equals(Relation.모) || houseMemberRelation.getRelation().getRelation().equals(Relation.조모) || houseMemberRelation.getRelation().getRelation().equals(Relation.조부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의부) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조모) || houseMemberRelation.getRelation().getRelation().equals(Relation.배우자의조부)) {
                            numberOfFamily = numberOfFamily(user, houseMemberRelation, houseMemberAdditionalInfo, numberOfFamily, parents, bothParentsIsHomelessYnList, aptInfo);
                        } else {

                            if (houseMemberRelation.getRelation().getRelation().equals(Relation.손자녀)&&houseMemberAdditionalInfo.getParentsDeathYn() == null)
                                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_PARENTSDEATHYN);

                            if (houseMemberRelation.getOpponent().getForeignerYn().equals(Yn.n) && ((houseMemberRelation.getRelation().getRelation().equals(Relation.손자녀) && houseMemberAdditionalInfo.getParentsDeathYn().equals(Yn.y)) || houseMemberRelation.getRelation().getRelation().equals(Relation.자녀_일반))) {//부모가 죽은 미혼 손자녀
                                if (houseMemberRelation.getOpponent().getMarriageDate() == null) {//미혼 직계비속이
                                    if (houseMemberAdditionalInfo.getDivorceYn() == null) //청약통장이 null이면 에러 발생
                                        throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_DIVORCEYN);
                                    if (houseMemberAdditionalInfo.getDivorceYn().equals(Yn.n)) {//이혼하지 않았고
                                        if (generalPrivateVerificationServiceImpl.calcAmericanAge(houseMemberRelation.getOpponent().getBirthDay()) < 30) {
                                            if (houseMemberAdditionalInfo.getStartDateOfStayOver() == null) //청약통장이 null이면 에러 발생
                                                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_STARTDATEOFSTAYOVER);

                                            if (houseMemberAdditionalInfo.getNowStayOverYn() == null)
                                                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_NOWSTAYOVERYN);

                                            if (!(houseMemberAdditionalInfo.getNowStayOverYn().equals(Yn.y) && nowPeriodOfStayOver(houseMemberAdditionalInfo.getStartDateOfStayOver(), aptInfo) > 90)) {//현재 체류여부
                                                numberOfFamily++;
                                            }
                                        } else {//30세 이상일경우
                                            if (houseMemberAdditionalInfo.getStartDateOfStayOver() == null) //청약통장이 null이면 에러 발생
                                                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_STARTDATEOFSTAYOVER);

                                            if (houseMemberAdditionalInfo.getEndDateOfStayOver() == null) //청약통장이 null이면 에러 발생
                                                throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_ENDDATEOFSTAYOVER);

                                            if (!(periodOfStayOver(1, houseMemberAdditionalInfo.getStartDateOfStayOver(), houseMemberAdditionalInfo.getEndDateOfStayOver(), aptInfo) > 90)) {// 체류여부
                                                if (houseMemberAdditionalInfo.getStartDateOfSameResident() == null) //청약통장이 null이면 에러 발생
                                                    throw new CustomException(ErrorCode.NOT_FOUND_HOUSE_MEMBER_ADDITIONAL_INFO_STARTDATEOFSAMERESIDENT);

                                                if (periodOfYear(houseMemberAdditionalInfo.getStartDateOfSameResident()) >= 1) {
                                                    numberOfFamily++;
                                                }
                                            }

                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

        }

        for (int z = 1; z <= 6; z++) {
            if (numberOfFamily < z) {
                return point = z * 5;
            } else point = 35;
        }
        return point;
    }

    public long periodOfStayOver(int standardYear, LocalDate startDateOfStayOver, LocalDate endDateOfStayOver, AptInfo aptInfo) {
        LocalDate announcementDate = aptInfo.getAnnouncementDate();
        LocalDate standardStartDateOfStayOver = announcementDate.minusYears(standardYear);

        if (standardStartDateOfStayOver.isAfter(startDateOfStayOver)) {
            startDateOfStayOver = standardStartDateOfStayOver;
        }
        System.out.println("startDateOfStayOver"+startDateOfStayOver);
        long periodOfStayOver = ChronoUnit.DAYS.between(startDateOfStayOver, endDateOfStayOver);
        System.out.println("periodOfStayOver"+periodOfStayOver);
        return periodOfStayOver;
    }

    public long nowPeriodOfStayOver(LocalDate startDateOfStayOver, AptInfo aptInfo) {
        LocalDate announcementDate = aptInfo.getAnnouncementDate();

        long periodOfStayOver = ChronoUnit.DAYS.between(startDateOfStayOver, announcementDate);
        return periodOfStayOver;
    }


    public int periodOfMonth(LocalDate joinDate) {//개월수 구하는 메소드
        LocalDate now = LocalDate.now();
        Period period = joinDate.until(now);
        return period.getYears() * 12 + period.getMonths();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bankBookVaildYn(User user) {//청약통장 유효여부
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        if (optUserBankbook.isEmpty()) //청약통장이 null이면 에러 발생
            throw new CustomException(ErrorCode.NOT_FOUND_BANKBOOK);
        Optional<Bankbook> stdBankbook = bankbookRepository.findByBankbook(optUserBankbook.get().getBankbook());
        //신청자 통장이 민영주택에서 신청가능한 통장이면 true반환
        if (stdBankbook.get().getPrivateHousingSupplyIsPossible().equals(Yn.y)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer bankbookJoinPeriod(User user) {//통장 가입기간 가점
        int bankbookJoinPeriodGetPoint = 0;
        Optional<UserBankbook> optUserBankbook = userBankbookRepository.findByUser(user);
        if (optUserBankbook.isEmpty())//청약통장이 null이면 에러 발생
            throw new CustomException(ErrorCode.NOT_FOUND_BANKBOOK);
        int joinPeriodOfMonth = periodOfMonth(optUserBankbook.get().getJoinDate());
        int joinPeriodOfYear = periodOfYear(optUserBankbook.get().getJoinDate());
        if (joinPeriodOfMonth < 12) {
            for (int z = 1; z <= 2; z++) {
                if (joinPeriodOfMonth < z * 6) {
                    bankbookJoinPeriodGetPoint = z;
                }
            }
        } else {
            for (int z = 2; z <= 15; z++) {
                if (joinPeriodOfYear < z) {
                    return bankbookJoinPeriodGetPoint = z + 1;
                } else bankbookJoinPeriodGetPoint = 17;
            }
        }
        return bankbookJoinPeriodGetPoint;
    }
}







