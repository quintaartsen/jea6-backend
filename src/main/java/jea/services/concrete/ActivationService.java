package jea.services.concrete;

import jea.messaging.gateways.ActivationServiceGateway;
import jea.models.User;
import jea.repositories.interfaces.IUserRepository;
import jea.services.interfaces.IActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ActivationService implements IActivationService {

    private static final Logger LOGGER = Logger.getLogger(ActivationService.class.getName());

    private final IUserRepository repository;
    private ActivationServiceGateway activationServiceGateway;

    @Autowired
    public ActivationService(final IUserRepository repository) {
        this.repository = repository;

        initGateway();
    }

    private void initGateway() {
        activationServiceGateway = new ActivationServiceGateway() {

            @Override
            public void onUserDeletionRequest(final UUID userId) {
                handleUserDeletionRequest(userId);
            }

            @Override
            public void onActivatedUserNotice(final UUID userId) {
                handleActivatedUserNotice(userId);
            }
        };
    }

    @Override
    public void sendUserRegistrationNotice(final UUID userId, final String userEmailAddress) {
        activationServiceGateway.sendUserRegistrationNotice(userId, userEmailAddress);
    }

    @Override
    public void sendActivationEntryVisitNotice(final UUID entryId) {
        activationServiceGateway.sendActivationEntryVisitNotice(entryId);
    }

    @Override
    public void handleUserDeletionRequest(final UUID userId) {
        final Optional<User> optionalUser = repository.findById(userId);

        if (!optionalUser.isPresent()) {
            LOGGER.log(Level.WARNING, "[UserService][handleUserDeletionRequest] No user exists for the given userId.");
            return;
        }

        LOGGER.log(Level.INFO, "[UserService][handleUserDeletionRequest] Deleting user with id: " + userId);
        repository.deleteById(userId);
    }

    @Override
    public void handleActivatedUserNotice(final UUID userId) {
        final Optional<User> optionalUser = repository.findById(userId);

        if (!optionalUser.isPresent()) {
            LOGGER.log(Level.WARNING, "[UserService][handleActivatedUserNotice] No user exists for the given userId.");
            return;
        }

        LOGGER.log(Level.INFO, "[UserService][handleActivatedUserNotice] Toggling IsActivated for user with id: " + userId);
        final User user = optionalUser.get();
        user.setActivated(true);
        repository.save(user);
    }
}