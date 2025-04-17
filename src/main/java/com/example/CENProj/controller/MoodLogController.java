package com.example.CENProj.controller;

import com.example.CENProj.model.enums.MoodType;
import com.example.CENProj.model.User;
import com.example.CENProj.model.Dto.UserDto;
import com.example.CENProj.service.MoodTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mood-tracker")
@RequiredArgsConstructor
public class MoodLogController {

    private final MoodTrackService moodTrackService;

    @ModelAttribute("loggedInUser")
    public User populateLoggedInUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto dto = (UserDto) auth.getPrincipal();
            return dto.getUser();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping({"", "/"})
    public String showMoodPage(Model model) {
        model.addAttribute("moodOptions", MoodType.values());
        model.addAttribute("distribution", moodTrackService.countsByMood());
        return "mood-tracker";    // renders mood-tracker.html
    }

    @PostMapping
    public String submitMood(
            @RequestParam("moodType") MoodType moodType,
            @ModelAttribute("loggedInUser") User user,
            Model model
    ) {
        moodTrackService.createMoodLog(moodType, user);

        model.addAttribute("moodOptions", MoodType.values());
        model.addAttribute("distribution", moodTrackService.countsByMood());
        model.addAttribute("message", "Logged mood: " + moodType);

        return "mood-tracker";
    }

    @GetMapping("/report")
    public String showReport(Model model) {
        model.addAttribute("distribution", moodTrackService.countsByMood());
        model.addAttribute("dailyData", moodTrackService.dailyCounts());
        return "MoodReports";
    }
}




