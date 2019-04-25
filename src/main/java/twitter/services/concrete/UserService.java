package twitter.services.concrete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import twitter.models.User;
import twitter.repositories.interfaces.IUserRepository;
import twitter.services.interfaces.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The type User service.
 */
@Service
public class UserService implements IUserService {

    private final IUserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Instantiates a new User service.
     *
     * @param repository the repository
     * @param bCryptPasswordEncoder
     */
    @Autowired
    public UserService(IUserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public List<User> getUsers(String username) {
        if(username == null)
            return (List<User>) repository.findAll();
        List<User> users = new ArrayList<>();
        users.add(repository.findByUserName(username));
        return users;
    }

    public User getUserById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        User user = repository.findByUserName(username);
        return user;
    }

    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);
        return user;
    }

    public void deleteUserById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void updateUsername(String username, User user) {
        if (!username.isEmpty()) {
            user.setUserName(username);
            repository.save(user);
        }
    }

    public void updateUser(User user) {
        repository.save(user);
    }

    public List<User> getUserFollowers(UUID id) {
        User user = repository.findById(id).orElse(null);
        if(user == null) return null;
        return user.getFollowers();
    }

    public List<User> getUserFollowing(UUID id) {
        User user = repository.findById(id).orElse(null);
        if(user == null) return null;
        return user.getFollowing();
    }

    public User addFollowing(UUID followingId, UUID id) {
        if(followingId != id) {
            User user = repository.findById(id).orElse(null);
            User followingUser = repository.findById(followingId).orElse(null);
            if(user != null && followingUser != null) {

                user.addFollowing(followingUser);
                followingUser.addFollower(user);

                repository.save(user);
                repository.save(followingUser);
                return user;
            }
        }
        return null;
    }

    public User removeFollowing(UUID followingId, UUID id) {
        User user = repository.findById(id).orElse(null);
        User followingUser = repository.findById(followingId).orElse(null);
        if(user != null && followingUser != null) {
            user.removeFollowing(followingUser);
            followingUser.removeFollower(user);
            repository.save(user);
            repository.save(followingUser);
            return user;
        }
        return null;
    }
}


