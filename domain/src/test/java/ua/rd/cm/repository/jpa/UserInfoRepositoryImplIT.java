package ua.rd.cm.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.UserInfoRepository;

public class UserInfoRepositoryImplIT extends AbstractJpaCrudRepositoryIT<UserInfo> {
    @Autowired
    private UserInfoRepository repository;

    @Override
    protected CrudRepository<UserInfo> createRepository() {
        return repository;
    }

    @Override
    protected UserInfo createEntity() {
        return new UserInfo();
    }
}