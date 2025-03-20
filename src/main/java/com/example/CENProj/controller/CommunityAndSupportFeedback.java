// Community Support & Feedback Subsystem
public interface CommunitySupport {
    /**
     * Posts a message in the community forum.
     * @param userId The ID of the user posting in the forum.
     * @param postContent The content of the post.
     */
    void postInForum(String userId, String postContent);

    /**
     * Submits feedback from a user.
     * @param userId The ID of the user submitting feedback.
     * @param feedback The feedback content.
     */
    void submitFeedback(String userId, String feedback);

    /**
     * Retrieves frequently asked questions (FAQ).
     * @return A string containing FAQ information.
     */
    String getFAQ();

    /**
     * Reports a technical issue.
     * @param userId The ID of the user reporting the issue.
     * @param issue The description of the issue.
     */
    void reportTechnicalIssue(String userId, String issue);
}
