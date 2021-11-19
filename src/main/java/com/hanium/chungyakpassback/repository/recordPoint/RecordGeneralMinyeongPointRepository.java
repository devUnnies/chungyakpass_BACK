package com.hanium.chungyakpassback.repository.recordPoint;

import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.recordPoint.RecordGeneralMinyeongPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordGeneralMinyeongPointRepository extends JpaRepository<RecordGeneralMinyeongPoint, Long> {

    List<RecordGeneralMinyeongPoint> findAllByUser(User user);
}

