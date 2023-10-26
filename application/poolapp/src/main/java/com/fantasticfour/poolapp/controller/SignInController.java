package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.Account;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.AccountRepository;
import com.fantasticfour.poolapp.repository.DriverRepository;
import com.fantasticfour.poolapp.repository.PasswordRepository;
import com.fantasticfour.poolapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/signin")
public class SignInController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/")
    public ResponseEntity<List<Object>> signInUser (@RequestBody Map<String, String> jsonMap) {

        jsonMap.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
        List<Object> responseList = new ArrayList<>();

        //Fetch user by email
       Optional<User> optionalUser = userRepository.findByEmail(jsonMap.get("email"));

       //check if user exists by email
       User user;
       if (optionalUser.isPresent()){
           user = optionalUser.get();
       }else {
           responseList.add("Incorrect username or password");
           return new ResponseEntity<>(responseList, HttpStatus.NOT_FOUND);
       }

       Optional<Account> optionalAccount = accountRepository.findAccountByUserId(user.getUserId());

       Account account;
       if (optionalAccount.isPresent()) {
           account = optionalAccount.get();
       }else {
           responseList.add("Could not find account");
           return new ResponseEntity<>(responseList, HttpStatus.NOT_FOUND);
       }

       //check if password matches
        String inputPassword = jsonMap.get("password");
        String storedPassword = account.getPassword().getPassword(); //hashed password
        boolean isPasswordMatching = passwordEncoder.matches(inputPassword, storedPassword);

       if (isPasswordMatching) {
           responseList.add(user);

           // Check if user is a driver
           boolean isDriver = driverRepository.findByUser_UserId(user.getUserId()).isPresent();
           responseList.add("Is Driver: " + isDriver);

           return new ResponseEntity<>(responseList, HttpStatus.FOUND);
        }

        responseList.add("Incorrect username or password");
       return new ResponseEntity<>(responseList, HttpStatus.NOT_FOUND);
    }
}
