package ua.rd.cm.services.businesslogic.impl;

import static ua.rd.cm.services.exception.ResourceNotFoundException.USER_INFO_NOT_FOUND;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.UserInfoRepository;
import ua.rd.cm.services.businesslogic.UserInfoService;
import ua.rd.cm.services.exception.ResourceNotFoundException;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;

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
