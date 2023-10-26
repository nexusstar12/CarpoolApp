package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.Driver;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.DriverRepository;
import com.fantasticfour.poolapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/driver")
public class DriverControllerTest {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping({"", "/"})
    public ResponseEntity<Driver> addDriver(@RequestBody Driver driver){
        // Fetch the associated User first
        Optional<User> userOpt = userRepository.findById((long) 1.0); // Better: Get this ID from the driver object or client request, not hardcoded

        if (!userOpt.isPresent()){
            System.out.println("User doesn't exist");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Return a 400 Bad Request if the user doesn't exist
        }

        // Set the User to the Driver
        driver.setUser(userOpt.get());

        // Save the Driver with its associated User
        Driver newDriver = driverRepository.save(driver);

        return new ResponseEntity<Driver>(newDriver, HttpStatus.ACCEPTED);
    }
}
