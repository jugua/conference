package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.*;
import ua.rd.cm.services.*;
import ua.rd.cm.services.exception.TalkNotFoundException;
import ua.rd.cm.services.preparator.ChangeTalkStatusOrganiserPreparator;
import ua.rd.cm.services.preparator.ChangeTalkStatusSpeakerPreparator;
import ua.rd.cm.services.preparator.SubmitNewTalkOrganiserPreparator;
import ua.rd.cm.services.preparator.SubmitNewTalkSpeakerPreparator;
import ua.rd.cm.web.controller.annotation.OrganiserMethod;
import ua.rd.cm.web.controller.dto.ActionDto;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.TalkDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/talk")
public class TalkController {
    public static final String TALK_NOT_FOUND = "talk_not_found";
    private static final String ORGANISER = "ORGANISER";

    private ModelMapper mapper;
    private UserService userService;
    private TalkService talkService;
    private TypeService typeService;
    private LanguageService languageService;
    private LevelService levelService;
    private TopicService topicService;
    private MailService mailService;

    public static final String DEFAULT_TALK_STATUS = "New";

    @Autowired
    public TalkController(ModelMapper mapper, UserService userService,
                          TalkService talkService,
                          TypeService typeService, LanguageService languageService,
                          LevelService levelService, TopicService topicService,
                          MailService mailService
    ) {
        this.mapper = mapper;
        this.userService = userService;
        this.talkService = talkService;
        this.languageService = languageService;
        this.topicService = topicService;
        this.mailService = mailService;
        this.typeService = typeService;
        this.levelService = levelService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity submitTalk(@Valid @RequestBody TalkDto dto, BindingResult bindingResult, HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
        HttpStatus httpStatus;

        if (bindingResult.hasFieldErrors()) {
            messageDto.setError("fields_error");
            return prepareResponse(HttpStatus.BAD_REQUEST, messageDto);
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
        return new ResponseEntity<>(messageDto, httpStatus);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<TalkDto>> getTalks(HttpServletRequest request) {
        List<TalkDto> userTalkDtoList;
        if (request.isUserInRole(ORGANISER)) {
            userTalkDtoList = getTalksForOrganiser();
        } else {
            userTalkDtoList = getTalksForSpeaker(request.getRemoteUser());
        }
        return new ResponseEntity<>(userTalkDtoList, HttpStatus.OK);
    }

    @OrganiserMethod
    @GetMapping("/{talkId}")
    public ResponseEntity getTalkById(@PathVariable Long talkId) {
        Talk talk = talkService.findTalkById(talkId);
        TalkDto talkDto = entityToDto(talk);
        return new ResponseEntity<>(talkDto, HttpStatus.OK);
    }

    @OrganiserMethod
    @PatchMapping("/{id}")
    public ResponseEntity actionOnTalk(@PathVariable("id") Long talkId,
                                       @Valid @RequestBody ActionDto dto,
                                       BindingResult bindingResult,
                                       HttpServletRequest request) {
        MessageDto resultMessage = new MessageDto();
        if (bindingResult.hasFieldErrors()) {
            if (isCommentToLong(bindingResult)) {
                resultMessage.setError("comment_too_long");
                return prepareResponse(HttpStatus.PAYLOAD_TOO_LARGE, resultMessage);
            } else {
                resultMessage.setError("fields_error");
                return prepareResponse(HttpStatus.BAD_REQUEST, resultMessage);
            }
        }

        Talk talk = talkService.findTalkById(talkId);
        String status = dto.getStatus();
        switch (status) {
            case "Rejected": {
                if (dto.getComment() == null || dto.getComment().length() < 1) {
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

    @ExceptionHandler(TalkNotFoundException.class)
    public ResponseEntity<MessageDto> handleTalkNotFound() {
        MessageDto resultMessage = new MessageDto();
        resultMessage.setError(TALK_NOT_FOUND);
        return new ResponseEntity<>(resultMessage, HttpStatus.NOT_FOUND);
    }

    private boolean isCommentToLong(BindingResult bindingResult) {
        return bindingResult.getFieldError("comment").getDefaultMessage().equals("comment_too_long");
    }

    private ResponseEntity prepareResponse(HttpStatus status, MessageDto message) {
        return ResponseEntity.status(status).body(message);
    }

    private ResponseEntity trySetStatus(ActionDto dto, Talk talk, HttpServletRequest request) {
        MessageDto message = new MessageDto();
        ResponseEntity responseEntity;
        if (talk.setStatus(TalkStatus.getStatusByName(dto.getStatus()))) {
            talk.setOrganiser(userService.getByEmail(request.getRemoteUser()));

            talk.setOrganiserComment(dto.getComment());
            talkService.update(talk);
            message.setResult("successfully_updated");
            responseEntity = prepareResponse(HttpStatus.OK, message);
            notifyOrganisers(talk, request);
            notifySpeaker(talk);
        } else {
            message.setError("wrong_status");
            responseEntity = prepareResponse(HttpStatus.CONFLICT, message);
        }
        return responseEntity;
    }

    private void notifySpeaker(Talk talk) {
        TalkStatus status = talk.getStatus();
        if (status.isStatusName("In Progress") && !talk.isValidComment()) {
            return;
        }
        mailService.sendEmail(talk.getUser(), new ChangeTalkStatusSpeakerPreparator(talk));
    }

    private void notifyOrganisers(Talk talk, HttpServletRequest request) {
        String organiserEmail = request.getUserPrincipal().getName();
        User currentOrganiser = userService.getByEmail(organiserEmail);
        List<User> receivers = userService.getByRoleExceptCurrent(currentOrganiser, Role.ORGANISER);
        mailService.notifyUsers(receivers, new ChangeTalkStatusOrganiserPreparator(currentOrganiser, talk));
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
        mailService.sendEmail(currentUser, new SubmitNewTalkSpeakerPreparator());
        return currentTalk.getId();
    }

    private TalkDto entityToDto(Talk talk) {
        TalkDto dto = mapper.map(talk, TalkDto.class);
        dto.setSpeakerFullName(talk.getUser().getFullName());
        dto.setStatusName(talk.getStatus().getName());
        dto.setDate(talk.getTime().toString());

        User organiser = talk.getOrganiser();
        if (organiser != null) {
            dto.setAssignee(organiser.getFullName());
        }
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