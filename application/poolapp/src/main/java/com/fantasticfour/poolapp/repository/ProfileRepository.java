package com.fantasticfour.poolapp.repository;


import com.fantasticfour.poolapp.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    // Basic CRUD methods are provided out-of-the-box
    Optional<Profile> findProfileByUserId(@Param("userId") Integer userId);
}
