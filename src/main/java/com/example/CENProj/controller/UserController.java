package com.example.CENProj.controller;

import com.example.CENProj.model.Availability;
import com.example.CENProj.model.Dto.UserCreatedDto;
import com.example.CENProj.model.Dto.UserDto;
import com.example.CENProj.model.Therapist;
import com.example.CENProj.model.User;
import com.example.CENProj.model.ViewModel.BaseViewModel;
import com.example.CENProj.model.ViewModel.TherapistViewModel;
import com.example.CENProj.model.enums.UserType;
import com.example.CENProj.repository.TherapistRepository;
import com.example.CENProj.service.AvailabilityService;
import com.example.CENProj.service.TherapistService;
import com.example.CENProj.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "user")
public class UserController {

    private final UserServiceImpl userService;
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    private final TherapistService therapistService;
    private final AvailabilityService availabilityService;
    private final TherapistRepository therapistRepository;


    // handles create user, redirects to account page
    @PostMapping("/create")
    public RedirectView createUser(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password
            , @RequestParam(value = "firstname") String firstName, @RequestParam(value = "userType") String userType
            , @RequestParam(value = "lastname") String lastName, RedirectAttributes redirectAttributes) {
        UserCreatedDto addedUser = this.userService.createUser(email, password, firstName, lastName, UserType.valueOf(userType));
        List<User> users = this.userService.getAllUsers();
        redirectAttributes.addFlashAttribute("users", users);
        BaseViewModel baseViewModel = new BaseViewModel();
        List<String> errorMessages = new ArrayList<>();
        if(addedUser == null) {
            errorMessages.add("Unable to create user");
        }
        errorMessages.add(addedUser.getErrorMsg());
        baseViewModel.setErrorMessages(errorMessages);
        redirectAttributes.addFlashAttribute("user", addedUser);
        redirectAttributes.addFlashAttribute("baseview", baseViewModel);
        return new RedirectView("/user/account");
    }

    // keeps track of logged-in user
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

