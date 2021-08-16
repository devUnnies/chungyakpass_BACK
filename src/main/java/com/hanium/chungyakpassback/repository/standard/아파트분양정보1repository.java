package com.hanium.chungyakpassback.repository.standard;

import com.hanium.chungyakpassback.domain.standard.아파트분양정보;
import com.hanium.chungyakpassback.domain.standard.아파트분양정보1;
import com.hanium.chungyakpassback.dto.아파트분양정보1Dto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public interface 아파트분양정보1repository extends JpaRepository<아파트분양정보1, Integer> {
         //Optional<아파트분양정보1>findTop1By공고번호(Integer 공고번호);
            //List<아파트분양정보1> findTop1By공고번호(Integer 공고번호);
}

