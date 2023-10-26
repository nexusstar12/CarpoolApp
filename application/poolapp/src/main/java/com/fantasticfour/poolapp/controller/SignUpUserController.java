package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.*;
import com.fantasticfour.poolapp.repository.UserRepository;
import com.fantasticfour.poolapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/signup")
public class SignUpUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverService driverService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Autowired
    private ProfileService profileService;*/

//    @PostMapping("/")
//    public ResponseEntity<User> addUser (@RequestBody User user) {
//        User newUser = userServce.addUser(user);
//
//        //TODO: ADD VALIDATION USER IS ADDED
//        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//    }

    @PostMapping({"", "/"})
    public ResponseEntity<Map<String, Object>> addUser (@RequestBody Map<String, String> jsonMap) {
        jsonMap.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));



        //create new user
        String firstName = jsonMap.get("firstName");
        String lastName = jsonMap.get("lastName");
        String name = firstName + " " + lastName;


        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(jsonMap.get("email"));
        newUser.setName(name);

        Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());
        if (existingUser.isPresent()) {
            // handle duplicate email
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "duplicate user email");
            return new ResponseEntity<>(responseMap, HttpStatus.CONFLICT);
        }

        //create new hashed password
        Password newPassword = new Password();
        newPassword.setPassword(passwordEncoder.encode(jsonMap.get("password")));

        //create new account that links user and password
        Account newAccount = new Account();
        newAccount.setUser(newUser);
        newAccount.setPassword(newPassword);

        //add users and password to DB table
        //and add users and password to the account table
       User addedUser = userService.addUser(newUser);
       Password addPassword = passwordService.addPassword(newPassword);
       Account addAccount = accountService.addAccount(newAccount);

       //build custom json response body
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", addedUser);
        responseMap.put("password", addPassword);
        responseMap.put("account", addAccount);

        //allow user to assign the driver role to their existing account & profile
        if ("driver".equals(jsonMap.get("role"))){
            Driver driver = new Driver();
            driver.setUser(addedUser);
            driver.setFastrakVerification(Boolean.parseBoolean(jsonMap.get("fastrakVerification")));
            driver.setDriversLicense(jsonMap.get("driversLicense"));
            driverService.addDriver(driver);

            Profile profile = new Profile();
            profile.setUserType("driver");
            //profileService.addProfile(profile);
            responseMap.put("driver", driver);
        } else if ("passenger".equals(jsonMap.get("role"))) {
            Passenger passenger = new Passenger();
            passenger.setUser(addedUser);
            passengerService.addPassenger(passenger);

            Profile profile = new Profile();
            profile.setUserType("passenger");
            //profileService.addProfile(profile);
            responseMap.put("passenger", passenger);
        } else {
            // Unknown roles
        }

        //returns a map of added user, account, and password
        //TODO: ADD VALIDATION USER, account, and password are ADDED
        return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
    }
}
