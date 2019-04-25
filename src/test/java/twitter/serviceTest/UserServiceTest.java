package twitter.serviceTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import twitter.models.Role;
import twitter.models.User;
import twitter.repositories.interfaces.IUserRepository;
import twitter.services.concrete.UserService;

import java.util.UUID;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository userRepo;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void ServiceTest() {
        assertNotNull(userService);
    }

    @Test
    public void addFollowingTest1() {

        User user1 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        User user2 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user1.setId(new UUID(1, 1));
        user2.setId(new UUID(2, 2));
        when(userRepo.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        when(userRepo.findById(user2.getId())).thenReturn(java.util.Optional.of(user2));

        User returnUser = userService.addFollowing(user1.getId(), user2.getId());
        Assert.assertEquals(1, returnUser.getFollowing().size());
    }

    @Test
    public void addFollowingTest2() {
        User user1 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        User user2 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user1.setId(new UUID(1, 1));
        user2.setId(new UUID(2, 2));
        when(userRepo.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        when(userRepo.findById(user2.getId())).thenReturn(java.util.Optional.of(user2));

        userService.addFollowing(user1.getId(), user2.getId());
        Assert.assertEquals(1, user1.getFollowers().size());
    }

    @Test
    public void removeFollowingTest1() {
        User user1 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        User user2 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user1.setId(new UUID(1, 1));
        user2.setId(new UUID(2, 2));
        when(userRepo.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        when(userRepo.findById(user2.getId())).thenReturn(java.util.Optional.of(user2));

        userService.addFollowing(user1.getId(), user2.getId());
        userService.removeFollowing(user1.getId(), user2.getId());
        Assert.assertEquals(0, user1.getFollowers().size());
    }

    @Test
    public void removeFollowingTest2() {
        User user1 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        User user2 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user1.setId(new UUID(1, 1));
        user2.setId(new UUID(2, 2));
        when(userRepo.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        when(userRepo.findById(user2.getId())).thenReturn(java.util.Optional.of(user2));

        userService.addFollowing(user1.getId(), user2.getId());
        userService.removeFollowing(user1.getId(), user2.getId());
        Assert.assertEquals(0, user2.getFollowing().size());
    }

    @Test
    public void updateUsernameTest1() {
        User user1 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user1.setId(new UUID(1, 1));
        when(userRepo.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));

        userService.updateUsername("test", user1);
        Assert.assertEquals("test", user1.getUserName());
    }

    @Test
    public void updateUsernameTest3() {
        User user1 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user1.setId(new UUID(1, 1));
        when(userRepo.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));

        userService.updateUsername("", user1);
        Assert.assertEquals("username", user1.getUserName());
    }
}
