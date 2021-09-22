package com.hanium.chungyakpassback.repository.standard;

import com.hanium.chungyakpassback.entity.standard.AddressLevel1;
import com.hanium.chungyakpassback.entity.standard.AddressLevel2;
import com.hanium.chungyakpassback.entity.standard.PriorityJoinPeriod;
import com.hanium.chungyakpassback.enumtype.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrioritySubscriptionPeriodRepository extends JpaRepository<PriorityJoinPeriod, Long> {
    List<PriorityJoinPeriod> findAllBySupply(Supply supply);

}
