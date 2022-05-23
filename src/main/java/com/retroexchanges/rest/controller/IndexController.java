package com.retroexchanges.rest.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:8081")
public class IndexController {

    @GetMapping
    public String sayHello() {
        return "Hello and Welcome to the retroexchanges-rest-server";
    
    }
}
