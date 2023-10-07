package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.services.UserServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
public class SignUpUserController {

    @Autowired
    private UserServce userServce;

    @PostMapping("/")
    public ResponseEntity<User> addUser (@RequestBody User user) {
        User newUser = userServce.addUser(user);

        //TODO: ADD VALIDATION USER IS ADDED
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
