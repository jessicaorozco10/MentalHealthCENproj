package com.example.CENProj.controller;

import com.example.CENProj.model.Appointment;
import com.example.CENProj.model.User;
import com.example.CENProj.service.AppointmentService;
import com.example.CENProj.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserServiceImpl userService;


    @GetMapping("/schedule")
    public String showScheduleForm(Model model) {
        model.addAttribute("appointment", new Appointment());

        List<String> mockTimes = List.of(
                "2025-04-17T09:00",
                "2025-04-17T10:00",
                "2025-04-17T11:30",
                "2025-04-17T14:00",
                "2025-04-17T15:30"
        );
        model.addAttribute("mockTimes", mockTimes);

        return "appointments/form";
    }

}
