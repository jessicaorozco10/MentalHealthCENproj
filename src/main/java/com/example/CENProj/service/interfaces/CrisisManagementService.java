package com.example.CENProj.service.interfaces;

// Crisis Management & Emergency Support Subsystem
public interface CrisisManagementService {
    /**
     * Provides access to crisis resources for a user.
     * @param userId The ID of the user accessing resources.
     */
    void accessCrisisResources(String userId);

    /**
     * Calls an emergency hotline for immediate support.
     * @param userId The ID of the user calling the hotline.
     */
    void callEmergencyHotline(String userId);
}
