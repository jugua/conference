package ua.rd.cm.repository;

import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

/**
 * @author Olha_Melnyk
 */
public interface UserInfoRepository {

    void saveUserInfo(UserInfo userInfo);

    void updateUserInfo(UserInfo userInfo);

    void removeUserInfo(UserInfo userInfo);

    List<UserInfo> findAll();

    List<UserInfo> findBySpecification(Specification<UserInfo> spec);
}
