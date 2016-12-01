package ua.rd.cm.web.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.rd.cm.domain.*;
import ua.rd.cm.services.TalkService;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.TalkDto;

@RestController
@RequestMapping("/api/talk")
public class TalkController {

	private ModelMapper mapper;
	private UserService userService;
	private TalkService talkService;

	@Autowired
	public TalkController(ModelMapper mapper, UserService userService, TalkService talkService) {
		this.mapper = mapper;
		this.userService = userService;
		this.talkService = talkService;
	}


	@PostMapping
	public ResponseEntity submitTalk(@Valid @RequestBody TalkDto dto, Principal principal, BindingResult bindingResult) {
		HttpStatus httpStatus;
		MessageDto messageDto = new MessageDto();
		
		if(principal == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		if(bindingResult.hasFieldErrors()) {
			httpStatus = HttpStatus.BAD_REQUEST;
            messageDto.setError("fields_error");
		} else {
			httpStatus = HttpStatus.ACCEPTED;
			User currentUser = userService.getByEmail(principal.getName());
			System.out.println(dto);
			Talk currentTalk = dtoToEntity(dto);
			currentTalk.setUser(currentUser);
			System.out.println(currentTalk);
			talkService.save(currentTalk);
		}
		return ResponseEntity.status(httpStatus).body(messageDto);
	}
	
	@GetMapping
	public ResponseEntity<List<Talk>> getTalks(Principal principal) {
		if(principal == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		User currentUser = userService.getByEmail(principal.getName());
		return new ResponseEntity<>(talkService.findByUserId(currentUser.getId()), HttpStatus.ACCEPTED);
	}


	private Talk dtoToEntity(TalkDto dto) {
		Talk talk = mapper.map(dto, Talk.class);
		talk.setTime(LocalDateTime.now());
		talk.setStatus(new Status(1L, "NEW"));
		talk.setLanguage(new Language(1L, "English"));
		talk.setLevel(new Level(1L, ""));
		talk.setType(new Type(1L, ""));
		talk.setTopic(new Topic(1L, ""));
		return talk;
	}

	private boolean checkForFilledUserInfo(Principal principal) {
		User currentUser = userService.getByEmail(principal.getName());
		UserInfo currentUserInfo = currentUser.getUserInfo();
		if (currentUserInfo.getShortBio().equals("") ||
			currentUserInfo.getJobTitle().equals("") ||
			currentUserInfo.getCompany().equals("")) {
			return false;
		}
		return true;
	}
}
