package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.*;
import ua.rd.cm.services.*;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.TalkDto;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/talk")
public class TalkController {

	private ModelMapper mapper;
	private UserService userService;
	private TalkService talkService;
	private StatusService statusService;
	private TypeService typeService;
	private LanguageService languageService;
	private LevelService levelService;
	private TopicService topicService;

	@Autowired
	public TalkController(ModelMapper mapper, UserService userService, TalkService talkService,
						  StatusService statusService, TypeService typeService, LevelService levelService,
						  LanguageService languageService, TopicService topicService) {
		this.mapper = mapper;
		this.userService = userService;
		this.talkService = talkService;
		this.statusService = statusService;
		this.topicService = topicService;
		this.typeService = typeService;
		this.languageService = languageService;
		this.levelService = levelService;
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
		talk.setStatus(statusService.getByName("New").get(0));
		talk.setLanguage(languageService.getByName(dto.getLanguage()).get(0));
		talk.setLevel(levelService.getByName(dto.getLevel()).get(0));
		talk.setType(typeService.getByName(dto.getType()).get(0));
		talk.setTopic(topicService.getByName(dto.getTopic()).get(0));
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
