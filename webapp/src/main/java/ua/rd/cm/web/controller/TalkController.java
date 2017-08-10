package ua.rd.cm.web.controller;

import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.rd.cm.domain.*;
import ua.rd.cm.services.*;
import ua.rd.cm.services.exception.TalkNotFoundException;
import ua.rd.cm.services.exception.TalkValidationException;
import ua.rd.cm.services.preparator.*;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.dto.SubmitTalkDto;
import ua.rd.cm.dto.TalkDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Log4j
@RestController
@RequestMapping("/api/talk")
public class TalkController {
    public static final String TALK_NOT_FOUND = "talk_not_found";
    private static final String ORGANISER = "ORGANISER";

    public static final String DEFAULT_TALK_STATUS = "New";
    private ModelMapper mapper;
    private UserService userService;
    private TalkService talkService;
    private TypeService typeService;
    private LanguageService languageService;
    private LevelService levelService;
    private TopicService topicService;
    private MailService mailService;
    private FileStorageService storageService;
    private ConferenceService conferenceService;

    public static final String APPROVED = "Approved";
    public static final String REJECTED = "Rejected";
    public static final String IN_PROGRESS = "In Progress";

    @Autowired
    public TalkController(ModelMapper mapper, UserService userService,
                          TalkService talkService,
                          TypeService typeService, LanguageService languageService,
                          LevelService levelService, TopicService topicService,
                          MailService mailService,
                          FileStorageService storageService,
                          ConferenceService conferenceService
    ) {
        this.mapper = mapper;
        this.userService = userService;
        this.talkService = talkService;
        this.languageService = languageService;
        this.topicService = topicService;
        this.mailService = mailService;
        this.typeService = typeService;
        this.levelService = levelService;
        this.storageService = storageService;
        this.conferenceService = conferenceService;
    }

    @ExceptionHandler(TalkNotFoundException.class)
    public ResponseEntity<MessageDto> handleTalkNotFound() {
        MessageDto resultMessage = new MessageDto();
        resultMessage.setError(TALK_NOT_FOUND);
        return new ResponseEntity<>(resultMessage, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity submitTalk(
            @Valid SubmitTalkDto submitTalkDto,
            HttpServletRequest request) {

        TalkDto dto = new TalkDto(null, submitTalkDto.getTitle(), null, submitTalkDto.getConferenceId(), null, null, submitTalkDto.getDescription(), submitTalkDto.getTopic(),
                submitTalkDto.getType(), submitTalkDto.getLang(), submitTalkDto.getLevel(), submitTalkDto.getAddon(),
                submitTalkDto.getStatus(), null, null, null, submitTalkDto.getFile());

        MessageDto messageDto = new MessageDto();
        HttpStatus httpStatus;
        User currentUser = userService.getByEmail(request.getRemoteUser());
        Long id = null;

        if (!checkForFilledUserInfo(currentUser)) {
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (dto.getMultipartFile() != null && storageService.isFileSizeMoreThanMaxSize(dto.getMultipartFile())) {
            messageDto.setError("maxSize");
            httpStatus = HttpStatus.PAYLOAD_TOO_LARGE;
        } else if (dto.getMultipartFile() != null && storageService.isFileTypeSupported(dto.getMultipartFile())) {
            messageDto.setError("pattern");
            httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else {
            String multipartFile = dto.getMultipartFile() != null ? saveNewAttachedFile(dto.getMultipartFile()) : null;
            Talk currentTalk = talkService.save(dto, currentUser, multipartFile);
            id = currentTalk.getId();
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
            userTalkDtoList = talkService.getTalksForOrganiser();
        } else {
            userTalkDtoList = talkService.getTalksForSpeaker(request.getRemoteUser());
        }
        return new ResponseEntity<>(userTalkDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ORGANISER')")
    @GetMapping("/{talkId}")
    public ResponseEntity getTalkById(@PathVariable Long talkId) {
        TalkDto talkDto = talkService.findById(talkId);
        return new ResponseEntity<>(talkDto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    public ResponseEntity actionOnTalk(@PathVariable("id") Long talkId,
                                       @RequestBody TalkDto dto,
                                       BindingResult bindingResult,
                                       HttpServletRequest request) {
        MessageDto message = new MessageDto();
        dto.setId(talkId);
        if (bindingResult.hasFieldErrors()) {
            message.setError("fields_error");
            return prepareResponse(HttpStatus.BAD_REQUEST, message);
        }
        try {
            if (request.isUserInRole("ORGANISER")) {
                talkService.updateAsOrganiser(dto, userService.getByEmail(request.getRemoteUser()));
            }else if (request.isUserInRole("SPEAKER")) {
                talkService.updateAsSpeaker(dto, userService.getByEmail(request.getRemoteUser()));
            } else {
                message.setError("unauthorized");
                return prepareResponse(HttpStatus.UNAUTHORIZED, message);
            }
        } catch (TalkValidationException ex) {
            message.setError(ex.getMessage());
            return prepareResponse(ex.getHttpStatus(), message);
        }
        message.setResult("successfully_updated");
        return prepareResponse(HttpStatus.OK, message);
    }

    private ResponseEntity prepareResponse(HttpStatus status, MessageDto message) {
        return ResponseEntity.status(status).body(message);
    }

    private boolean checkForFilledUserInfo(User currentUser) {
        UserInfo currentUserInfo = currentUser.getUserInfo();
        return !(currentUserInfo.getShortBio().isEmpty() ||
                currentUserInfo.getJobTitle().isEmpty() ||
                currentUserInfo.getCompany().isEmpty());
    }

    private String saveNewAttachedFile(MultipartFile multipartFile) {
        try {
            return storageService.saveFile(multipartFile);
        } catch (IOException e) {
            log.warn(e);
        }
        return null;
    }
}