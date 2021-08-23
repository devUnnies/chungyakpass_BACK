package com.hanium.chungyakpassback.dto;

import com.hanium.chungyakpassback.entity.enumtype.Bank;
import com.hanium.chungyakpassback.entity.enumtype.BankbookType;
import com.hanium.chungyakpassback.entity.enumtype.Yn;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.input.UserBankbook;
import lombok.Builder;


import java.time.LocalDate;

public class UserBankbookDto {
    private User user;
    private Bank bank;
    private BankbookType bankbookType;
    private LocalDate joinDate;
    private int deposit;
    private int paymentsCount;
    private Yn validYn;

    public UserBankbook toEntity(){
        return UserBankbook.builder()
        .user(user)
        .bank(bank)
        .bankbookType(bankbookType)
        .joinDate(joinDate)
        .deposit(deposit)
        .paymentsCount(paymentsCount)
        .validYn(validYn)
        .build();
    }

    @Builder
    public UserBankbookDto(User user, Bank bank, BankbookType bankbookType, LocalDate joinDate, int deposit, int paymentsCount, Yn validYn) {
        this.user = user;
        this.bank = bank;
        this.bankbookType = bankbookType;
        this.joinDate = joinDate;
        this.deposit = deposit;
        this.paymentsCount = paymentsCount;
        this.validYn = validYn;
    }

}
