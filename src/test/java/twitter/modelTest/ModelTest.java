package twitter.modelTest;

import org.junit.Assert;
import org.junit.Test;
import twitter.models.Role;
import twitter.models.Tweet;
import twitter.models.User;

import static org.junit.Assert.assertEquals;

public class ModelTest {

    @Test
    public void userTest() {
        User user = new User(Role.ROLE_USER, "user@mail.nl", "username", "password");
        Tweet tweet = new Tweet("Tweet message", user);

        assertEquals(user, tweet.getOwner());
    }


    @Test
    public void addFollowerTest() {
        User user = new User(Role.ROLE_USER, "testUser@mail.com", "testUser", "password");
        User user2 = new User(Role.ROLE_USER, "testUser@mail.com", "testUser", "password");

        user.addFollower(user2);
        Assert.assertEquals(1, user.getFollowers().size());
        Assert.assertEquals(0, user.getFollowing().size());
    }

    @Test
    public void addFollowingTest() {
        User user = new User(Role.ROLE_USER, "testUser@mail.com", "testUser", "password");
        User user2 = new User(Role.ROLE_USER, "testUser@mail.com", "testUser", "password");

        user.addFollowing(user2);
        Assert.assertEquals(0, user.getFollowers().size());
        Assert.assertEquals(1, user.getFollowing().size());
    }

    @Test
    public void addLike() {
        User user = new User(Role.ROLE_USER, "testUser@mail.com", "testUser", "password");
        Tweet tweet = new Tweet("Message #test #test2", user);

        tweet.addLike(user);
        Assert.assertEquals(1, tweet.getLikes().size());
    }
}
