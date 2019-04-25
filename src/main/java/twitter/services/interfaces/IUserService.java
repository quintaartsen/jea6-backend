package twitter.services.interfaces;

import twitter.models.User;

import java.util.List;
import java.util.UUID;

/**
 * The interface User service.
 */
public interface IUserService {
    /**
     * Gets users.
     *
     * @return the users
     */
    List<User> getUsers(String username);

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    User getUserById(UUID id);

    /**
     * Gets user by username.
     *
     * @param username the username
     * @return the user by username
     */
    User getUserByUsername(String username);

    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     */
    User createUser(User user);

    /**
     * Delete user by id.
     *
     * @param id the id
     */
    void deleteUserById(UUID id);

    /**
     * Update user.
     *
     * @param user the user
     */
    void updateUser(User user);

    /**
     * Update username.
     *
     * @param newUsername the new username
     * @param user        the user
     */
    void updateUsername(String newUsername, User user);

    /**
     * Remove following user.
     *
     * @param followingId the following id
     * @param id          the id
     * @return the user
     */
    User removeFollowing(UUID followingId, UUID id);

    /**
     * Add following user.
     *
     * @param followingId the following id
     * @param id          the id
     * @return the user
     */
    User addFollowing(UUID followingId, UUID id);

    /**
     * Gets user followers.
     *
     * @param id the id
     * @return the user followers
     */
    List<User> getUserFollowers(UUID id);

    /**
     * Gets user following.
     *
     * @param id the id
     * @return the user following
     */
    List<User> getUserFollowing(UUID id);
}
