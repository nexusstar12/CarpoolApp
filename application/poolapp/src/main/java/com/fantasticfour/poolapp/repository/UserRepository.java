package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

        //Searches for a users by name matching regex supplied.
        @Query(value = "SELECT * FROM user WHERE name REGEXP ?1", nativeQuery = true)
        List<User> findByNameMatchesRegex(String regex);

        //search for a user by email. should be unique
        Optional<User> findByEmail (String email);

}