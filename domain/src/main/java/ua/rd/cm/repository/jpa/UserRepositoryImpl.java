package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.User;
import ua.rd.cm.repository.UserRepository;

@Repository
public class UserRepositoryImpl
        extends AbstractJpaCrudRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
        super("u", User.class);
    }
}
