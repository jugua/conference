package ua.rd.cm.repository.specification.userinfo;

import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class UserInfoByPastConference implements Specification<UserInfo> {

    private String pastConference;

    public UserInfoByPastConference(String pastConference) {
        this.pastConference = pastConference;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" u.pastConference = '%s' ", pastConference);
    }

    @Override
    public boolean test(UserInfo userInfo) {
        return userInfo.getPastConference().equals(pastConference);
    }
}