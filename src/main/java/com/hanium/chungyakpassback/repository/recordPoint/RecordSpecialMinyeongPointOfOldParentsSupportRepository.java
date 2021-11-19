package com.hanium.chungyakpassback.repository.recordPoint;

import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.entity.recordPoint.RecordSpecialMinyeongPointOfOldParentsSupport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordSpecialMinyeongPointOfOldParentsSupportRepository extends JpaRepository<RecordSpecialMinyeongPointOfOldParentsSupport, Long> {
    List<RecordSpecialMinyeongPointOfOldParentsSupport> findAllByUser(User user);
}