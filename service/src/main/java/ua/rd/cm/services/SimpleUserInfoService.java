package ua.rd.cm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.UserInfoRepository;
import ua.rd.cm.repository.specification.userinfo.UserInfoById;

/**
 * @author Olha_Melnyk
 */
@Service
public class SimpleUserInfoService implements UserInfoService {

    private UserInfoRepository userInfoRepository;

    @Autowired
    public SimpleUserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserInfo find(Long id) {
        return userInfoRepository.findBySpecification(new UserInfoById(id)).get(0);
    }

    @Override
    @Transactional
    public void save(UserInfo userInfo) {
        userInfoRepository.saveUserInfo(userInfo);
    }

    @Override
    @Transactional
    public void update(UserInfo userInfo) {
        userInfoRepository.updateUserInfo(userInfo);
    }
}