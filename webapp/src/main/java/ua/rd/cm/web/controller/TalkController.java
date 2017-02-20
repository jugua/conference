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
import ua.rd.cm.services.preparator.*;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.SubmitTalkDto;
import ua.rd.cm.web.controller.dto.TalkDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@RestController
@RequestMapping("/api/talk")
public class TalkController {
    public static final String TALK_NOT_FOUND = "talk_not_found";
    private static final String ORGANISER = "ORGANISER";



    private static final long MAX_SIZE = 314_572_800;
    private static final List<String> LIST_TYPE = Arrays.asList(
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "application/vnd.ms-powerpoint",
            "application/vnd.oasis.opendocument.presentation"
    );


    private static final int MAX_ORG_COMMENT_LENGTH = 1000;
    public static final int MAX_ADDITIONAL_INFO_LENGTH = 1500;
    private ModelMapper mapper;
    private UserService userService;
    private TalkService talkService;
    private TypeService typeService;
    private LanguageService languageService;
    private LevelService levelService;
    private TopicService topicService;
    private MailService mailService;
    private FileStorageService storageService;

    public static final String APPROVED = "Approved";
    public static final String DEFAULT_TALK_STATUS = "New";
    public static final String REJECTED = "Rejected";
    public static final String IN_PROGRESS = "In Progress";

