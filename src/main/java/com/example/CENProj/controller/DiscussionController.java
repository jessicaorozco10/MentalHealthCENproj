package com.example.CENProj.controller;

import com.example.CENProj.model.Dto.UserDto;
import com.example.CENProj.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "discussion")
public class DiscussionController {

    @ModelAttribute("loggedInUser")
    public User populateTypes() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            UserDto userDto = (UserDto) authentication.getPrincipal();
            return userDto.getUser();
        } catch (Exception ignored) {}
        return null;
    }

    @GetMapping("")
    public String index(Model model) {
        return "discussion/index";
    }

}
