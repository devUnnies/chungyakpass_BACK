package com.hanium.chungyakpassback.repository.standard;

import com.hanium.chungyakpassback.entity.apt.AptInfo;
import com.hanium.chungyakpassback.entity.enumtype.DepositAmountRegionClassification;
import com.hanium.chungyakpassback.entity.enumtype.Yn;
import com.hanium.chungyakpassback.entity.standard.AreaLevel1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaLevel1Repository extends JpaRepository<AreaLevel1, Long> {
    List<AreaLevel1> findAllByMetropolitanArea(Yn metropolitanArea);
    List<AreaLevel1> findAllByDepositAmountArea(DepositAmountRegionClassification depositAmountArea);
    List<AreaLevel1> findAllByNearbyArea( int nearbyArea);
    //AreaLevel1 findByArea_level1;

}
