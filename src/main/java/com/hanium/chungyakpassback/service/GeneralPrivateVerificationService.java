package com.hanium.chungyakpassback.service;

import com.hanium.chungyakpassback.entity.enumtype.Yn;

public interface GeneralPrivateVerificationService {
    public boolean 청약통장충족여부();

    public int 만나이계산();

    public Yn 세대주여부();

    public boolean 전세대원5년이내당첨이력존재여부();

    public boolean 규제지역여부();

    public Integer 주택소유();

    public boolean 특이사항충족();


    public boolean 예치금액충족();

    public boolean 가입기간충족();


    // public boolean 규제지역로직();


    //  public boolean 전세대원5년이내당첨이력존재여부();
}
