package com.example.CENProj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "user")
public class UserController {

    @GetMapping("/")
    public String Test() {
        return "Hello User";
    }

    @GetMapping("/login")
    public String Login(){
        return "user/login";
    }

    @PostMapping("/verifylogin")
    public boolean VerifyLogin(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password){
        return true;
    }
}
