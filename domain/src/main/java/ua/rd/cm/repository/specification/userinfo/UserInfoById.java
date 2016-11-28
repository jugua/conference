package ua.rd.cm.repository.specification.userinfo;

import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class UserInfoById implements Specification<UserInfo> {

    private Long id;

    public UserInfoById(Long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" u.id = '%s' ", id);
    }

    @Override
    public boolean test(UserInfo userInfo) {
        return userInfo.getId().equals(id);
    }
}