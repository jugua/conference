package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.UserInfo;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    UserInfo findById(Long id);
}
