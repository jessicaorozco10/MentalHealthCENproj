package com.example.CENProj.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public static void send(String to, String subject, String text) {
        System.out.println(" Email sent to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + text);
    }
}
