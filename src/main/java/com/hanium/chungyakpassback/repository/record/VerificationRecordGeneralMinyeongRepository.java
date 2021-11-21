package com.hanium.chungyakpassback.repository.record;

import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.record.GeneralMinyeongVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationRecordGeneralMinyeongRepository extends JpaRepository<GeneralMinyeongVerification, Long> {

    List<GeneralMinyeongVerification> findAllByUser(User user);
}
