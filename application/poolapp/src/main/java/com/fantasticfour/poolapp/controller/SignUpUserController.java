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
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @PostMapping({"", "/"})
    public ResponseEntity<Map<String, Object>> addUser (@RequestBody Map<String, String> jsonMap) {
//        jsonMap.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        //build custom json response body
        Map<String, Object> responseMap = new HashMap<>();

        //create new user
        String firstName = jsonMap.get("firstName");
        String lastName = jsonMap.get("lastName");
        String name = firstName + " " + lastName;
        String phoneNumber = jsonMap.get("phoneNumber");


        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(jsonMap.get("email"));
        newUser.setName(name);
        newUser.setPhoneNumber(phoneNumber);

        if ("driver".equals(jsonMap.get("role"))) { //add user as driver
            newUser.setIsDriver(true);
        }

        //checking for duplicate email
        Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());
        if (existingUser.isPresent()) {
            // handle duplicate email
            Map<String, Object> response = new HashMap<>();
            response.put("message", "duplicate user email");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
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

        // Register the user and get the updated information
        //boolean isDriver = "driver".equals(jsonMap.get("role"));
        //User registeredUser = userRegistrationService.registerUser(addedUser, isDriver);

        //saving password db
        Password addedPassword = passwordService.addPassword(newPassword);

//        newAccount.setUser(registeredUser);
//        newAccount.setPassword(addedPassword);

        // Add account to DB
        Account addedAccount = accountService.addAccount(newAccount);

        //assign all users who sign up the passenger role.
        Passenger passenger = new Passenger();
//        passenger.setUser(registeredUser);
        passenger.setUser(newUser); //added after next today
        passenger = passengerService.addPassenger(passenger);

        //create user profile, and associate passenger id.
        Profile profile = new Profile();
//        profile.setUserId(registeredUser);//associate user with passenger
        profile.setUserId(newUser);

        //allow user to assign the driver role to their existing account & profile
        Driver driver = null;
        if ("driver".equals(jsonMap.get("role"))) {
            driver = new Driver();
//            driver.setUser(registeredUser);
            driver.setUser(newUser);
            //unfamiliar syntax
            driver.setFastrakVerification(Boolean.parseBoolean(jsonMap.get("fastrakVerification")));
            driver.setDriversLicense(jsonMap.get("driversLicense"));
            driver = driverService.addDriver(driver);
        }

        // Set the passenger and/or driver ID in the profile as necessary
        if (passenger != null) {
            profile.setPassenger(passenger);
        }
        if (driver != null) {
            profile.setDriver(driver);
        }

        // Now, save the profile
        profile = profileService.addProfile(profile);

//        responseMap.put("user", registeredUser);
        responseMap.put("user", newUser);
        responseMap.put("password", addedPassword);
        responseMap.put("account", addedAccount);
        responseMap.put("passenger", passenger);
        responseMap.put("profile", profile);

        //returns a map of added user, account, and password
        //TODO: ADD VALIDATION USER, account, and password are ADDED
        return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
    }
}
