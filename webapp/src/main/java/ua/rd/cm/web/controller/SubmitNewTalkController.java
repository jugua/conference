package ua.rd.cm.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.dto.SubmitTalkDto;
import ua.rd.cm.dto.TalkDto;
import ua.rd.cm.services.*;
import ua.rd.cm.services.impl.FileStorageServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Log4j
@RestController
@RequestMapping("/api/")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class SubmitNewTalkController {
    private final TypeService typeService;
    private final TopicService topicService;
    private final LevelService levelService;
    private final LanguageService languageService;
    private final UserService userService;
    private final TalkService talkService;
    private final FileStorageService storageService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("type")
    public ResponseEntity getTypes() {
        return new ResponseEntity<>(typeService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("topic")
    public ResponseEntity getTopics() {
        return new ResponseEntity<>(topicService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("level")
    public ResponseEntity getLevels() {
        return new ResponseEntity<>(levelService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("lang")
    public ResponseEntity getLanguages() {
        return new ResponseEntity<>(languageService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/talk")
    public ResponseEntity submitTalk(
            @Valid SubmitTalkDto submitTalkDto,
            HttpServletRequest request) {

        TalkDto dto = new TalkDto(null, submitTalkDto.getTitle(), null, submitTalkDto.getConferenceId(), null, null, submitTalkDto.getDescription(), submitTalkDto.getTopic(),
                submitTalkDto.getType(), submitTalkDto.getLang(), submitTalkDto.getLevel(), submitTalkDto.getAddon(),
                submitTalkDto.getStatus(), null, null, null, submitTalkDto.getFile());

        MessageDto messageDto = new MessageDto();
        User currentUser = userService.getByEmail(request.getRemoteUser());

        if (userInfoNotFilled(currentUser)) {
            return new ResponseEntity<>(messageDto, HttpStatus.FORBIDDEN);
        }

        Talk talk = talkService.save(dto, currentUser, uploadFile(dto.getMultipartFile()));
        messageDto.setId(talk.getId());
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    private boolean userInfoNotFilled(User currentUser) {
        UserInfo currentUserInfo = currentUser.getUserInfo();
        return currentUserInfo.getShortBio().isEmpty() ||
                currentUserInfo.getJobTitle().isEmpty() ||
                currentUserInfo.getCompany().isEmpty();
    }

    private String uploadFile(MultipartFile file) {
        try {
            return storageService.saveFile(file, FileStorageServiceImpl.FileType.FILE);
        } catch (IOException e) {
            log.warn(e);
            return null;
        }
    }
}