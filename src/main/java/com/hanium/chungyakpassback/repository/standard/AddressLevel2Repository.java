package com.hanium.chungyakpassback.repository.standard;

import com.hanium.chungyakpassback.entity.standard.AddressLevel1;
import com.hanium.chungyakpassback.entity.standard.AddressLevel2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressLevel2Repository extends JpaRepository<AddressLevel2, Long> {
    Optional<AddressLevel2> findByAddressLevel1AndAddressLevel2(AddressLevel1 addressLevel1, com.hanium.chungyakpassback.enumtype.AddressLevel2 addressLevel2);
    Optional<AddressLevel2> findByAddressLevel2(com.hanium.chungyakpassback.enumtype.AddressLevel2 addressLevel2);
    List<AddressLevel2> findAllByAddressLevel1(Optional<AddressLevel1> addressLevel1);
}
