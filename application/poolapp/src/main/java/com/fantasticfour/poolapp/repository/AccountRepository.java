package com.fantasticfour.poolapp.repository;

import com.fantasticfour.poolapp.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface AccountRepository extends JpaRepository<Account,Integer> {
}
