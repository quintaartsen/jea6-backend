package jea.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import jea.models.User;

import java.util.UUID;

public interface IUserRepository extends CrudRepository<User, UUID> {

    User findByUserName(String userName);
    User findByEmail(String email);
}
