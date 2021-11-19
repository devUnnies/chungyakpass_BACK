package com.hanium.chungyakpassback.repository.recordPoint;

import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfMultiChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordSpecialMinyeongPointOfMultiChildRepository extends JpaRepository<RecordSpecialMinyeongPointOfMultiChild, Long> {

    List<RecordSpecialMinyeongPointOfMultiChild> findAllByUser(User user);
}
