package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //adds a  new user to the user table of the database
    public User addUser(User user) {
        return userRepository.save(user);
    }

}
