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
import java.util.ArrayList;
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
		MessageDto messageDto = new MessageDto();
		HttpStatus httpStatus;
		if(principal == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if(bindingResult.hasFieldErrors()) {
			httpStatus = HttpStatus.BAD_REQUEST;
			messageDto.setError("fields_error");
		} else if (!checkForFilledUserInfo(principal)) {
			httpStatus = HttpStatus.FORBIDDEN;
		} else {
			dto.setStatus("New");
			User currentUser = userService.getByEmail(principal.getName());
			Talk currentTalk = dtoToEntity(dto);
			currentTalk.setUser(currentUser);
			httpStatus = HttpStatus.OK;
			talkService.save(currentTalk);
		}
		return ResponseEntity.status(httpStatus).body(messageDto);
	}

	@GetMapping
	public ResponseEntity<List<TalkDto>> getTalks(Principal principal) {
		if(principal == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User currentUser = userService.getByEmail(principal.getName());
		List<Talk> userTalks = talkService.findByUserId(currentUser.getId());
		List<TalkDto> userTalkDtoList = dtoToTalkDtoList(userTalks);
		return new ResponseEntity<>(userTalkDtoList, HttpStatus.OK);
	}

	private List<TalkDto> dtoToTalkDtoList(List<Talk> userTalks) {
		List<TalkDto> list = new ArrayList<>();
		for (Talk t: userTalks) {
			list.add(entityToDto(t));
		}
		return list;
	}

	private TalkDto entityToDto(Talk t) {
		TalkDto dto = new TalkDto();
		dto.setAdditionalInfo(t.getAdditionalInfo());
		dto.setDescription(t.getDescription());
		dto.setTitle(t.getTitle());
		dto.setLanguage(t.getLanguage().getName());
		dto.setLevel(t.getLevel().getName());
		dto.setTopic(t.getTopic().getName());
		dto.setType(t.getType().getName());
		dto.setStatus(t.getStatus().getName());
		dto.setDate(t.getTime().toString());
		return dto;
	}

	private Talk dtoToEntity(TalkDto dto) {
		Talk talk = mapper.map(dto, Talk.class);
		talk.setTime(LocalDateTime.now());
		talk.setStatus(statusService.getByName(dto.getStatus()));
		talk.setLanguage(languageService.getByName(dto.getLanguage()));
		talk.setLevel(levelService.getByName(dto.getLevel()));
		talk.setType(typeService.getByName(dto.getType()));
		talk.setTopic(topicService.getByName(dto.getTopic()));
		return talk;
	}

	private boolean checkForFilledUserInfo(Principal principal) {
		User currentUser = userService.getByEmail(principal.getName());
		UserInfo currentUserInfo = currentUser.getUserInfo();
		if (currentUserInfo.getShortBio().isEmpty() ||
				currentUserInfo.getJobTitle().isEmpty() ||
				currentUserInfo.getCompany().isEmpty()) {
			return false;
		}
		return true;
	}
}