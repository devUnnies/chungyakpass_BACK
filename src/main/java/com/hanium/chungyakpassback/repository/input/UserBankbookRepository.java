package com.hanium.chungyakpassback.repository.input;

import com.hanium.chungyakpassback.entity.enumtype.BankbookType;
import com.hanium.chungyakpassback.entity.input.UserBankbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBankbookRepository extends JpaRepository<UserBankbook, Long> {
    Optional<UserBankbook> findOneByBankbookType(BankbookType BankbookType);
        Optional<UserBankbook> findByUser_Id(Long userId);
}
