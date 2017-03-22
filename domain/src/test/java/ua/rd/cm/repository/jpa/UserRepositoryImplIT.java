package ua.rd.cm.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.User;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.UserRepository;

public class UserRepositoryImplIT extends AbstractJpaCrudRepositoryIT<User> {
    @Autowired
    private UserRepository repository;

    @Override
    protected CrudRepository<User> createRepository() {
        return repository;
    }

    @Override
    protected User createEntity() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Wick");
        user.setStatus(User.UserStatus.CONFIRMED);
        user.setEmail("John_Wick@");
        user.setPassword("ybivashka");
        return user;
    }
}