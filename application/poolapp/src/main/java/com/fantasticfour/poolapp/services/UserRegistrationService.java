package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.domain.Driver;
import com.fantasticfour.poolapp.domain.Passenger;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private ProfileService profileService;

    @Transactional
    public User registerUser(User user, boolean isDriver) {
        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Create and save the passenger profile
        Passenger passenger = new Passenger();
        passenger.setUser(savedUser);
        passengerService.addPassenger(passenger);

        // If the user is also a driver, create and save the driver profile
        if (isDriver) {
            Driver driver = new Driver();
            driver.setUser(savedUser);
            // Driver-specific properties here
            driverService.addDriver(driver);
        }

        // Create a unified profile entry linking to the User's ID
        Profile profile = new Profile();
        profile.setUserId(savedUser);
        profile.setUserType(isDriver ? "driver" : "passenger"); // Adjust this logic as needed
        // Any logic that needs to connect specific profile entries with driver/passenger IDs here

        profileService.addProfile(profile);

        return savedUser;
    }
}

