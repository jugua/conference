package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.UserInfoRepository;

@Repository
public class UserInfoRepositoryImpl
        extends AbstractJpaCrudRepository<UserInfo> implements UserInfoRepository {

    public UserInfoRepositoryImpl() {
        super("u", UserInfo.class);
    }
}
