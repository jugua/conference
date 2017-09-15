package ua.rd.cm.services.businesslogic;

import ua.rd.cm.domain.UserInfo;

/**
 * @author Olha_Melnyk
 */
public interface UserInfoService {
    UserInfo find(Long id);

    void save(UserInfo userInfo);

    void update(UserInfo userInfo);
}
