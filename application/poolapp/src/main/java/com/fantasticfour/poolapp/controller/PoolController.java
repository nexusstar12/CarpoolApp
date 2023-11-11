package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.CustomResponse.*;
import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.*;
import com.fantasticfour.poolapp.services.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private CustomPoolResponse customPoolResponse;

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

        System.out.println(Arrays.toString(myPools.toArray()));

        //build custom response
        poolsByIdResponse.setMyPools(customPoolResponse.buildPoolResponseList(myPools));

        //available pools -  private pools they are a member of
        List<Pool> availablePools = poolRepository.findByProfileId(profileId).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(pool -> pool.getStartTime().isAfter(currentTime)) //.filter(Pool::isPrivacy)
                .toList();

        //add to custom response
        poolsByIdResponse.setAvailablePools(customPoolResponse.buildPoolResponseList(availablePools));

        //past pools
        List<Pool> pastPools = pools.stream()
                .filter(pool -> pool.getStartTime().isBefore(currentTime))
                .toList();

        //add to custom response
        poolsByIdResponse.setPastPools(customPoolResponse.buildPoolResponseList(pastPools));


       return new ResponseEntity<>(poolsByIdResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deletemember")
    public ResponseEntity<?> deletePoolMember (@RequestBody Map<String, Object> jsonMap) {
        //take in boyd poolId as int and profileId as int
        if (jsonMap.isEmpty()){return new ResponseEntity<>("request body is empty", HttpStatus.OK);}

        int poolId = (int)jsonMap.get("poolId");
        int profileId = (int)jsonMap.get("profileId");

       //delete member by profile id from a poool
        Optional<Pool> optionalPool = poolRepository.findById(poolId);

        if (optionalPool.isEmpty()) {
            return new ResponseEntity<>("Pool not found", HttpStatus.OK);
        }

        Pool pool = optionalPool.get();

        boolean isDeleted = false;

        if (pool.getMember1() != null && pool.getMember1().getProfileId() == profileId) {
            pool.setMember1(null);
            isDeleted = true;
        }

        if (pool.getMember2() != null && pool.getMember2().getProfileId() == profileId) {
            pool.setMember2(null);
            isDeleted = true;
        }

        if (pool.getMember3() != null && pool.getMember3().getProfileId() == profileId) {
            pool.setMember3(null);
            isDeleted = true;
        }

        if (isDeleted) {
            poolRepository.save(pool);
            return new ResponseEntity<>("Member successfully removed from pool", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Member with given profile ID not found in pool", HttpStatus.OK);
        }
    }

    @DeleteMapping("/deletepool/{poolId}")
    public ResponseEntity<?> deletePoolMember (@PathVariable int poolId) {
        poolRepository.deleteById(poolId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/addUserToPool")
    public ResponseEntity<String> addUserToPool(@RequestBody Map<String, Integer> body) {
        try {
            int poolId = body.get("poolId");
            int profileId = body.get("profileId");
            String resultMessage = poolService.addUserToPool(poolId, profileId);
            return new ResponseEntity<>(resultMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createpool")
    public ResponseEntity<?> createPool(@RequestBody Map<String, Object> poolData) {
        try {
            poolService.createPool(poolData);
            return new ResponseEntity<>("Pool successfully created", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
