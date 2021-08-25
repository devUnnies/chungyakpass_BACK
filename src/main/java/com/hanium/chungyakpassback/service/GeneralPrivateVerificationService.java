package com.hanium.chungyakpassback.service;

import com.hanium.chungyakpassback.entity.enumtype.Yn;

public interface GeneralPrivateVerificationService {
    boolean accountStatus();

    int americanAgeCount();

    Yn householderYn();

    boolean winningHistory();

    boolean restrictedAreaYn();

    Integer hasHouseYn();

    boolean specialNote();

    boolean surroundingArea();

    boolean depositAmount();

    boolean termsOfPolicy();

    // public boolean 규제지역로직();

}
