package ru.bolnik.testoauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/login")
    public String login(){
        return "login work";
    }

    @GetMapping
    public String internal(){
        return "internal work";
    }
}

