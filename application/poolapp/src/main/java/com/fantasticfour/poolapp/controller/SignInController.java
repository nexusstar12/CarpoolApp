package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.CustomResponse.CustomSignInResponse;
import com.fantasticfour.poolapp.domain.Account;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.*;
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

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping({"", "/"})
    public ResponseEntity<?> signInUser (@RequestBody Map<String, String> jsonMap) {

        jsonMap.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        //Fetch user by email
        Optional<User> optionalUser = userRepository.findByEmail(jsonMap.get("email"));

        //check if user exists by email
        User user;
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        } else {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }

        Optional<Account> optionalAccount = accountRepository.findAccountByUserId(user.getUserId());

        //Check if account exists for the user
        Account account;
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
        } else {
            return new ResponseEntity<>("Could not find account", HttpStatus.UNAUTHORIZED);
        }

        //check if password matches
        String inputPassword = jsonMap.get("password");
        String storedPassword = account.getPassword().getPassword(); //hashed password
        boolean isPasswordMatching = passwordEncoder.matches(inputPassword, storedPassword);

        if (isPasswordMatching) {
            // Check if user is a driver
            boolean isDriver = driverRepository.findByUser_UserId(user.getUserId()).isPresent();
            user.setIsDriver(isDriver);

           Profile profile= profileRepository.findByUserId(user);


            CustomSignInResponse customSignInResponse = new CustomSignInResponse();
           customSignInResponse.setUserId(user.getUserId());
           customSignInResponse.setEmail(user.getEmail());
           customSignInResponse.setFirstName(user.getFirstName());
           customSignInResponse.setLastName(user.getLastName());
           customSignInResponse.setName(user.getName());
           customSignInResponse.setPhoneNumber(user.getPhoneNumber());
           customSignInResponse.setDriver(user.getIsDriver());
           if (profile != null) {
               customSignInResponse.setProfileId(profile.getProfileId());
           }


            return new ResponseEntity<>(customSignInResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
    }
}
