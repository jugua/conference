package ua.rd.cm.repository.specification.userinfo;

import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class UserInfoByJobTitle implements Specification<UserInfo> {

    private String jobTitle;

    public UserInfoByJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" u.jobTitle = '%s' ", jobTitle);
    }

    @Override
    public boolean test(UserInfo userInfo) {
        return userInfo.getJobTitle().equals(jobTitle);
    }
}