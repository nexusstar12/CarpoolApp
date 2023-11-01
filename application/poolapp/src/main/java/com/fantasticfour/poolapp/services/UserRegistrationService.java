package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.domain.Driver;
import com.fantasticfour.poolapp.domain.Passenger;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationService.class);

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
        try {
            log.info("Registering user with email: {}", user.getEmail());

            // Save the user to the database
            User savedUser = userRepository.save(user);
            log.info("User saved with ID: {}", savedUser.getUserId());

            // Create and save the passenger profile
            Passenger passenger = new Passenger();
            passenger.setUser(savedUser);
            passengerService.addPassenger(passenger);
            log.info("Passenger profile added for user ID: {}", savedUser.getUserId());

            // If the user is also a driver, create and save the driver profile
            if (isDriver) {
                Driver driver = new Driver();
                driver.setUser(savedUser);
                // Driver-specific properties here
                driverService.addDriver(driver);
                log.info("Driver profile added for user ID: {}", savedUser.getUserId());
            }

            // Create a unified profile entry linking to the User's ID
            Profile profile = new Profile();
            profile.setUserId(savedUser);
            profile.setUserType(isDriver ? "driver" : "passenger");
            profileService.addProfile(profile);
            log.info("Unified profile added for user ID: {}", savedUser.getUserId());

            log.info("User registration completed for email: {}", user.getEmail());

            return savedUser;

        } catch (Exception e) {
            log.error("Error during user registration for email: {}", user.getEmail(), e);
            throw e;  // re-throwing to ensure the transactional annotation will rollback the transaction
        }
    }
}
