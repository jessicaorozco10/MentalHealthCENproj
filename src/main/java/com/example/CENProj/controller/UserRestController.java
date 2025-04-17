package com.example.CENProj.controller;

import com.example.CENProj.model.Dto.UserDto;
import com.example.CENProj.model.User;
import com.example.CENProj.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/user")
public class UserRestController {

    private final UserServiceImpl userService;


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

    @DeleteMapping("/delete")
    public boolean Delete(@RequestParam(value = "id") int id) {
       return this.userService.delete(id);
    }
}
