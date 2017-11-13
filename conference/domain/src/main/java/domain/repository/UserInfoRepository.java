package domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.model.UserInfo;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    UserInfo findById(Long id);
}
