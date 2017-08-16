package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import ua.rd.cm.domain.UserInfo;

public interface UserInfoRepository extends CrudRepository<UserInfo,Long> {

    UserInfo findById(Long id);
}
