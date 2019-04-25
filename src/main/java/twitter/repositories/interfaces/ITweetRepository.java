package twitter.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import twitter.models.Tweet;
import java.util.UUID;

public interface ITweetRepository extends CrudRepository<Tweet, UUID> {
}
