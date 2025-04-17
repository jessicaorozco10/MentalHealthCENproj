package com.example.CENProj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @Autowired
    private JavaMailSender emailSender;
    @GetMapping("/contact-us")
    public String contactUs() {
      return "contact.html";

    }


    @PostMapping("/submit-feedback")
    public String submitFeedback(@RequestParam String name, @RequestParam String email, @RequestParam String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("youremail@gmail.com");  // Use your actual email here
        mailMessage.setSubject("New Feedback from: " + name);
        mailMessage.setText("You have received new feedback from:\n\nName: " + name + "\nEmail: " + email + "\n\nMessage: " + message);

        emailSender.send(mailMessage);

        return "redirect:/thank-you";  // Redirect to a thank-you page after feedback submission
    }
}
