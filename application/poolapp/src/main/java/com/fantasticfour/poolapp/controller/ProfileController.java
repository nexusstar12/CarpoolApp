package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.Crew;
import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/")
    public ResponseEntity<Profile> addProfile(@RequestBody Profile profile) {
        Profile newProfile = profileService.addProfile(profile);
        return new ResponseEntity<>(newProfile, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<List<Profile>> getProfilesFromPool(@PathVariable int id) {
        List<Profile> profiles = profileService.getProfilesByPoolId(id);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }
    /*
    @GetMapping("/")
    public ResponseEntity<Profile> getProfileFromPassenger(@RequestBody int passengerId){
        Profile newProfile = profileService.getProfileByPassengerId(passengerId);
        return new ResponseEntity<>(newProfile, HttpStatus.FOUND);
    }
    @GetMapping("{id}")
    public ResponseEntity<List<Profile>> getProfilesFromCrew(@PathVariable int id){
        List<Profile> profiles = profileService.getProfilesByCrewId(id);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }*/


}
