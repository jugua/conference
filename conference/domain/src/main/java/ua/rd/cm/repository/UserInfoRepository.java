package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.UserInfo;

@RepositoryRestResource(exported = false)
@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    UserInfo findById(Long id);
}
