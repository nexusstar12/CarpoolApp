package com.fantasticfour.poolapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class TestController {
    @GetMapping("/test")
    public ResponseEntity <String> testEndPoint(){
        return ResponseEntity.ok("Howdy");
    }
}
