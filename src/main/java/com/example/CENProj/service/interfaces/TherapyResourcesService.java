package com.example.CENProj.service.interfaces;

// Therapy & Mental Health Resources Subsystem
public interface TherapyResourcesService {
    /**
     * Starts a therapy session with a therapist.
     * @param userId The ID of the user starting the session.
     * @param therapistId The ID of the therapist conducting the session.
     */
    void startTherapySession(String userId, String therapistId);

    /**
     * Tracks the user's mood.
     * @param userId The ID of the user tracking their mood.
     * @param moodScore An integer representing the user's mood score.
     */
    void trackMood(String userId, int moodScore);

    /**
     * Provides access to a meditation guide.
     * @param userId The ID of the user accessing the guide.
     */
    void accessMeditationGuide(String userId);

    /**
     * Uses behavioral therapy tools for mental health support.
     * @param userId The ID of the user using the tools.
     */
    void useBehavioralTherapyTools(String userId);

    /**
     * Retrieves mental health resources.
     */
    void getMentalHealthResources();

    /**
     * Retrieves educational content related to mental health.
     */
    void getEducationalContent();
}
