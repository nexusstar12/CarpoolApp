package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Pool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoolRepository extends JpaRepository<Pool, Integer> {

    @Query(value = "SELECT * FROM pool WHERE start_city REGEXP ?1", nativeQuery = true)
    List<Pool> findByCityMatchesRegex(String regex);

    @Query(value = "SELECT *, ?2 * 2 * ASIN(SQRT(POWER(SIN((?1 - abs(start_latitude)) * pi() / 180 / 2), 2) + " +
            "COS(?1 * pi() / 180) * COS(abs(start_latitude) * pi() / 180) * POWER(SIN((?3 - start_longitude) * pi() / 180 / 2), 2) )) " +
            "AS distance FROM pool HAVING distance < ?4 ORDER BY distance", nativeQuery = true)
    List<Pool> findWithinDistanceUsingStartZip(Double latitude, Double radius, Double longitude, Double distance);

    @Query(value = "SELECT *, ?2 * 2 * ASIN(SQRT(POWER(SIN((?1 - abs(end_latitude)) * pi() / 180 / 2), 2) + " +
            "COS(?1 * pi() / 180) * COS(abs(end_latitude) * pi() / 180) * POWER(SIN((?3 - end_longitude) * pi() / 180 / 2), 2) )) " +
            "AS distance FROM pool HAVING distance < ?4 ORDER BY distance", nativeQuery = true)
    List<Pool> findWithinDistanceUsingEndZip(Double latitude, Double radius, Double longitude, Double distance);
}
