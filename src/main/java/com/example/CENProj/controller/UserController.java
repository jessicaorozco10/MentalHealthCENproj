package com.example.CENProj.controller;

import com.example.CENProj.model.Dto.UserCreatedDto;
import com.example.CENProj.model.Dto.UserDto;
import com.example.CENProj.model.User;
import com.example.CENProj.model.ViewModel.BaseViewModel;
import com.example.CENProj.model.enums.UserType;
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

    @GetMapping("/")
    public String userProfile(Model model) {
        return "user/profile";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/reset-pass-confirmation")
    public String reset(){
        return "user/reset-pass-confirmation";
    }

    @GetMapping("/account")
    public String showAccount(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/account";
    }

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

        return "user/edit";
    }

    @PostMapping("/edit")
    public String editUserPost(@RequestParam(value = "id") int id, @RequestParam(value = "email") String email,
                               @RequestParam(value = "firstname") String firstName, @RequestParam(value = "lastname") String lastName,
                               @RequestParam(value = "userType") String userType, Model model) {
        User updatedUser = this.userService.updateUser(id, email, firstName, lastName, UserType.valueOf(userType));
        if(updatedUser == null) {
            List<User> users = this.userService.getAllUsers();
            model.addAttribute("users", users);
            return "redirect:/user/account";
        }
        model.addAttribute("user", updatedUser);
        return "redirect:/user/edit?id=" + updatedUser.getId();
    }

    @PostMapping("/verifylogin")
    public boolean VerifyLogin(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password){
        return true;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "user/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, @RequestParam("email-retype") String emailRetype, RedirectAttributes redirectAttributes) {
      if (!email.equals(emailRetype)) return "redirect:/user/forgot-password";

      User user =  userService.getUserByEmail(email);

      if(user == null) return "redirect:/user/forgot-password";

      redirectAttributes.addFlashAttribute("message", "Reset link sent if account exists.");
        redirectAttributes.addFlashAttribute("user", user);

        return "redirect:/user/forgot-password";
    }

    @PostMapping("/forgot-password/create-password")
    public String createForgotPassword(@RequestParam("password") String password,
                                       @RequestParam("password-retype") String passwordRetype, RedirectAttributes redirectAttributes,
                                       @RequestParam("id") int id) {

        if (!password.equals(passwordRetype)) return "redirect:/user/forgot-password";

        Optional<User> user =  userService.getUserById(id);

        if(user.isEmpty()) return "redirect:/user/forgot-password";


        userService.changePassword(user.get(), password);

        redirectAttributes.addFlashAttribute("message", "Reset link sent if account exists.");
        redirectAttributes.addFlashAttribute("user", user);

        return "redirect:/user/reset-pass-confirmation";
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
