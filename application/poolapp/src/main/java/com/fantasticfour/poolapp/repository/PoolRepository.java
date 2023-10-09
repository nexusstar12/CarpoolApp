package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoolRepository extends JpaRepository<Pool, Integer> {

    @Query(value = "SELECT * FROM pool WHERE start_city REGEXP ?1", nativeQuery = true)
    List<Pool> findByCityMatchesRegex(String regex);
}
