package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Integer> {

    Crew findByPool_PoolId(@Param("poolId")int poolId);

    @Query(value = "SELECT * FROM crew WHERE member1_id = ?1 OR member2_id = ?1 OR member3_id = ?1", nativeQuery = true)
    List<Optional<Crew>> findByUser_UserId(@Param("user_id")int userId);
}