    @Autowired
    public TalkController(ModelMapper mapper, UserService userService,
                          TalkService talkService,
                          TypeService typeService, LanguageService languageService,
                          LevelService levelService, TopicService topicService,
                          MailService mailService,
                          FileStorageService storageService
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
            SubmitTalkDto submitTalkDto,
            HttpServletRequest request) {

        TalkDto dto = new TalkDto(null,submitTalkDto.getTitle(),null,null,submitTalkDto.getDescription(),submitTalkDto.getTopic(),
                submitTalkDto.getType(),submitTalkDto.getLang(), submitTalkDto.getLevel(),submitTalkDto.getAddon(),
                submitTalkDto.getStatus(),null,null,null,submitTalkDto.getFile());

        MessageDto messageDto = new MessageDto();
        HttpStatus httpStatus;
        User currentUser = userService.getByEmail(request.getRemoteUser());
        Long id = null;

        if (!checkForFilledUserInfo(currentUser)) {
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (dto.getMultipartFile() != null && isAttachedFileSizeError(dto.getMultipartFile())) {
            messageDto.setError("maxSize");
            httpStatus = HttpStatus.PAYLOAD_TOO_LARGE;
        } else if (dto.getMultipartFile() != null && isAttachedFileTypeError(dto.getMultipartFile())) {
            messageDto.setError("pattern");
            httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
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

    @PreAuthorize("hasRole('ORGANISER')")
    @GetMapping("/{talkId}")
    public ResponseEntity getTalkById(@PathVariable Long talkId) {
        Talk talk = talkService.findTalkById(talkId);
        TalkDto talkDto = entityToDto(talk);
        return new ResponseEntity<>(talkDto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    public ResponseEntity actionOnTalk(@PathVariable("id") Long talkId,
                                       @RequestBody TalkDto dto,
                                       BindingResult bindingResult,
                                       HttpServletRequest request) {
        MessageDto resultMessage = new MessageDto();

        if (bindingResult.hasFieldErrors()) {
            resultMessage.setError("fields_error");
            return prepareResponse(HttpStatus.BAD_REQUEST, resultMessage);
        }
        Talk talk = talkService.findTalkById(talkId);
        if (request.isUserInRole("ORGANISER")) {
            return organiserUpdateTalk(dto, request, resultMessage, talk);
        }
        if (request.isUserInRole("SPEAKER")) {
            if (!validateStringMaxLength(dto.getAdditionalInfo(), MAX_ADDITIONAL_INFO_LENGTH)) {
                resultMessage.setError("additional_info_too_long");
                return prepareResponse(HttpStatus.PAYLOAD_TOO_LARGE, resultMessage);
            }
            if (!speakerUpdateTalk(dto, request, resultMessage, talk))
                return prepareResponse(HttpStatus.FORBIDDEN, resultMessage);
            else {
                return prepareResponse(HttpStatus.OK, resultMessage);
            }

        }
        resultMessage.setError("unauthorized");
        return prepareResponse(HttpStatus.UNAUTHORIZED, resultMessage);
    }

    private ResponseEntity organiserUpdateTalk(TalkDto dto, HttpServletRequest request, MessageDto resultMessage, Talk talk) {
        if (!validateStringMaxLength(dto.getOrganiserComment(), MAX_ORG_COMMENT_LENGTH)) {
            resultMessage.setError("comment_too_long");
            return prepareResponse(HttpStatus.PAYLOAD_TOO_LARGE, resultMessage);
        }
        if (dto.getStatusName() == null) {
            resultMessage.setError("status_is_null");
            return prepareResponse(HttpStatus.BAD_REQUEST, resultMessage);
        }
        switch (dto.getStatusName()) {
            case REJECTED: {
                if (dto.getOrganiserComment() == null || dto.getOrganiserComment().length() < 1) {
                    resultMessage.setError("empty_comment");
                    return prepareResponse(HttpStatus.BAD_REQUEST, resultMessage);
                }
                return trySetStatus(dto, talk, request);
            }
            case IN_PROGRESS: {
                return trySetStatus(dto, talk, request);
            }
            case APPROVED: {
                return trySetStatus(dto, talk, request);
            }
            default: {
                resultMessage.setError("wrong_status");
                return prepareResponse(HttpStatus.CONFLICT, resultMessage);
            }
        }
    }

    private ResponseEntity trySetStatus(TalkDto dto, Talk talk, HttpServletRequest request) {
        MessageDto message = new MessageDto();
        ResponseEntity responseEntity;
        if (talk.setStatus(TalkStatus.getStatusByName(dto.getStatusName()))) {
            talk.setOrganiser(userService.getByEmail(request.getRemoteUser()));
            talk.setOrganiserComment(dto.getOrganiserComment());
            talkService.update(talk);
            message.setResult("successfully_updated");
            responseEntity = prepareResponse(HttpStatus.OK, message);
            notifyOrganisersForOrganiserAction(talk, request);
            notifySpeaker(talk);
        } else {
            message.setError("wrong_status");
            responseEntity = prepareResponse(HttpStatus.CONFLICT, message);
        }
        return responseEntity;
    }

    private void notifySpeaker(Talk talk) {
        TalkStatus status = talk.getStatus();
        if (status.isStatusName(IN_PROGRESS) && !talk.isValidComment()) {
            return;
        }
        mailService.sendEmail(talk.getUser(), new ChangeTalkStatusSpeakerPreparator(talk));
    }

    private void notifyOrganisersForOrganiserAction(Talk talk, HttpServletRequest request) {
        String organiserEmail = request.getUserPrincipal().getName();
        User currentOrganiser = userService.getByEmail(organiserEmail);
        List<User> receivers = userService.getByRoleExceptCurrent(currentOrganiser, Role.ORGANISER);
        mailService.notifyUsers(receivers, new ChangeTalkStatusOrganiserPreparator(currentOrganiser, talk));
    }

    private boolean speakerUpdateTalk(TalkDto dto, HttpServletRequest request, MessageDto resultMessage, Talk talk) {
        User user = userService.getByEmail(request.getUserPrincipal().getName());
        if (isForbiddenToChangeTalk(user, talk)) {
            resultMessage.setError("forbidden");
            return false;
        }
        setFieldsMappedStringIntoEntity(dto, talk);
        setFieldsThatCanBeChange(dto, talk);
        talkService.update(talk);
        notifyOrganiserForSpeakerAction(talk);
        resultMessage.setResult("successfully_updated");
        return true;
    }

    private void setFieldsThatCanBeChange(TalkDto dto, Talk talk) {
        if (dto.getTitle() != null) {
            talk.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            talk.setDescription(dto.getDescription());
        }
        if (dto.getAdditionalInfo() != null) {
            talk.setAdditionalInfo(dto.getAdditionalInfo());
        }
    }

    private boolean isForbiddenToChangeTalk(User user, Talk talk) {
        return talk.getUser().getId() != user.getId() || talk.getStatus().getName().equals(REJECTED) || talk.getStatus().getName().equals(APPROVED);
    }

    private void notifyOrganiserForSpeakerAction(Talk talk) {
        if (talk.getOrganiser() != null) {
            mailService.sendEmail(talk.getOrganiser(), new ChangeTalkBySpeakerPreparator(talk));
        }
    }

    private ResponseEntity prepareResponse(HttpStatus status, MessageDto message) {
        return ResponseEntity.status(status).body(message);
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
        Talk currentTalk = dtoToEntity(dto);
        currentTalk.setStatus(TalkStatus.getStatusByName(DEFAULT_TALK_STATUS));
        currentTalk.setUser(currentUser);
        if (dto.getMultipartFile() != null) {
            currentTalk.setPathToAttachedFile(saveNewAttachedFile(dto.getMultipartFile()));
        }
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
        setFieldsMappedStringIntoEntity(dto, talk);
        return talk;
    }

    private void setFieldsMappedStringIntoEntity(TalkDto dto, Talk talk) {
        talk.setLanguage(languageService.getByName(dto.getLanguageName()));
        talk.setLevel(levelService.getByName(dto.getLevelName()));
        talk.setType(typeService.getByName(dto.getTypeName()));
        talk.setTopic(topicService.getByName(dto.getTopicName()));
    }

    private boolean validateStringMaxLength(String message, int maxSize) {
        return message == null || message.length() <= maxSize;
    }

    private boolean checkForFilledUserInfo(User currentUser) {
        UserInfo currentUserInfo = currentUser.getUserInfo();
        return !(currentUserInfo.getShortBio().isEmpty() ||
                currentUserInfo.getJobTitle().isEmpty() ||
                currentUserInfo.getCompany().isEmpty());
    }

    private boolean isAttachedFileTypeError(MultipartFile multipartFile) {
        return getTypeIfSupported(multipartFile)==null;
    }

    private boolean isAttachedFileSizeError(MultipartFile multipartFile) {
        return multipartFile.getSize()>MAX_SIZE;
    }

    private String saveNewAttachedFile(MultipartFile multipartFile) {
        try {
            return storageService.saveFile(multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTypeIfSupported(MultipartFile file) {
        if (!file.getOriginalFilename().matches("^.+(\\.(?i)(docx|ppt|pptx|pdf|odp))$")) {
            return null;
        }
        String mimeType = file.getContentType();
        if (mimeType == null || !LIST_TYPE.contains(mimeType)) {
            return null;
        }
        return mimeType;
    }

}