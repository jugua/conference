package service.businesslogic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.model.User;
import domain.repository.TalkRepository;
import domain.repository.UserRepository;
import service.businesslogic.api.SignInService;
import service.businesslogic.dto.LoginDto;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private TalkRepository talkRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public LoginDto login(String email, String password) {
        User user = userRepository.findByEmail(email);
        String firstName = user.getFirstName();
        String role = user.getRoleNames().get(0);
        long id = user.getId();
        long conferenceCount = user.getOrganizerConferences().size();
        long talksCount = talkRepository.countByUserId(user.getId());
        LoginDto loginDto = new LoginDto(id, firstName, role, conferenceCount, talksCount);
        return loginDto;
    }

}
