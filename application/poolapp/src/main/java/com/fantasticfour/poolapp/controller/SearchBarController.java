package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchBarController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/api/searchbar")
    public List<User> searchBar (@RequestParam String filter, @RequestParam String value) {
        //http://localhost:8080/api/searchbar?filter=[columnNameInDB]&value=[stringTobesearched]
        String regex = "^" + value;

        //returns a list of users
        return userRepository.findByNameMatchesRegex(regex);
    }
}
