package twitter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import twitter.models.Tweet;
import twitter.models.User;
import twitter.services.interfaces.ITweetService;
import twitter.services.interfaces.IUserService;

import java.util.List;

@Scope(value = "session")
@Component(value = "adminController")
public class AdminController {

    private final IUserService userService;
    private final ITweetService tweetService;

    @Autowired
    public AdminController(IUserService userService, ITweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }

    public List<User> getAllUsers() {
        return userService.getUsers(null);
    }

    public List<Tweet> getAllTweets() {
        return tweetService.getTweets();
    }

    public void deleteUser(User user) {
        userService.deleteUserById(user.getId());
    }

    public void deleteTweet(Tweet tweet) {
        tweetService.deleteTweetById(tweet.getId());
    }
}
