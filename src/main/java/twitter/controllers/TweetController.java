package twitter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import twitter.models.Tweet;
import twitter.services.interfaces.ITweetService;

import java.util.List;
import java.util.UUID;

@RestController
public class TweetController {

    private final ITweetService service;

    @Autowired
    public TweetController(ITweetService service) {
        this.service = service;
    }

    @GetMapping(path="tweets", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Tweet> getTweets() {
        return service.getTweets();
    }

    @GetMapping(path = "tweets/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Tweet getTweetById(@PathVariable UUID id) {
        return service.getTweetById(id);
    }

    @GetMapping(path = "users/{id}/tweets", produces = "application/json")
    public @ResponseBody
    List<Tweet> getTweetByUserId(@PathVariable UUID id) {
        return service.getTweetByUserId(id);
    }

    @PostMapping(path = "users/{id}/tweets", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTweet(@PathVariable UUID id, @RequestBody Tweet tweet) {
         service.createTweet(tweet, id);
    }

    @PostMapping(path= "tweets/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void likeTweet(@PathVariable UUID id, @PathVariable UUID userId) {
        service.addLike(id, userId);
    }

    @DeleteMapping(path= "tweets/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void unLikeTweet(@PathVariable UUID id, @PathVariable UUID userId) {
        service.removeLike(id, userId);
    }

    @DeleteMapping(path = "tweets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTweetById(@PathVariable UUID id) {
        service.deleteTweetById(id);
    }
}