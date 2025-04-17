package com.example.CENProj.service.interfaces;

import com.example.CENProj.model.User;

// Security & Administration Subsystem
public interface SecurityAdminService {
    void provideSystemFeedback(String feedback);
    boolean deleteUser(String userId);
    void updateUser(String userId, String newData);

    void forgotPassword(String userId, String newData);

    void logSecurityEvent(String eventDetails);
    User getUserByEmail(String email);
    User forgotPassword(String email);
}
