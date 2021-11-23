package com.hanium.chungyakpassback.repository.verification;

import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.verification.VerificationRecordSpecialMinyeongMultiChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationRecordSpecialMinyeongMultiChildRepository extends JpaRepository<VerificationRecordSpecialMinyeongMultiChild, Long> {

    List<VerificationRecordSpecialMinyeongMultiChild> findAllByUser(User user);

}