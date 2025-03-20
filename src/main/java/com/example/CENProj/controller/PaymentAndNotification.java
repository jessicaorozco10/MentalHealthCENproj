// Payment & Notification Subsystem
public interface PaymentNotification {
    /**
     * Processes a payment for a user.
     * @param userId The ID of the user making the payment.
     * @param paymentDetails The payment details.
     * @return true if the payment was processed successfully, false otherwise.
     */
    boolean processPayment(String userId, String paymentDetails);

    /**
     * Updates payment information for a user.
     * @param userId The ID of the user updating their payment info.
     * @param newPaymentInfo The new payment information.
     */
    void managePaymentInfo(String userId, String newPaymentInfo);

    /**
     * Sends a notification to a user.
     * @param userId The ID of the user receiving the notification.
     * @param message The message content of the notification.
     */
    void sendNotification(String userId, String message);
}
