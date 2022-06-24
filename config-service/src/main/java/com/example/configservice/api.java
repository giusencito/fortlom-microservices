package com.example.configservice;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class api {

    @GetMapping
    public String getAllEvents() {
        return "config-service on";
    }

}
