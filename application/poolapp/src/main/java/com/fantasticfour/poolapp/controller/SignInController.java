package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.CustomResponse.CustomSignInResponse;
import com.fantasticfour.poolapp.CustomResponse.JwtResponse;
import com.fantasticfour.poolapp.config.JwtHelper;
import com.fantasticfour.poolapp.domain.Account;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.*;
import com.fantasticfour.poolapp.services.CustomUserDetails;
import com.fantasticfour.poolapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;


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

            //jwt logic Start
            doAuthenticate(user.getEmail(), inputPassword);
            UserDetails userDetails=userDetailsService.loadUserByUsername(user.getEmail());
//            String token = jwtHelper.generateToken(userDetails);
//            customSignInResponse.setJwtToken(token);
            // Cast to CustomUserDetails
            if (userDetails instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
                String token = jwtHelper.generateToken(customUserDetails);
                customSignInResponse.setJwtToken(token);
            } else {
                // Handle the case where userDetails is not an instance of CustomUserDetails
//                 String token = jwtHelper.generateToken(userDetails);
//                 customSignInResponse.setJwtToken(token);
            }
            //JwtResponse response = JwtResponse.builder().jwtToken(token).userName(userDetails.getUsername()).build();
            //jwt logic end

            return new ResponseEntity<>(customSignInResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
    }

    private void doAuthenticate(String username,String password) {
        System.out.println(password);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);

        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or password");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
