package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Integer> {

    Crew findByPool_PoolId(@Param("poolId")int poolId);
}
