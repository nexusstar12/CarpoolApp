package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.domain.Account;
import com.fantasticfour.poolapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account addAccount (Account account) {
        return accountRepository.save(account);
    }

}
