package service.businesslogic.impl;

import static service.businesslogic.exception.ResourceNotFoundException.USER_INFO_NOT_FOUND;

import static java.util.Optional.ofNullable;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.model.Contact;
import domain.model.User;
import domain.model.UserInfo;
import domain.repository.ContactTypeRepository;
import domain.repository.UserInfoRepository;
import domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import service.businesslogic.api.UserInfoService;
import service.businesslogic.dto.UserInfoDto;
import service.businesslogic.exception.ResourceNotFoundException;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;
    private ModelMapper mapper;
    private ContactTypeRepository contactTypeRepository;

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
    public void update(String email, UserInfoDto userInfoDto) {
        User user = userRepository.findByEmail(email);
        user.setFirstName(userInfoDto.getFirstName());
        user.setLastName(userInfoDto.getLastName());

        UserInfo entity = userInfoDtoToEntity(userInfoDto);
        entity.setId(user.getUserInfo().getId());

        userRepository.save(user);
        userInfoRepository.save(entity);
    }

    private UserInfo userInfoDtoToEntity(UserInfoDto dto) {
        UserInfo userInfo = mapper.map(dto, UserInfo.class);

        return userInfo;
    }

    private Contact createContact(String url, String contactType) {
        return (url != null)
                ? new Contact(url, contactTypeRepository.findFirstByName(contactType))
                : null;
    }
}
