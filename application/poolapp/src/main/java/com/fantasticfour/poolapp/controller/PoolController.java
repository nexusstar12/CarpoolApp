package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.Pool;
import com.fantasticfour.poolapp.services.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pool")
public class PoolController {

    @Autowired
    private PoolService poolService;

    @PostMapping("/join/{poolId}")
    public ResponseEntity<?> joinPool(@PathVariable int poolId, @RequestBody int profileId){
        poolService.addProfileToPool(poolId, profileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //might need to change parameter based on postInput
    @PostMapping("/create/{id}")
    public ResponseEntity<Pool> markVisibility(@RequestParam String status, @PathVariable int id) {
        if(status.equals("public")){
            poolService.setProfileToPublic(true, id);
        }
        else if(status.equals("private")){
            poolService.setProfileToPrivate(false, id);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping ("/getpools/{id}")
//    public ResponseEntity<?> getPools (@PathVariable int userId) {
//
//       return new ResponseEntity<>(HttpStatus.OK);
//    }

}
