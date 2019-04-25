package twitter.serviceTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import twitter.models.Role;
import twitter.models.Tweet;
import twitter.models.User;
import twitter.repositories.interfaces.ITweetRepository;
import twitter.repositories.interfaces.IUserRepository;
import twitter.services.concrete.TweetService;

import java.util.UUID;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TweetServiceTest {


    @InjectMocks
    private TweetService tweetService;

    @Mock
    private ITweetRepository tweetRepo;

    @Mock
    private IUserRepository userRepo;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void ServiceTest() {
        assertNotNull(tweetService);
    }

    @Test
    public void addLikeTest1() {

        User user1 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user1.setId(new UUID(1, 1));
        when(userRepo.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        Tweet tweet = new Tweet("test", user1);
        when(tweetRepo.findById(tweet.getId())).thenReturn(java.util.Optional.of(tweet));
        tweetService.addLike(tweet.getId(), user1.getId());
        Assert.assertEquals(1, tweet.getLikes().size());
    }

    @Test
    public void addLikeTest2() {

        User user1 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user1.setId(new UUID(1, 1));
        when(userRepo.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        Tweet tweet = new Tweet("test", user1);
        when(tweetRepo.findById(tweet.getId())).thenReturn(java.util.Optional.of(tweet));
        tweetService.addLike(tweet.getId(), user1.getId());
        tweetService.addLike(tweet.getId(), user1.getId());
        Assert.assertEquals(1, tweet.getLikes().size());
    }

    @Test
    public void removeLikeTest1() {

        User user1 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user1.setId(new UUID(1, 1));
        when(userRepo.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        Tweet tweet = new Tweet("test", user1);
        tweetService.addLike(tweet.getId(), user1.getId());
        tweetService.removeLike(tweet.getId(), user1.getId());
        Assert.assertEquals(0, tweet.getLikes().size());
    }

    @Test
    public void removeLikeTest2() {

        User user1 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user1.setId(new UUID(1, 1));
        User user2 = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user2.setId(new UUID(1, 2));
        when(userRepo.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        Tweet tweet = new Tweet("test", user1);
        when(tweetRepo.findById(tweet.getId())).thenReturn(java.util.Optional.of(tweet));
        tweetService.addLike(tweet.getId(), user1.getId());
        tweetService.removeLike(tweet.getId(), user2.getId());
        Assert.assertEquals(1, tweet.getLikes().size());
    }
}