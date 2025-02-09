package com.example.CENProj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
public class SeedStarterMngController {
    @ModelAttribute("allTypes")
    public List<String> populateTypes() {
        List<String> test = new LinkedList<>();
        test.add("Hello");
        test.add("yes");
        return test;
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
    public String showAboutUs() {
        return "about-us";
    }

    @RequestMapping("/community-forums")
    public String showCommunityForums() {
        return "community-forums";
    }

    @RequestMapping("/account")
    public String showAccount() {
        return "account";
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
