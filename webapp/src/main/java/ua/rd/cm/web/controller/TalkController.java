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
import java.util.stream.Collectors;

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

	private static final String DEFAULT_TALK_STATUS = "New";

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

		if (bindingResult.hasFieldErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fields_error");
		}

		User currentUser = userService.getByEmail(principal.getName());

		if (!checkForFilledUserInfo(currentUser)) {
			httpStatus = HttpStatus.FORBIDDEN;
		} else {
			saveNewTalk(dto, currentUser);
			httpStatus = HttpStatus.OK;
		}
		return ResponseEntity.status(httpStatus).body(messageDto);
	}

	@GetMapping
	public ResponseEntity<List<TalkDto>> getTalks(Principal principal) {
		if(principal == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String userEmail = principal.getName();
		List<TalkDto> userTalkDtoList = prepareTalkDtos(userEmail);
		return new ResponseEntity<>(userTalkDtoList, HttpStatus.OK);
	}

	private void saveNewTalk(TalkDto dto, User currentUser) {
		dto.setStatusName(DEFAULT_TALK_STATUS);
		Talk currentTalk = dtoToEntity(dto);
		currentTalk.setUser(currentUser);
		talkService.save(currentTalk);
	}

	private List<TalkDto> prepareTalkDtos(String userEmail) {
		User currentUser = userService.getByEmail(userEmail);

		return talkService.findByUserId(currentUser.getId())
								.stream()
								.map((talk) -> entityToDto(talk))
								.collect(Collectors.toList());
	}

	private TalkDto entityToDto(Talk talk) {
		TalkDto dto = mapper.map(talk, TalkDto.class);
		dto.setDate(talk.getTime().toString());
		return dto;
	}

	private Talk dtoToEntity(TalkDto dto) {
		Talk talk = mapper.map(dto, Talk.class);
		talk.setTime(LocalDateTime.now());
		talk.setStatus(statusService.getByName(dto.getStatusName()));
		talk.setLanguage(languageService.getByName(dto.getLanguageName()));
		talk.setLevel(levelService.getByName(dto.getLevelName()));
		talk.setType(typeService.getByName(dto.getTypeName()));
		talk.setTopic(topicService.getByName(dto.getTopicName()));
		return talk;
	}

	private boolean checkForFilledUserInfo(User currentUser) {
		UserInfo currentUserInfo = currentUser.getUserInfo();
		if (currentUserInfo.getShortBio().isEmpty() ||
				currentUserInfo.getJobTitle().isEmpty() ||
				currentUserInfo.getCompany().isEmpty()) {
			return false;
		}
		return true;
	}
}