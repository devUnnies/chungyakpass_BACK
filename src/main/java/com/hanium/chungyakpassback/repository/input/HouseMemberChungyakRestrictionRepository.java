package com.hanium.chungyakpassback.repository.input;

import com.hanium.chungyakpassback.entity.input.HouseMember;
import com.hanium.chungyakpassback.entity.input.HouseMemberChungyak;
import com.hanium.chungyakpassback.entity.input.HouseMemberChungyakRestriction;
import com.hanium.chungyakpassback.entity.input.HouseMemberProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseMemberChungyakRestrictionRepository extends JpaRepository<HouseMemberChungyakRestriction, Long> {
    void deleteByHouseMemberChungyak(HouseMemberChungyak houseMemberChungyak);

    HouseMemberChungyakRestriction findByHouseMemberChungyak(HouseMemberChungyak houseMemberChungyak);

    List<HouseMemberChungyakRestriction> findAllByHouseMemberChungyak(HouseMemberChungyak houseMemberChungyak);
}
