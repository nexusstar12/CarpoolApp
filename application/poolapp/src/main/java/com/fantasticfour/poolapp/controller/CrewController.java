/**
 * CrewController is part of the pool REST api that handles logic for CREWS.
 *  FYI: A crew is a group of people that can schedule rides with one another.
 */
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

    /**
     * Create a crew entity.
     * @param crew from json request body.
     * @return ResponseEntity<Crew>
     */
    @PostMapping({"", "/"})
    public ResponseEntity<Crew> addCrew(@RequestBody Crew crew) {
        Crew newCrew = crewService.addCrew(crew);
        return new ResponseEntity<>(newCrew, HttpStatus.CREATED);
    }

    /**
     * Retrieves crews certain profiles are a member of.
     * @param profileId
     * @return a list of crew entities the user is a member of.
     */
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
                if((crew.getCreator() != null)) {
                    crewResponse.setOneMember(crew.getCreator());
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

    /**
     * Updates members of an existing crew.
     * @param id (crew id)
     * @param crew
     * @return A json response body of the updated crew.
     */
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


    /**
     * Removes a single member of a crew, if the member of the crew removed is also the
     * creator then the entire crew entity is deleted.
     * @param jsonMap : json request body with profileID and crewID.
     * @return String in json response body whether removal is successful or not.
     */
    @DeleteMapping("/remove/member")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, Object> jsonMap) {
        jsonMap.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
        System.out.println("we are in the remove member function");
        if(jsonMap.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        int profileId = (int)jsonMap.get("profileId");
        int crewId = (int)jsonMap.get("crew_id");


        Optional<Crew> optionalCrew = crewRepository.findById(crewId);

        if(!optionalCrew.isPresent()){
            return new ResponseEntity<>("Crew not found", HttpStatus.NOT_FOUND);
        }

        Crew crew = optionalCrew.get();

        //if profileId called is the creator, delete the entire crew
        //if not, remove profile from crew

        boolean isDeleted = false;
        if(crew.getCreator() != null && profileId == crew.getCreator().getProfileId()){
            deleteCrew(crew.getCrewId());

            //set pool created to 0 after creator is deleted. because crew is deleted when creator deletes a crew.
            Optional<Pool> optionalPool = poolRepository.findById(crew.getOriginPoolId());
            if (optionalPool.isPresent()) {
                Pool originalPool = optionalPool.get();
                originalPool.setCrewCreated(false);
                poolRepository.save(originalPool);
            }else {
                System.out.println("origin pool id does not exist to toggle crew_created field");
            }

            return new ResponseEntity<>("Crew deleted", HttpStatus.OK);
        }

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

    /**
     * Delete a crew entity.
     * @param id (Crew ID)
     * @return no content returned on deletion.
     */
    @DeleteMapping("/{id:[\\d]+}") //"/{id}" regex to make pathing more specific, only accepts integers.
    public ResponseEntity<Void> deleteCrew(@PathVariable("id") int id) {
        crewService.deleteCrew(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Create a crew from from a previous pool id(carpoool id). Member of the pool will be the
     * new members of the crew.
     * @param jsonMap keys including the pool_id and creator of the crew.
     * @return a json return body confirming if new crew is created or not.
     */
    @PostMapping("/createcrew")
    public ResponseEntity<?> createCrew(@RequestBody Map<String,Object> jsonMap){
        jsonMap.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        if(jsonMap.isEmpty()){
            return new ResponseEntity<>("Nothing in Json Body", HttpStatus.NO_CONTENT);
        }
        boolean profileExists = false;
        Profile creator_id;
        Profile member1_id;
        Profile member2_id;
        Profile member3_id;
        int originPoolId;

        Crew crew = new Crew();
        Pool pool = new Pool();


        if(jsonMap.get("origin_pool_id") != null){
            originPoolId = (int)jsonMap.get("origin_pool_id");
            Optional<Pool> optionalPool = poolRepository.findPoolByPoolId(originPoolId);
            if(optionalPool.isPresent()){
                pool = optionalPool.get();
                pool.setCrewCreated(true);
                poolRepository.save(pool);
                crew.setOriginPoolId((int)jsonMap.get("origin_pool_id"));
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

        if(pool.getMember1() != null){
            member1_id = pool.getMember1();
            crew.setMember1(member1_id);
            profileExists = true;
        }
        if(pool.getMember2() != null){
            member2_id = pool.getMember1();
            crew.setMember2(member2_id);
            profileExists = true;
        }
        if(pool.getMember3() != null){
            member3_id = pool.getMember1();
            crew.setMember3(member3_id);
            profileExists = true;
        }
        crew.setDescription(pool.getDescription());


        if(profileExists){
            crewRepository.save(crew);
            return new ResponseEntity<>("Crew created", HttpStatus.OK);
        }

        else{
            return new ResponseEntity<>("Profile(s) do not exist, crew cannot be created", HttpStatus.NOT_FOUND);
        }

    }
}
