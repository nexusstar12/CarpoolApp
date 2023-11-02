package com.fantasticfour.poolapp.services;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;

import com.fantasticfour.poolapp.domain.Crew;
import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.UserRepository;
import com.fantasticfour.poolapp.repository.PoolRepository;
import com.fantasticfour.poolapp.repository.CrewRepository;
import com.fantasticfour.poolapp.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoolService {

    private final PoolRepository poolRepository;
    private final ProfileRepository profileRepository;
    private final CrewRepository crewRepository;
    private final CrewService crewService;
    private final UserRepository userRepository;

    @Autowired
    public PoolService(PoolRepository poolRepository, ProfileRepository profileRepository, CrewRepository crewRepository, CrewService crewService, UserRepository userRepository){
        this.poolRepository = poolRepository;
        this.profileRepository = profileRepository;
        this.crewRepository = crewRepository;
        this.crewService = crewService;
        this.userRepository = userRepository;
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

    public void createPool(Map<String, Object> poolData) {
        String description = (String) poolData.get("description");
        String startStreet = (String) poolData.get("start_street");
        String startCity = (String) poolData.get("start_city");
        String startZip = (String) poolData.get("start_zip");
        String startState = (String) poolData.get("start_state");
        String endStreet = (String) poolData.get("end_street");
        String endCity = (String) poolData.get("end_city");
        String endZip = (String) poolData.get("end_zip");
        String endState = (String) poolData.get("end_state");
        Integer creatorId = (Integer) poolData.get("creator_id");
        Boolean publicOrPrivate = (Boolean) poolData.get("public_or_private");
        Integer crewId = (Integer) poolData.get("crew_id");
        LocalDateTime startTime = null;
        try {
            startTime = LocalDateTime.parse((String) poolData.get("start_time"));
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

        poolRepository.save(pool);
    }

    private boolean profileExists(Integer profileId) {
        return profileRepository.existsById(profileId);
    }

    private boolean crewExists(Integer crewId) {
        return crewRepository.existsById(crewId);
    }
}
