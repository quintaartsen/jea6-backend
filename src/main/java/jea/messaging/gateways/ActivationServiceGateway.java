package jea.messaging.gateways;


import jea.messaginglib.JmsConstants;
import jea.messaginglib.MessageReceiver;
import jea.messaginglib.MessageSender;
import jea.messaginglib.Serializer;
import jea.messaginglib.models.ActivationEntryVisitNotice;
import jea.messaginglib.models.UserActivatedNotice;
import jea.messaginglib.models.UserDeletionRequest;
import jea.messaginglib.models.UserRegistrationNotice;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class ActivationServiceGateway {

    private static final Logger LOGGER = Logger.getLogger(ActivationServiceGateway.class.getName());


    private MessageReceiver userDeletionConsumer;
    private MessageReceiver activatedUserConsumer;
    private MessageSender userRegistrationProducer;
    private MessageSender activationEntryProducer;

    public ActivationServiceGateway() {
        initConsumers();
        initProducers();
    }

    private void initConsumers() {
        userDeletionConsumer = new MessageReceiver(JmsConstants.USER_DELETION_TOPIC);
        userDeletionConsumer.receive(this::handleUserDeletion);

        activatedUserConsumer = new MessageReceiver(JmsConstants.ACTIVATED_USER_TOPIC);
        activatedUserConsumer.receive(this::handleActivatedUser);
    }

    private void handleUserDeletion(String message) {
        LOGGER.log(Level.INFO, "[ActivationServiceGateway] Received UserDeletionRequest: " + message);

        final UserDeletionRequest userDeletionRequest = new Serializer().FromString(message, UserDeletionRequest.class);
        onUserDeletionRequest(userDeletionRequest.getUserId());
    }

    private void handleActivatedUser(String message) {
        LOGGER.log(Level.INFO, "[ActivationServiceGateway] Received UserActivatedNotice: " + message);

        final UserActivatedNotice userActivatedNotice = new Serializer().FromString(message, UserActivatedNotice.class);
        onActivatedUserNotice(userActivatedNotice.getUserId());
    }

    private void initProducers() {
        userRegistrationProducer = new MessageSender(JmsConstants.USER_REGISTRATION_TOPIC);
        activationEntryProducer = new MessageSender(JmsConstants.ACTIVATION_ENTRY_VISIT_TOPIC);
    }

    public void sendUserRegistrationNotice(final UUID userId, final String userEmailAddress) {
        final UserRegistrationNotice userRegistrationNotice = new UserRegistrationNotice(userId, userEmailAddress);

        LOGGER.log(Level.INFO, "[ActivationServiceGateway] Sending UserRegistrationNotice Notice: " + userRegistrationNotice);
        userRegistrationProducer.send(new Serializer().ToString(userRegistrationNotice), 0);
    }

    public void sendActivationEntryVisitNotice(final UUID entryId) {
        final ActivationEntryVisitNotice activationEntryVisitNotice = new ActivationEntryVisitNotice(entryId);

        LOGGER.log(Level.INFO, "[ActivationServiceGateway] Sending ActivationEntryVisitNotice: " + activationEntryVisitNotice);
        activationEntryProducer.send(new Serializer().ToString(activationEntryVisitNotice), 0);
    }

    public abstract void onUserDeletionRequest(final UUID userId);

    public abstract void onActivatedUserNotice(final UUID userId);
}