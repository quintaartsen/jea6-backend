package twitter.services.interfaces;

import twitter.models.Tweet;

import java.util.List;
import java.util.UUID;

/**
 * The interface Tweet service.
 */
public interface ITweetService {
    /**
     * Gets tweets.
     *
     * @return the tweets
     */
    List<Tweet> getTweets();

    /**
     * Gets tweet by id.
     *
     * @param id the id
     * @return the tweet by id
     */
    Tweet getTweetById(UUID id);

    /**
     * Create tweet tweet.
     *
     * @param tweet the tweet
     * @param id    the id
     * @return the tweet
     */
    Tweet createTweet(Tweet tweet, UUID id);

    /**
     * Gets tweet by user id.
     *
     * @param id the id
     * @return the tweet by user id
     */
    List<Tweet> getTweetByUserId(UUID id);

    /**
     * Delete tweet by id.
     *
     * @param id the id
     */
    void deleteTweetById(UUID id);

    /**
     * Add like.
     *
     * @param tweetId the tweet id
     * @param userId  the user id
     */
    void addLike(UUID tweetId, UUID userId);

    /**
     * Remove like.
     *
     * @param tweetId the tweet id
     * @param userId  the user id
     */
    void removeLike(UUID tweetId, UUID userId);
}
