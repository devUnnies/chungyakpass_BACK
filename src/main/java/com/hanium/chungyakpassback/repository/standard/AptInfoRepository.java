package com.hanium.chungyakpassback.repository.standard;

import com.hanium.chungyakpassback.entity.standard.AptInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AptInfoRepository extends JpaRepository<AptInfo, Integer> {
}
