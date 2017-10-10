package ua.rd.cm.services.businesslogic;

import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.dto.UserDto;

/**
 * @author Olha_Melnyk
 */
public interface UserInfoService {
    UserInfo find(Long id);

    void save(UserInfo userInfo);

    void update(String email, UserDto userInfo);
}
