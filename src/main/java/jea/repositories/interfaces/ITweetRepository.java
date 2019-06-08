package jea.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import jea.models.Tweet;
import java.util.UUID;

public interface ITweetRepository extends CrudRepository<Tweet, UUID> {
}