    // handles logging out, redirects to home page
    @GetMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/";
    }

    // shows login page
    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    // shows the account pages and returns all the users from userService
    @GetMapping("/account")
    public String showAccount(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/account";
    }

    // shows the user you're editing
    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id") int id, Model model) {
        Optional<User> user = this.userService.getUserById(id);
        if(user.isEmpty()) {
            List<User> users = this.userService.getAllUsers();
            model.addAttribute("users", users);
            return "redirect:/user/account";
        }
        model.addAttribute("user", user.get());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDto userDtoOld = (UserDto) authentication.getPrincipal();
        if(id == userDtoOld.getUser().getId()) {
            UserDto userDto = new UserDto(user.get().getEmail(), user.get().getPassword(), new LinkedList<>());
            userDto.setUser(user.get());
            Authentication authenticationNew = new UsernamePasswordAuthenticationToken(userDto, userDto.getPassword(), userDto.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationNew);
            model.addAttribute("loggedInUser", userDto.getUser());
        }
        Therapist therapist = therapistRepository.findByUserId(id);
        if(therapist != null) {
            TherapistViewModel therapistViewModel = new TherapistViewModel();
            therapistViewModel.setTherapist(therapist);
            therapist.getAvailability().forEach(availability -> {
                switch (availability.getDayOfWeek()){
                    case MONDAY:
                        therapistViewModel.setMonday(true);
                        break;
                    case TUESDAY:
                        therapistViewModel.setTuesday(true);
                        break;
                    case WEDNESDAY:
                        therapistViewModel.setWednesday(true);
                        break;
                    case THURSDAY:
                        therapistViewModel.setThursday(true);
                        break;
                    case FRIDAY:
                        therapistViewModel.setFriday(true);
                        break;
                    case SATURDAY:
                        therapistViewModel.setSaturday(true);
                        break;
                    case SUNDAY:
                        therapistViewModel.setSunday(true);
                        break;
                    default:
                        break;
                }
            });
            model.addAttribute("therapist", therapistViewModel);
        }

        return "user/edit";
    }

    /* changes the user you're trying to edit, redirects to account page if no changes made, redirects to the
    * edit page of the user you're editing if there were changes */
    @PostMapping("/edit")
    public String editUserPost(@RequestParam(value = "id") int id, @RequestParam(value = "email") String email,
                               @RequestParam(value = "firstname") String firstName, @RequestParam(value = "lastname") String lastName,
                               @RequestParam(value = "userType") String userType, @RequestParam(value = "specialization", required = false) String specialization,
                               @RequestParam(value = "license-info", required = false) String licenseInfo, @RequestParam(value = "experience", required = false) Integer experience,
                               @RequestParam(value = "monday", required = false) String monday, @RequestParam(value = "tuesday", required = false) String tuesday,
                               @RequestParam(value = "wednesday", required = false) String wednesday, @RequestParam(value = "thursday", required = false) String thursday,
                               @RequestParam(value = "friday", required = false) String friday, @RequestParam(value = "saturday", required = false) String saturday,
                               @RequestParam(value = "sunday", required = false) String sunday, Model model) {
        UserType oldUserType = this.userService.getUserById(id).get().getUserType();
        User updatedUser = this.userService.updateUser(id, email, firstName, lastName, UserType.valueOf(userType));
        if(updatedUser == null) {
            List<User> users = this.userService.getAllUsers();
            model.addAttribute("users", users);
            return "redirect:/user/account";
        }

        if(oldUserType == UserType.THERAPIST) {
            List<Availability> availabilities = availabilityService.findAll();
            List<Availability> filteredAvailabilities = availabilities.stream()
                    .filter(availability ->
                            AvailabilityService.isAvailable(availability, monday, tuesday, wednesday, thursday, friday, saturday, sunday)).toList();
            Therapist therapist = therapistService.updateTherapist(updatedUser, specialization, licenseInfo, experience, filteredAvailabilities);
            TherapistViewModel therapistViewModel  = therapistService.createTherapistViewModel(therapist);
            model.addAttribute("therapist", therapistViewModel);
            model.addAttribute("user", updatedUser);
            return "redirect:/user/edit?id=" + updatedUser.getId();
        }
        model.addAttribute("user", updatedUser);
        return "redirect:/user/edit?id=" + updatedUser.getId();

    }

    // shows user reset confirmation page
    @GetMapping("/reset-user-confirmation")
    public String resetUsernameConfirm(Model model) {
        return "user/reset-user-confirmation";
    }

    // shows forgot username page
    @GetMapping("/forgot-username")
    public String forgotUsername(Model model) {
        return "user/forgot-username";
    }

    // handles the processing for forgot username (gets firstname lastname), redirects to forgot username page
    @PostMapping("/forgot-username")
    public String processForgotUsername(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName,
                                        RedirectAttributes redirectAttributes) {
        User user = userService.getUserByName(firstName, lastName);
        if(user == null) {
            redirectAttributes.addFlashAttribute("message", "Could not find user");
            return "redirect:/user/forgot-username";
        }

        redirectAttributes.addFlashAttribute("user", user);
        return "redirect:/user/forgot-username";
    }

    // Handles resetting username/email, changes email in userService and redirects to reset username confirmation page
    @PostMapping("/reset-username")
    public String resetUsername(@RequestParam(value = "email") String email, @RequestParam(value = "confirm-email") String confirmEmail,
                                @RequestParam(value = "user-id") int userId, RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.getUserById(userId);
        if(user.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Could not find user");
            return "redirect:/user/forgot-username";
        }


        if(!confirmEmail.equals(email)) {
            redirectAttributes.addFlashAttribute("message", "Email does not match");
            return "redirect:/user/forgot-username";
        }
        User userNew = userService.changeEmail(user.get(), confirmEmail);
        redirectAttributes.addFlashAttribute("user", userNew);
        return "redirect:/user/reset-user-confirmation";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "user/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        userService.initiateForgotPassword(email);
        redirectAttributes.addFlashAttribute("message", "Reset link sent if account exists.");
        return "redirect:/user/login";
    }

    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "user/reset-password";
    }

    @PostMapping("/reset-password")
    public String processReset(@RequestParam("token") String token, @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes) {
        boolean result = userService.resetPassword(token, password);
        if (result) {
            return "user/reset-password-confirmation";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired token.");
            return "redirect:/user/reset-password?token=" + token;
        }
    }



}
