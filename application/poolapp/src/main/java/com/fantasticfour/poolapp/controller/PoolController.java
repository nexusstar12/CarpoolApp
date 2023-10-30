package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.CustomResponse.*;
import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.*;
import com.fantasticfour.poolapp.services.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pool")
public class PoolController {

    @Autowired
    private PoolService poolService;

    @Autowired
    private PoolRepository poolRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @PostMapping("/join/{poolId}")
    public ResponseEntity<?> joinPool(@PathVariable int poolId, @RequestBody int profileId){
        poolService.addProfileToPool(poolId, profileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //might need to change parameter based on postInput
    @PostMapping("/create/{id}")
    public ResponseEntity<Pool> markVisibility(@RequestParam String status, @PathVariable int id) {
        if(status.equals("public")){
            poolService.setProfileToPublic(true, id);
        }
        else if(status.equals("private")){
            poolService.setProfileToPrivate(false, id);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping ("/getpools/{userId}")
    public ResponseEntity<?> getPools (@PathVariable int userId) {
        //response object
        PoolsByIdResponse poolsByIdResponse = new PoolsByIdResponse();
//        CustomPoolResponse customPoolResponse = new CustomPoolResponse();

        //get user entity
        Optional<User> optionalUser = userRepository.findById(userId);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        else {
            //no user found by user id
            System.out.println("/api/pool/getpools/{id} no user found with id");
            return new ResponseEntity<>(null , HttpStatus.OK);
        }

        //get profile id using user id
        Profile profile = profileRepository.findByUserId(user);
        int profileId = profile.getProfileId();

        //get pools where profile id  is a member or creator of a pool
        List<Pool> pools = poolRepository.findByProfileId(profileId).stream()
                                         .filter(Optional::isPresent)
                                         .map(Optional::get)
                                         .toList();

        //mypools- user created pools occuring today or in the future.
        LocalDateTime currentTime = LocalDateTime.now();
        List<Pool> myPools = pools.stream()
                                  .filter(pool -> pool.getCreator().getProfileId() == profileId)
                                  .filter(pool -> pool.getStartTime().isAfter(currentTime))
                                  .toList();

//        poolsByIdResponse.setMyPools(customPoolResponse.buildPoolResponseList(pools));
        //create custom response for my pools
        for (Pool pool : pools) {
            PoolResponse poolResponse = new PoolResponse();

            poolResponse.setPoolId(pool.getPoolId());
            poolResponse.setStartLocation(pool.getStartLocation());
            poolResponse.setEndLocation(pool.getEndLocation());
            poolResponse.setStartTime(pool.getStartTime());
            poolResponse.setDescription(pool.getDescription());

            //get the driver from profile -> userid -> driver
            CustomDriver customDriver = new CustomDriver();

            int creatorProfileId = pool.getCreator().getProfileId();
            //add driver to response
            profileRepository.findProfileByProfileId(creatorProfileId)
                    .ifPresent(driverProfile -> {

                        User userDriver = driverProfile.getUserId();

                        driverRepository.findByUser_UserId(userDriver.getUserId())
                                .ifPresent(driver -> {
                                    customDriver.setDriverId(driver.getDriverId());
                                    customDriver.setName(userDriver.getName());
                                    poolResponse.setDriver(customDriver);
                                });

                    } );

            //get passengers

            List<Profile> memberList = new ArrayList<>();
             memberList.add(pool.getMember1());
            memberList.add(pool.getMember2());
            memberList.add(pool.getMember3());

            for (Profile member: memberList) {
                CustomPassenger customPassenger = new CustomPassenger();
                if (member != null) {
                      User userPassenger = member.getUserId();
                      passengerRepository.findPassengerByUserId(userPassenger.getUserId())
                              .ifPresent(passenger -> {
                                  customPassenger.setPassengerId(passenger.getPassengerId());
                                  customPassenger.setName(userPassenger.getName());
                                  poolResponse.addPassenger(customPassenger);
                              });
                }
            }

            poolsByIdResponse.addMyPoolsIndex(poolResponse);

            //for testing make custom class.
            poolsByIdResponse.addAvailablePoolsIndex(poolResponse);
            poolsByIdResponse.addPastPoolsIndex(poolResponse);

        }//End For Each

        //available pools -  private pools they are a member of
        List<Pool> availablePools = poolRepository.findByProfileId(profileId).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Pool::isPublicOrPrivate)
                .toList();

        //past pools
        List<Pool> pastPools = pools.stream()
                .filter(pool -> pool.getStartTime().isBefore(currentTime))
                .toList();


       return new ResponseEntity<>(poolsByIdResponse, HttpStatus.OK);
    }

}
