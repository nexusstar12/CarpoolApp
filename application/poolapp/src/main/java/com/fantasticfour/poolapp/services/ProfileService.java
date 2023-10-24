package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService  {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    public Profile addProfile(Profile profile){return profileRepository.save(profile);}
}
