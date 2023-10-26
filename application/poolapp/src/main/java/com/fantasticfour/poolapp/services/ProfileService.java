package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService  {
    @Autowired
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile addProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    // Method to fetch profiles based on poolId
    public List<Profile> getProfilesByPoolId(int poolId) {
        return profileRepository.findByPool_PoolId(poolId);
    }
    /*public Profile getProfileByPassengerId(int passengerId){
        return profileRepository.findProfileByPassengerId(passengerId);
    }

    /*public List<Profile> getProfilesByCrewId(int crewId) {
        return profileRepository.findByCrew_CrewId(crewId);
    }*/

}
