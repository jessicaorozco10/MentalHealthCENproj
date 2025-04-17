package com.example.CENProj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// /forgotusername
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "forgotusername")
public class ForgotUsernameController {
    @GetMapping({"/", ""})
    public String index(Model model) {
        return "forgotusername";
    }
}
