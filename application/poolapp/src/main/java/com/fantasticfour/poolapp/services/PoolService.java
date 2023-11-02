package com.fantasticfour.poolapp.services;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;

import com.fantasticfour.poolapp.domain.*;
import com.fantasticfour.poolapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoolService {

    private final PoolRepository poolRepository;
    private final ProfileRepository profileRepository;
    private final CrewRepository crewRepository;
    private final CrewService crewService;
    private final UserRepository userRepository;

    private final ZipDataRepository zipDataRepository;

    @Autowired
    public PoolService(PoolRepository poolRepository, ProfileRepository profileRepository, CrewRepository crewRepository, CrewService crewService, UserRepository userRepository, ZipDataRepository zipDataRepository){
        this.poolRepository = poolRepository;
        this.profileRepository = profileRepository;
        this.crewRepository = crewRepository;
        this.crewService = crewService;
        this.userRepository = userRepository;
        this.zipDataRepository = zipDataRepository;
    }

    public void addProfileToPool(int poolId, int profileId){
        Optional<Pool> poolOptional = poolRepository.findById(poolId);
        Optional<Profile> profileOptional = profileRepository.findById(profileId);
        if(poolOptional.isPresent() && profileOptional.isPresent()) {
            Pool pool = poolOptional.get();
            Profile profile = profileOptional.get();
            if(pool.getMember1() == null){
                pool.setMember1(profile);
            }
            else if(pool.getMember2() == null){
                pool.setMember2(profile);
            }
            else if(pool.getMember3() == null){
                pool.setMember3(profile);
            }
            poolRepository.save(pool);
        }
    }

    public void setProfileToPublic(boolean status, int poolId){
        Optional<Pool> poolOptional = poolRepository.findById(poolId);
        if(poolOptional.isPresent()) {
            Pool pool = poolOptional.get();
            pool.setViewable(status);
            pool.setCrew(null);
        }
    }

    public void setProfileToPrivate(boolean status, int poolId){
        Optional<Pool> poolOptional = poolRepository.findById(poolId);
        if(poolOptional.isPresent()) {
            Pool pool = poolOptional.get();
            pool.setViewable(status);
            if(crewRepository.findByPool_PoolId(poolId) != null){
                pool.setCrew(crewService.getCrewbyPoolId(poolId));
            }
        }
    }

    public String addUserToPool(int poolId, int profileId) throws Exception {
        Optional<Pool> poolOptional = poolRepository.findById(poolId);
        Profile profile = profileRepository.findById(profileId).orElse(null); // Assuming profileId is the primary key in the Profile table

        if(!poolOptional.isPresent()) {
            throw new Exception("Pool not found");
        }
        if(profile == null) {
            throw new Exception("Profile not found");
        }

        Pool pool = poolOptional.get();

        if(pool.getMember1() != null && pool.getMember2() != null && pool.getMember3() != null) {
            throw new Exception("Pool is already full");
        }

        if(pool.getMember1() == null) {
            pool.setMember1(profile);
        }
        else if(pool.getMember2() == null) {
            pool.setMember2(profile);
        }
        else if(pool.getMember3() == null) {
            pool.setMember3(profile);
        }

        poolRepository.save(pool);
        return "Profile successfully added to the pool";
    }

    private String formatAddress(String street, String city, String state, String zip) {
        return String.format("%s, %s, %s, %s", street, city, state, zip);
    }
    public void createPool(Map<String, Object> poolData) {
        String description = (String) poolData.get("name");
        String startStreet = (String) poolData.get("startStreet");
        String startCity = (String) poolData.get("startCity");
        String startZip = (String) poolData.get("startZip");
        String startState = (String) poolData.get("startState");
        String endStreet = (String) poolData.get("endStreet");
        String endCity = (String) poolData.get("endCity");
        String endZip = (String) poolData.get("endZip");
        String endState = (String) poolData.get("endState");
        Integer creatorId = (Integer) poolData.get("creatorId");
        Boolean publicOrPrivate = (Boolean) poolData.get("privacy");
        Integer crewId = (Integer) poolData.get("crewId");
        String startAddress = formatAddress(startStreet, startCity, startState, startZip);
        String endAddress = formatAddress(endStreet, endCity, endState, endZip);
        LocalDateTime startTime = null;
        try {
            startTime = LocalDateTime.parse((String) poolData.get("startTime"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date-time format for start_time", e);
        }
        // Validation
        if (description == null ||
                startStreet == null ||
                startCity == null ||
                startZip == null ||
                startState == null ||
                endStreet == null ||
                endCity == null ||
                endZip == null ||
                endState == null ||
                creatorId == null ||
                publicOrPrivate == null ||
                startTime == null) {
            throw new IllegalArgumentException("Required fields are missing");
        }

        if(!profileExists(creatorId)) {
            throw new IllegalArgumentException("ProfileId not found");
        }

        Crew crewEntity = null;
        if (crewId != null) {
            if (!crewExists(crewId)) {
                throw new IllegalArgumentException("CrewId not found");
            } else {
                crewEntity = crewRepository.findById(crewId).orElse(null);
            }
        }

        Profile creatorProfile = profileRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("ProfileId not found"));


        Pool pool = new Pool();
        pool.setDescription(description);
        pool.setStartStreet(startStreet);
        pool.setStartCity(startCity);
        pool.setStartZip(startZip);
        pool.setStartState(startState);
        pool.setEndStreet(endStreet);
        pool.setEndCity(endCity);
        pool.setEndZip(endZip);
        pool.setEndState(endState);
        pool.setCreator(creatorProfile);
        pool.setPublicOrPrivate(publicOrPrivate);
        pool.setCrew(crewEntity);
        pool.setStartTime(startTime);

        pool.setStartLocation(startAddress);
        pool.setEndLocation(endAddress);




        //add long and lat data
        Optional<ZipData> optionalStartZip=  zipDataRepository.findById(pool.getStartZip());
        Optional<ZipData> optionalEndZip=  zipDataRepository.findById(pool.getEndZip());

        if(optionalStartZip.isPresent()) {
            ZipData zipData = optionalStartZip.get();
            pool.setStartLatitude(zipData.getLatitude());
            pool.setStartLongitude(zipData.getLongitude());
        }
        if(optionalEndZip.isPresent()) {
            ZipData zipData =optionalEndZip.get();
            pool.setEndLatitude(zipData.getLatitude());
            pool.setEndLongitude(zipData.getLongitude());
        }

        poolRepository.save(pool);
    }

    private boolean profileExists(Integer profileId) {
        return profileRepository.existsById(profileId);
    }

    private boolean crewExists(Integer crewId) {
        return crewRepository.existsById(crewId);
    }
}
