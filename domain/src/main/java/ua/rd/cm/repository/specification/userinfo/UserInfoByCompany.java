package ua.rd.cm.repository.specification.userinfo;

import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class UserInfoByCompany implements Specification<UserInfo> {

    private String company;

    public UserInfoByCompany(String company) {
        this.company = company;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" u.company = '%s' ", company);
    }

    @Override
    public boolean test(UserInfo userInfo) {
        return userInfo.getCompany().equals(company);
    }
}