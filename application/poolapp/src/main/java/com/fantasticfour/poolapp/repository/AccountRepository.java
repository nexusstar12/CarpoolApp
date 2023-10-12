package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account,Integer> {

    @Query("SELECT a FROM Account a WHERE a.user.userId = :userId")
    Optional<Account> findAccountByUserId(@Param("userId") Integer userId);
}
