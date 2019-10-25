package com.studycloud.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @RequestMapping("/msg")
    public String productMsg(){
        return "this is product message";
    }
}
