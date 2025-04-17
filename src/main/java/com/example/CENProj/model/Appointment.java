package com.example.CENProj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import jakarta.persistence.Id;


@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User client;

    @ManyToOne
    private User therapist;

    private String appointmentTime;

    private String reason;

    private String scheduledTime;

    private String status; // e.g. "Scheduled", "Cancelled", "Completed"

    private String notes;
}
