package service.businesslogic.api;

import domain.model.UserInfo;
import service.businesslogic.dto.UserInfoDto;

/**
 * @author Olha_Melnyk
 */
public interface UserInfoService {

    UserInfo getById(Long id);

    void save(UserInfo userInfo);

    void update(String email, UserInfoDto userInfo);
}
