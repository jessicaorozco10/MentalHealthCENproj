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

    // Injecting JavaMailSender instance to send email
    @Autowired
    private JavaMailSender emailSender;

    /**
     * Handles the GET request to the "/contact-us" page.
     *
     * @return The name of the HTML file to render (contact.html).
     */
    @GetMapping("/contact-us")
    public String contactUs() {
        return "contact.html";  // Returns the contact page for user interaction
    }

    /**
     * Handles the POST request to submit feedback from the contact form.
     *
     * @param name    The name of the person submitting feedback (taken from the form).
     * @param email   The email of the person submitting feedback (taken from the form).
     * @param message The feedback message provided by the user (taken from the form).
     * @return A redirect URL to the thank-you page after feedback submission.
     */
    @PostMapping("/submit-feedback")
    public String submitFeedback(@RequestParam String name, @RequestParam String email, @RequestParam String message) {
        // Create a new SimpleMailMessage instance to compose the email
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // Setting the recipient's email address (this is the email where feedback will be sent)
        mailMessage.setTo("youremail@gmail.com");  // Use your actual email here

        // Setting the subject of the email, dynamically including the submitter's name
        mailMessage.setSubject("New Feedback from: " + name);

        // Setting the body of the email, including the name, email, and feedback message from the user
        mailMessage.setText("You have received new feedback from:\n\nName: " + name + "\nEmail: " + email + "\n\nMessage: " + message);

        // Sending the email using the injected JavaMailSender instance
        emailSender.send(mailMessage);

        // Redirecting to a "thank-you" page after the feedback is submitted successfully
        return "redirect:/thank-you";  // Redirects the user to a thank-you page after feedback submission
    }
}
