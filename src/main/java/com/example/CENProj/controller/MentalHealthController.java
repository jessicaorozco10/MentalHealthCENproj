package com.example.CENProj.controller;

import com.example.CENProj.model.Dto.UserDto;
import com.example.CENProj.model.User;
import com.example.CENProj.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MentalHealthController {

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

    @RequestMapping({"/","/seedstartermng"})
    public String showSeedstarters() {
        return "seedstartermng";
    }

    @RequestMapping(value="/seedstartermng", params={"save"})
    public String saveSeedstarter(final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "seedstartermng";
        }
        model.clear();
        return "redirect:/seedstartermng";
    }

    @RequestMapping("/about-us")
    public String showAboutUs(Model model) {
        return "about-us";
    }

    @RequestMapping("/find-therapists")
    public String showFindTherapists() {
        return "find-therapists";
    }

    @RequestMapping("/therapy-portal")
    public String showTherapyPortal() {
        return "therapy-portal";
    }

    @RequestMapping("/mood-tracker")
    public String showMoodTracker() {
        return "mood-tracker";
    }

    @RequestMapping("/therapy-exercises")
    public String showTherapyExercises() {
        return "therapy-exercises";
    }

    @RequestMapping("/meditation-guides")
    public String showMeditationGuides() {
        return "meditation-guides";
    }

    @RequestMapping("/crisis-management-resources")
    public String showCrisisManagementResources() {
        return "crisis-management-resources";
    }

    @RequestMapping("/faq")
    public String showFaq() {
        return "faq";
    }

}
