package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    // Custom methods here if needed
    Optional<Driver> findByUser_UserId(int userId);
}
