package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.domain.Password;
import com.fantasticfour.poolapp.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    @Autowired
    private PasswordRepository passwordRepository;

    //add password to password table
    public Password addPassword (Password password) {
        return passwordRepository.save(password);
    }
}
