package com.fantasticfour.poolapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"", "/"})
    public ResponseEntity<String> home() {
        String message = "Welcome to My App";
        return ResponseEntity.ok(message);
    }
}
