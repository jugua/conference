package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.UserInfoRepository;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.exception.ResourceNotFoundException;

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
        UserInfo usersInfo = userInfoRepository.findById(id);
        if (usersInfo == null) {
            throw new ResourceNotFoundException(USER_INFO_NOT_FOUND);
        }
        return usersInfo;
    }

    @Override
    @Transactional
    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    @Transactional
    public void update(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }
}
