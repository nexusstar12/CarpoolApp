package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}