package com.hanium.chungyakpassback.repository.input;

import com.hanium.chungyakpassback.entity.input.House;
import com.hanium.chungyakpassback.entity.input.HouseMember;
import com.hanium.chungyakpassback.entity.input.User;
import com.hanium.chungyakpassback.enumtype.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HouseMemberRepository extends JpaRepository<HouseMember, Long> {
    List<HouseMember> findAllByHouse(House house);
}
