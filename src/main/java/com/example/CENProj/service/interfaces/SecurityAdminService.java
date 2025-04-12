package com.example.CENProj.service.interfaces;

// Security & Administration Subsystem
public interface SecurityAdminService {
    void provideSystemFeedback(String feedback);
    boolean deleteUser(String userId);
    void updateUser(String userId, String newData);
    void logSecurityEvent(String eventDetails);
}
