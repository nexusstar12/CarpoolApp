package com.fantasticfour.poolapp.services;
import java.util.Optional;

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

    public String addUserToPool(int poolId, int userId) throws Exception {
        Optional<Pool> poolOptional = poolRepository.findById(poolId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(!poolOptional.isPresent()) {
            throw new Exception("Pool not found");
        }
        if(!userOptional.isPresent()) {
            throw new Exception("User not found");
        }

        Pool pool = poolOptional.get();
        User user = userOptional.get();

        Profile profile = profileRepository.findByUserId(user);
        if(profile == null) {
            throw new Exception("Profile not found for user");
        }

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
        return "User successfully added to the pool";
    }
}
