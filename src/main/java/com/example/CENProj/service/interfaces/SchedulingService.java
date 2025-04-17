package com.example.CENProj.service.interfaces;

import com.example.CENProj.model.Appointment;
import com.example.CENProj.model.User;

public interface SchedulingService {
    Appointment scheduleAppointment(User client, User therapist, String reason, String scheduledTime);
}
