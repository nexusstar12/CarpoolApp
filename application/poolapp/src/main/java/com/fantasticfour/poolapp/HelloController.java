package com.fantasticfour.poolapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @RequestMapping(value = "/")
    public String index() {
        return "aboutMe.html";
    }
}