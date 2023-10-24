package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    // Basic CRUD methods are provided out-of-the-box
}
