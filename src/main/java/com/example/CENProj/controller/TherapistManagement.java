// Therapist Management Subsystem
public interface TherapistManagement {

    /**
     * Finds therapists based on specified criteria.
     *
     * @param criteria The criteria used to filter therapists (e.g., specialty, location).
     */
    void findTherapists(String criteria);

    /**
     * Schedules an appointment with a therapist.
     *
     * @param userId      The ID of the user scheduling the appointment.
     * @param therapistId The ID of the therapist.
     * @param dateTime    The date and time of the appointment.
     * @return true if the appointment was successfully scheduled, false otherwise.
     */
    boolean scheduleAppointment(String userId, String therapistId, String dateTime);

    /**
     * Cancels a scheduled appointment.
     *
     * @param appointmentId The ID of the appointment to be canceled.
     * @return true if the appointment was successfully canceled, false otherwise.
     */
    boolean cancelAppointment(String appointmentId);

    /**
     * Sends a message to a therapist in a chat.
     *
     * @param userId      The ID of the user sending the message.
     * @param therapistId The ID of the therapist receiving the message.
     * @param message     The content of the message.
     */
    void chatWithTherapist(String userId, String therapistId, String message);

    /**
     * Reports a user for inappropriate behavior.
     *
     * @param reporterId The ID of the user submitting the report.
     * @param reportedId The ID of the user being reported.
     * @param reason     The reason for reporting the user.
     */
    void reportUser(String reporterId, String reportedId, String reason);
}
