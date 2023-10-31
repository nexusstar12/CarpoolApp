package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.domain.User;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    // Basic CRUD methods

    // Method to fetch profiles based on poolId
    @Query(value  = "SELECT member_1_id,member_2_id, member_3_id FROM pool WHERE pool_id = ?1;", nativeQuery = true)
    List<Profile> findByPool_PoolId(@Param("poolId") int poolId);
    @Query(value = "SELECT member1_id, member2_id, member3_id FROM crew WHERE crewId = ?1;", nativeQuery = true)
    List<Profile> findByCrew_CrewId(@Param("crewId") int crewId);

    Profile findByPassenger_PassengerId(@Param("passengerId")int passengerId);

    Profile findByUserId ( User user);

   Optional<Profile> findProfileByProfileId(int profileId);


}
