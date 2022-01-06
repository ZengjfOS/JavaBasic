package com.zengjf.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/")
    String home() {
        return "home page";
    }

    @RequestMapping("/index.html")
    String index() {
        return "index page";
    }
    
}
