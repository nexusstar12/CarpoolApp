package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.Crew;
import com.fantasticfour.poolapp.services.CrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Crew> getCrewById(@PathVariable("id") int id) {
        Optional<Crew> crew = crewService.getCrewById(id);
        return crew.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
