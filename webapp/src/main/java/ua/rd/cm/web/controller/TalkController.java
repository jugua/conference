package ua.rd.cm.web.controller;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.services.*;
import ua.rd.cm.services.preparator.ChangeTalkStatusPreparator;
import ua.rd.cm.services.preparator.SubmitNewTalkOrganiserPreparator;
import ua.rd.cm.web.controller.dto.ActionDto;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.TalkDto;
import ua.rd.cm.web.controller.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
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
    private MailService mailService;
    private ContactTypeService contactTypeService;

    public static final String DEFAULT_TALK_STATUS = "New";

    @Autowired
    public TalkController(ModelMapper mapper, UserService userService,
                          TalkService talkService, StatusService statusService,
                          TypeService typeService, LanguageService languageService,
                          LevelService levelService, TopicService topicService,
                          MailService mailService, ContactTypeService contactTypeService
    ) {
        this.mapper = mapper;
        this.userService = userService;
        this.talkService = talkService;
        this.statusService = statusService;
        this.typeService = typeService;
        this.languageService = languageService;
        this.levelService = levelService;
        this.topicService = topicService;
        this.mailService = mailService;
        this.contactTypeService = contactTypeService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity submitTalk(@Valid @RequestBody TalkDto dto, BindingResult bindingResult, HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
        HttpStatus httpStatus;

        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fields_error");
        }

        User currentUser = userService.getByEmail(request.getRemoteUser());
        Long id = null;
        if (!checkForFilledUserInfo(currentUser)) {
            httpStatus = HttpStatus.FORBIDDEN;
        } else {
            id = saveNewTalk(dto, currentUser);
            httpStatus = HttpStatus.OK;
        }
        messageDto.setId(id);
        //return ResponseEntity.status(httpStatus).body(messageDto);
        return new ResponseEntity<>(messageDto, httpStatus);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<TalkDto>> getTalks(HttpServletRequest request) {
        List<TalkDto> userTalkDtoList;
        if (request.isUserInRole("ORGANISER")) {
            userTalkDtoList = getTalksForOrganiser();
        } else {
            userTalkDtoList = getTalksForSpeaker(request.getRemoteUser());
        }
        return new ResponseEntity<>(userTalkDtoList, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{talkId}")
    public ResponseEntity getTalkById(@PathVariable Long talkId, HttpServletRequest request) {
        if (!request.isUserInRole("ORGANISER")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }
        TalkDto talkDto = entityToDto(talkService.findTalkById(talkId));
        HttpStatus status = HttpStatus.OK;
        if (talkDto == null) {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(talkDto, status);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/user")
    public ResponseEntity getUserById(@PathVariable("id") Long userId, HttpServletRequest request) {
        if (!request.isUserInRole("ORGANISER")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }
        User user = userService.find(userId);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        UserDto userDto = new UserDto();
        userDto.setContactTypeService(contactTypeService);
        return new ResponseEntity<>(userDto.entityToDto(user), HttpStatus.ACCEPTED);
    }


    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    public ResponseEntity actionOnTalk(@PathVariable("id") Long talkId,
                                       @RequestBody ActionDto dto,
                                       BindingResult bindingResult,
                                       HttpServletRequest request) {
        MessageDto resultMessage = new MessageDto();

        if (!request.isUserInRole("ORGANISER")) {
            resultMessage.setError("unauthorized");
            return prepareResponse(HttpStatus.UNAUTHORIZED, resultMessage);
        }
        if (bindingResult.hasFieldErrors()) {
            resultMessage.setError("fields_error");
            return prepareResponse(HttpStatus.BAD_REQUEST, resultMessage);
        }
        if (dto.getComment().length() > 1000) {
            resultMessage.setError("comment_too_long");
            return prepareResponse(HttpStatus.PAYLOAD_TOO_LARGE, resultMessage);
        }
        Talk talk = talkService.findTalkById(talkId);
        if (talk == null) {
            resultMessage.setError("talk_not_found");
            return prepareResponse(HttpStatus.NOT_FOUND, resultMessage);
        }
        switch (dto.getStatus()) {
            case "Rejected": {
                if (dto.getComment().length() < 1) {
                    resultMessage.setError("empty_comment");
                    return prepareResponse(HttpStatus.BAD_REQUEST, resultMessage);
                }
                return trySetStatus(dto, talk, request);
            }
            case "In Progress": {
                return trySetStatus(dto, talk, request);
            }
            case "Approved": {
                return trySetStatus(dto, talk, request);
            }
            default: {
                resultMessage.setError("wrong_status");
                return prepareResponse(HttpStatus.CONFLICT, resultMessage);
            }
        }
    }

    private ResponseEntity prepareResponse(HttpStatus status, MessageDto message) {
        return ResponseEntity.status(status).body(message);
    }

    private ResponseEntity trySetStatus(ActionDto dto, Talk talk, HttpServletRequest request) {
        MessageDto message = new MessageDto();
        ResponseEntity responseEntity;
        if (talk.setStatus(TalkStatus.getStatusByName(dto.getStatus()))) {
            talk.setOrganiserComment(dto.getComment());
            talkService.update(talk);
            message.setResult("successfully_updated");
            responseEntity = prepareResponse(HttpStatus.OK, message);
            notifyOrganisers(talk, request);
        } else {
            message.setError("wrong_status");
            responseEntity = prepareResponse(HttpStatus.CONFLICT, message);
        }
        return responseEntity;
    }

    private void notifyOrganisers(Talk talk, HttpServletRequest request) {
        String organiserEmail = request.getUserPrincipal().getName();
        User currentOrganiser = userService.getByEmail(organiserEmail);
        List<User> receivers = userService.getByRoleExceptCurrent(currentOrganiser, Role.ORGANISER);
        mailService.notifyUsers(receivers, new ChangeTalkStatusPreparator(currentOrganiser, talk));
    }

    private List<TalkDto> getTalksForSpeaker(String userEmail) {
        User currentUser = userService.getByEmail(userEmail);

        return talkService.findByUserId(currentUser.getId())
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    private List<TalkDto> getTalksForOrganiser() {
        return talkService.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    private Long saveNewTalk(TalkDto dto, User currentUser) {
        dto.setStatusName(DEFAULT_TALK_STATUS);
        Talk currentTalk = dtoToEntity(dto);
        currentTalk.setUser(currentUser);
        talkService.save(currentTalk);
        List<User> receivers = userService.getByRole(Role.ORGANISER);
        mailService.notifyUsers(receivers, new SubmitNewTalkOrganiserPreparator(currentTalk));
        return currentTalk.getId();
    }

    private TalkDto entityToDto(Talk talk) {
        TalkDto dto = mapper.map(talk, TalkDto.class);
        dto.setSpeakerFullName(talk.getUser().getFirstName() + " " + talk.getUser().getLastName());
        dto.setStatusName(talk.getStatus().getName());
        dto.setDate(talk.getTime().toString());
        return dto;
    }

    private Talk dtoToEntity(TalkDto dto) {
        Talk talk = mapper.map(dto, Talk.class);
        talk.setTime(LocalDateTime.now());
        talk.setStatus(TalkStatus.getStatusByName(dto.getStatusName()));
        talk.setLanguage(languageService.getByName(dto.getLanguageName()));
        talk.setLevel(levelService.getByName(dto.getLevelName()));
        talk.setType(typeService.getByName(dto.getTypeName()));
        talk.setTopic(topicService.getByName(dto.getTopicName()));
        return talk;
    }

    private boolean checkForFilledUserInfo(User currentUser) {
        UserInfo currentUserInfo = currentUser.getUserInfo();
        return !(currentUserInfo.getShortBio().isEmpty() ||
                currentUserInfo.getJobTitle().isEmpty() ||
                currentUserInfo.getCompany().isEmpty());
    }
}