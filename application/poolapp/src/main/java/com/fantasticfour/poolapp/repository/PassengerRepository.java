package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Account;
import com.fantasticfour.poolapp.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    @Query("SELECT a FROM Passenger a WHERE a.user.userId = :userId")
    Optional<Passenger> findPassengerByUserId(@Param("userId") Integer userId);
}
