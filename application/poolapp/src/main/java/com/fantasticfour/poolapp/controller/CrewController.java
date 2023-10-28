package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.CustomResponse.CrewListResponse;
import com.fantasticfour.poolapp.CustomResponse.CrewResponse;
import com.fantasticfour.poolapp.domain.Crew;
import com.fantasticfour.poolapp.services.CrewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/crew")
public class CrewController {

    @Autowired
    private CrewService crewService;

    @PostMapping({"", "/"})
    public ResponseEntity<Crew> addCrew(@RequestBody Crew crew) {
        Crew newCrew = crewService.addCrew(crew);
        return new ResponseEntity<>(newCrew, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CrewResponse>> getCrewById(@PathVariable("id") int userId) {

        CrewListResponse crewListResponse = new CrewListResponse();
        List<CrewResponse> crewResponselist = new ArrayList<>();

        List<Crew> crews = crewService.getCrewByUserId(userId).stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<Crew>> getAllCrews() {
        List<Crew> crews = crewService.getAllCrews();
        if (crews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
}
