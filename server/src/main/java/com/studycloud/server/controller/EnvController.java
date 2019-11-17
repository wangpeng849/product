package com.studycloud.server.controller;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/env")
@RestController
public class EnvController {

    @Value("${env}")
    private  String env;

    @GetMapping("getValue")
    public String getEnv(){
        return env;
    }
}
