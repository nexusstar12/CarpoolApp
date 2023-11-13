package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.CustomResponse.CrewListResponse;
import com.fantasticfour.poolapp.CustomResponse.CrewResponse;
import com.fantasticfour.poolapp.domain.Crew;
import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.domain.Profile;
import com.fantasticfour.poolapp.repository.PoolRepository;
import com.fantasticfour.poolapp.repository.ProfileRepository;
import com.fantasticfour.poolapp.services.CrewService;
import com.fantasticfour.poolapp.repository.CrewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/crew")
public class CrewController {

    @Autowired
    private CrewService crewService;

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PoolRepository poolRepository;

    @PostMapping({"", "/"})
    public ResponseEntity<Crew> addCrew(@RequestBody Crew crew) {
        Crew newCrew = crewService.addCrew(crew);
        return new ResponseEntity<>(newCrew, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CrewResponse>> getCrewById(@PathVariable("id") int profileId) {

        CrewListResponse crewListResponse = new CrewListResponse();
        List<CrewResponse> crewResponselist = new ArrayList<>();

        List<Crew> crews = crewService.getCrewByProfileId(profileId).stream()
                                      .filter(Optional::isPresent)
                                      .map(Optional::get)
                                      .collect(Collectors.toList());
        if(!crews.isEmpty()){
            for (Crew crew: crews
                 ) {
                CrewResponse crewResponse = new CrewResponse();
                crewResponse.setCrewId(crew.getCrewId());
                crewResponse.setDescription(crew.getDescription());
                if ((crew.getMember1() != null)) {
                    crewResponse.setOneMember(crew.getMember1());
                } else {
                    System.out.println("User does not Exist");
                }
                if ((crew.getMember2() != null)) {
                    crewResponse.setOneMember(crew.getMember2());
                } else {
                    System.out.println("User does not Exist");
                }
                if ((crew.getMember3() != null)) {
                    crewResponse.setOneMember(crew.getMember3());
                } else {
                    System.out.println("User does not Exist");
                }
                crewResponselist.add(crewResponse);
            }
            return new ResponseEntity<>(crewResponselist,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<Crew>> getAllCrews() {
        List<Crew> crews = crewService.getAllCrews();
        if (crews.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(crews, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crew> updateCrew(@PathVariable("id") int id, @RequestBody Crew crew) {
        Optional<Crew> currentCrew = crewService.getCrewById(id);
        if (currentCrew.isPresent()) {
            Crew updatedCrew = crewService.updateCrew(crew);
            return new ResponseEntity<>(updatedCrew, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrew(@PathVariable("id") int id) {
        crewService.deleteCrew(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/removemember")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, Object> jsonMap) {
        jsonMap.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        if(jsonMap.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        int profileId = (int)jsonMap.get("profileId");
        int crewId = (int)jsonMap.get("crewId");

        Optional<Crew> optionalCrew = crewRepository.findById(crewId);

        if(!optionalCrew.isPresent()){
            return new ResponseEntity<>("Crew not found", HttpStatus.NOT_FOUND);
        }

        Crew crew = optionalCrew.get();

        boolean isDeleted = false;

        if(crew.getMember1() != null && crew.getMember1().getProfileId() == profileId){
            crew.setMember1(null);
            isDeleted = true;
        }
        if(crew.getMember2() != null && crew.getMember2().getProfileId() == profileId){
            crew.setMember2(null);
            isDeleted = true;
        }
        if(crew.getMember3() != null && crew.getMember3().getProfileId() == profileId){
            crew.setMember3(null);
            isDeleted = true;
        }
        if(crew.getCreator() != null && crew.getCreator().getProfileId() == profileId){
            crew.setCreator(null);
            isDeleted = true;
        }

        if(isDeleted){
            crewRepository.save(crew);
            return new ResponseEntity<>("Profile with id" + profileId + "removed from crew", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Profile with id" + profileId + "not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createcrew")
    public ResponseEntity<?> createCrew(@RequestBody Map<String,Object> jsonMap){
        jsonMap.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        if(jsonMap.isEmpty()){
            return new ResponseEntity<>("Nothing in Json Body", HttpStatus.NO_CONTENT);
        }
        String description = (String)jsonMap.get("description");
        boolean profileExists = false;
        Profile creator_id;
        Profile member1_id;
        Profile member2_id;
        Profile member3_id;
        int originPoolId;

        Crew crew = new Crew();
        crew.setDescription(description);

        if(jsonMap.get("origin_pool_id") != null){
            originPoolId = (int)jsonMap.get("origin_pool_id");
            Optional<Pool> optionalPool = poolRepository.findPoolByPoolId(originPoolId);
            if(optionalPool.isPresent()){
                Pool pool = optionalPool.get();
                pool.setCrewCreated(true);
                poolRepository.save(pool);
            }
        }

        //checks for existing profileId
        if(jsonMap.get("creator_id") != null){
            int creatorid = (int)jsonMap.get("creator_id");
            Optional<Profile> creator = profileRepository.findProfileByProfileId(creatorid);
            if(creator.isPresent()){
                creator_id = creator.get();
                crew.setCreator(creator_id);
                profileExists = true;
            }
        }
        if(jsonMap.get("member1_id") != null){
            Optional<Profile> member_1 = profileRepository.findProfileByProfileId((int)jsonMap.get("member1_id"));
            if(member_1.isPresent()){
                member1_id = member_1.get();
                crew.setMember1(member1_id);
                profileExists = true;
            }
        }
        if(jsonMap.get("member2_id") != null){
            Optional<Profile> member_2 = profileRepository.findProfileByProfileId((int)jsonMap.get("member2_id"));
            if(member_2.isPresent()){
                member2_id = member_2.get();
                crew.setMember2(member2_id);
                profileExists = true;
            }
        }
        if(jsonMap.get("member3_id") != null){
            Optional<Profile> member_3 = profileRepository.findProfileByProfileId((int)jsonMap.get("member3_id"));
            if(member_3.isPresent()){
                member3_id = member_3.get();
                crew.setMember3(member3_id);
                profileExists = true;
            }
        }

        if(profileExists){
            crewRepository.save(crew);
            return new ResponseEntity<>("Crew created", HttpStatus.OK);
        }

        else{
            return new ResponseEntity<>("Profile(s) do not exist, crew cannot be created", HttpStatus.NOT_FOUND);
        }









    }
}
