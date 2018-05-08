package web.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import domain.model.Talk;
import domain.model.User;
import service.businesslogic.api.TalkService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.SubmitTalkDto;
import service.businesslogic.dto.TalkDto;
import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.fileStorage.impl.FileStorageServiceImpl;

@Log4j
@RestController
@RequestMapping("/submitTalk")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class SubmitNewTalkController {

    private final UserService userService;
    private final TalkService talkService;
    private final FileStorageService storageService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<MessageDto> submitTalk(
            @Valid SubmitTalkDto submitTalkDto,
            HttpServletRequest request) {

        TalkDto dto = new TalkDto(null, submitTalkDto.getTitle(), null, submitTalkDto.getConferenceId(),
                null, null, submitTalkDto.getDescription(), submitTalkDto.getTopic(),
                submitTalkDto.getType(), submitTalkDto.getLang(), submitTalkDto.getLevel(), submitTalkDto.getAddon(),
                submitTalkDto.getStatus(), null, null, null, submitTalkDto.getFile());

        User currentUser = userService.getByEmail(request.getRemoteUser());

        if (currentUser.getUserInfo().isNotFilled()) {
            return new ResponseEntity<>(new MessageDto(), HttpStatus.FORBIDDEN);
        }

        Talk talk = talkService.save(dto, currentUser, uploadFile(dto.getMultipartFile()));
        return ok(new MessageDto(talk.getId()));
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