package com.example.CENProj.service;

import com.example.CENProj.model.Appointment;
import com.example.CENProj.model.User;
import com.example.CENProj.repository.AppointmentRepository;
import com.example.CENProj.service.interfaces.SchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService implements SchedulingService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment scheduleAppointment(User client, User therapist, String reason, String scheduledTime) {
        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setTherapist(therapist);
        appointment.setReason(reason);
        appointment.setScheduledTime(scheduledTime); // Set the selected mock time
        return appointmentRepository.save(appointment);
    }
}
