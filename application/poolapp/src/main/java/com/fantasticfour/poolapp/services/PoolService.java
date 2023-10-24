package com.fantasticfour.poolapp.services;
import java.util.Optional;
import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.repository.PoolRepository;
import com.fantasticfour.poolapp.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PoolService {

    private final PoolRepository poolRepository;

    private final ProfileRepository profileRepository;

    @Autowired
    public PoolService(PoolRepository poolRepository, ProfileRepository profileRepository){
        this.poolRepository = poolRepository;
        this.profileRepository = profileRepository;
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


}
