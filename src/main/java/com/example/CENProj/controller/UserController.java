package com.example.CENProj.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class UserController {
    @GetMapping("/")
    public String Test() {
        return "Hello User";
    }
    @GetMapping("/Login")
    public boolean Login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        return true;
    }
}
