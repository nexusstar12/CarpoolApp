package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Integer> {

}
