package ua.rd.cm.services.businesslogic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.rd.cm.domain.User;
import ua.rd.cm.dto.LoginDto;
import ua.rd.cm.repository.ConferenceRepository;
import ua.rd.cm.repository.TalkRepository;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.services.businesslogic.SignInService;

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
		List<String> roles = user.getRoleNames();
		long conferenceCount = user.getOrganizerConferences().size();
		long talksCount = talkRepository.countByUserId(user.getId());
		LoginDto loginDto = new LoginDto(firstName, roles, conferenceCount, talksCount);
		return loginDto;
	}

}
