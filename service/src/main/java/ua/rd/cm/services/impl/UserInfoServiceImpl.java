package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.UserInfoRepository;
import ua.rd.cm.repository.specification.userinfo.UserInfoById;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.exception.ResourceNotFoundException;

import java.util.List;

import static ua.rd.cm.services.exception.ResourceNotFoundException.USER_INFO_NOT_FOUND;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserInfo find(Long id) {
        List<UserInfo> usersInfo = userInfoRepository.findBySpecification(new UserInfoById(id));
        if (usersInfo.isEmpty()) {
            throw new ResourceNotFoundException(USER_INFO_NOT_FOUND);
        }
        return usersInfo.get(0);
    }

    @Override
    @Transactional
    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    @Transactional
    public void update(UserInfo userInfo) {
        userInfoRepository.update(userInfo);
    }
}
