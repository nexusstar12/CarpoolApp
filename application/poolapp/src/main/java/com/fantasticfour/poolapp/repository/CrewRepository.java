package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Crew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewRepository extends JpaRepository<Crew, Integer> {
    // This interface now has methods like save, findAll, findById, etc. for Crew entity
}
