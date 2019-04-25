package twitter.services.concrete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter.models.Tweet;
import twitter.models.User;
import twitter.repositories.interfaces.ITweetRepository;
import twitter.repositories.interfaces.IUserRepository;
import twitter.services.interfaces.ITweetService;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * The type Tweet service.
 */
@Service
public class TweetService implements ITweetService {

    private final ITweetRepository repository;
    private final IUserRepository userRepository;

    /**
     * Instantiates a new Tweet service.
     *
     * @param repository     the repository
     * @param userRepository the user repository
     */
    @Autowired
    public TweetService(ITweetRepository repository, IUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Tweet> getTweets() {
        return (List<Tweet>) repository.findAll();
    }

    public Tweet getTweetById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public List<Tweet> getTweetByUserId(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) return  null;
        return user.getTweets();
    }


    public Tweet createTweet(Tweet tweet, UUID id) {
        tweet.setCreatedAt(Calendar.getInstance().getTime());

        User owner = userRepository.findById(id).orElse(null);
        if(owner == null) return null ;
        owner.addTweet(tweet);
        tweet.setOwner(owner);
        repository.save(tweet);
        userRepository.save(owner);
        return tweet;
    }

    public void deleteTweetById(UUID id) {
        repository.deleteById(id);
    }

    public void addLike(UUID tweetId, UUID userId) {
        Tweet tweet = repository.findById(tweetId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if(user != null && tweet !=null){
            tweet.addLike(user);
            repository.save(tweet);
        }
    }


    public void removeLike(UUID tweetId, UUID userId) {
        Tweet tweet = repository.findById(tweetId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if(user != null && tweet !=null) {
            tweet.removeLike(user);
            repository.save(tweet);
        }
    }
}


