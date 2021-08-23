package com.hanium.chungyakpassback.entity.input;

import com.hanium.chungyakpassback.entity.enumtype.Bank;
import com.hanium.chungyakpassback.entity.enumtype.Yn;
import com.hanium.chungyakpassback.entity.enumtype.BankbookType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inp_user_bankbook")
public class UserBankbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_bankbook_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private Bank bank;

    @Column
    @Enumerated(EnumType.STRING)
    private BankbookType bankbookType;

    @Column
    private LocalDate joinDate;

    @Column
    private int deposit;

    @Column
    private int paymentsCount;

    @Column
    @Enumerated(EnumType.STRING)
    private Yn validYn;

    @Builder
    public UserBankbook(User user, Bank bank, BankbookType bankbookType, LocalDate joinDate, int deposit, int paymentsCount, Yn validYn) {
        this.user = user;
        this.bank = bank;
        this.bankbookType = bankbookType;
        this.joinDate = joinDate;
        this.deposit = deposit;
        this.paymentsCount = paymentsCount;
        this.validYn = validYn;
    }
}
