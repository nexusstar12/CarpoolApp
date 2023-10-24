package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    // Basic CRUD methods
    Optional<Profile> findProfileByUserId(@Param("profileId") Integer profileId);

    // Method to fetch profiles based on poolId
    List<Profile> findByPool_PoolId(@Param("poolId") int poolId);

    //on “join pool” so that the profileId associated with the passengerId is sent in the payload
   Profile findProfileByPassengerId(@Param("profileId") int passengerId);
   //on “view crews” so that the profiles associated with crews display

    List<Profile> findByCrew_CrewId(@Param("crewId") int crewId);
}
