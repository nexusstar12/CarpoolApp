package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.services.AccountService;
import com.fantasticfour.poolapp.services.PasswordService;
import com.fantasticfour.poolapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/signin")
public class SignInController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private AccountService accountService;

//    @GetMapping("/")
//    public ResponseEntity<Map<String, Object>> signInUser (@RequestBody Map<String, String> jsonMap) {
//
//    }
}
