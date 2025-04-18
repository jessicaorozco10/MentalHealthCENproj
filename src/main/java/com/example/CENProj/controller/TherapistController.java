package com.example.CENProj.controller;

import com.example.CENProj.model.Availability;
import com.example.CENProj.model.Dto.UserDto;
import com.example.CENProj.model.Therapist;
import com.example.CENProj.model.User;
import com.example.CENProj.model.ViewModel.TherapistViewModel;
import com.example.CENProj.service.TherapistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "therapist")
public class TherapistController {
    private final TherapistService therapistService;

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

    // shows find therapists page
    @GetMapping({"/",""})
    public String index(Model model) {
        return "find-therapists";
    }

    @PostMapping("/find")
    public RedirectView find(@RequestParam(value = "name") String name, RedirectAttributes redirectAttributes) {
        List<Therapist> therapistList = this.therapistService.findByCriteria();
        List<TherapistViewModel> therapistViewModels = therapistList.stream().map(therapist -> {
            TherapistViewModel therapistViewModel = new TherapistViewModel();
            therapistViewModel.setTherapist(therapist);
            StringBuilder availability = new StringBuilder();
            int availabilityCount = therapist.getAvailability().size();
            int count = 0;
            for (Availability availabilityEnum : therapist.getAvailability()) {
                availability.append(availabilityEnum.getDayOfWeek().name());
                count++;
                if(count < availabilityCount) availability.append(", ");
            }

            return therapistViewModel;
        }).toList();
        redirectAttributes.addFlashAttribute("therapists", therapistViewModels);
        return new RedirectView("/therapist");
    }
}
