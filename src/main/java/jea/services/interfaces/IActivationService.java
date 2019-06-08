package jea.services.interfaces;

import java.util.UUID;

/**
 * This Service handles account activation operations.
 */
public interface IActivationService {

    /**
     * Sends an UserRegistrationNotice notice to the ActivationService.
     * @param userId The id of the user that just registered.
     * @param userEmailAddress The email address of the user that just registered.
     */
    void sendUserRegistrationNotice(final UUID userId, final String userEmailAddress);

    /**
     * Sends an ActivationEntryNotice to the ActivationService.
     * @param entryId
     */
    void sendActivationEntryVisitNotice(final UUID entryId);

    /**
     * Handles incoming UserDeletionRequests by deleting a user for the given userId.
     * @param userId The id of the user that should be deleted.
     */
    void handleUserDeletionRequest(final UUID userId);

    /**
     * Handles incoming UserActivatedNotices by toggling the activated property for the given userId.
     * @param userId The id of the user that should be deleted.
     */
    void handleActivatedUserNotice(final UUID userId);
}