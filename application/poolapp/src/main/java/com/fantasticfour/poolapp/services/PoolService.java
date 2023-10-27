package com.fantasticfour.poolapp.services;
import java.util.Optional;

import com.fantasticfour.poolapp.domain.Crew;
import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.services.CrewService;
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

    @Autowired
    public PoolService(PoolRepository poolRepository, ProfileRepository profileRepository, CrewRepository crewRepository, CrewService crewService){
        this.poolRepository = poolRepository;
        this.profileRepository = profileRepository;
        this.crewRepository = crewRepository;
        this.crewService = crewService;
    }

    public void addProfileToPool(int poolId, int profileId){
        Optional<Pool> poolOptional = poolRepository.findById(poolId);
        Optional<Profile> profileOptional = profileRepository.findById(profileId);
        if(poolOptional.isPresent() && profileOptional.isPresent()) {
            Pool pool = poolOptional.get();
            Profile profile = profileOptional.get();
            pool.addProfile(profile);
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


}
