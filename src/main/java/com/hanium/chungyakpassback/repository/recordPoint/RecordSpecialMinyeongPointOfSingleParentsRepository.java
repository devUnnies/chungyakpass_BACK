package com.hanium.chungyakpassback.repository.recordPoint;

import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfSingleParents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordSpecialMinyeongPointOfSingleParentsRepository extends JpaRepository<RecordSpecialMinyeongPointOfSingleParents, Long> {
    List<RecordSpecialMinyeongPointOfSingleParents> findAllByUser(User user);
}
