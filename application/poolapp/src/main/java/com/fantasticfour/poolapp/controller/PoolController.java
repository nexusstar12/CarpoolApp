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

    @PostMapping("/create/{poolId}")
    public ResponseEntity<Pool> markVisibility(@RequestParam String status, @PathVariable int poolId) {
        if(status == "public"){
            poolService.setProfileToPublic(true, poolId);
        }
        else if(status == "private"){
            poolService.setProfileToPrivate(false, poolId);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
