package ua.rd.cm.web.controller;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.rd.cm.domain.*;
import ua.rd.cm.services.*;
import ua.rd.cm.services.exception.ResourceNotFoundException;
import ua.rd.cm.services.exception.TalkNotFoundException;
import ua.rd.cm.services.exception.TalkValidationException;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.dto.SubmitTalkDto;
import ua.rd.cm.dto.TalkDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@RestController
@RequestMapping("/api/talk")
public class TalkController {
    private static final String ORGANISER = "ORGANISER";

    public static final String DEFAULT_TALK_STATUS = "New";
    private UserService userService;
    private TalkService talkService;
    private FileStorageService storageService;

    @Autowired
    public TalkController(UserService userService, TalkService talkService, FileStorageService storageService) {
        this.userService = userService;
        this.talkService = talkService;
        this.storageService = storageService;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageDto> handleResourceNotFound(ResourceNotFoundException ex){
        MessageDto message = new MessageDto();
        message.setError(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TalkValidationException.class)
    public ResponseEntity<MessageDto> handleTalkValidationException(TalkValidationException ex) {
        MessageDto message = new MessageDto();
        message.setError(ex.getMessage());
        return new ResponseEntity<>(message, ex.getHttpStatus());
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
        } else if (dto.getMultipartFile() != null && storageService.isFileSizeGreaterThanMaxSize(dto.getMultipartFile())) {
            messageDto.setError("maxSize");
            httpStatus = HttpStatus.PAYLOAD_TOO_LARGE;
        } else if (dto.getMultipartFile() != null && storageService.getTypeIfSupported(dto.getMultipartFile())) {
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
        if (request.isUserInRole("ORGANISER")) {
            talkService.updateAsOrganiser(dto, userService.getByEmail(request.getRemoteUser()));
        } else if (request.isUserInRole("SPEAKER")) {
            talkService.updateAsSpeaker(dto, userService.getByEmail(request.getRemoteUser()));
        } else {
            message.setError("unauthorized");
            return prepareResponse(HttpStatus.UNAUTHORIZED, message);
        }
        message.setResult("successfully_updated");
        return prepareResponse(HttpStatus.OK, message);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{talk_id}/filename",
            produces = "application/json")
    public ResponseEntity takeFileName(@PathVariable("talk_id") Long talkId) {
        Talk talk = talkService.findTalkById(talkId);
        if (talk == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        File file = storageService.getFile(talk.getPathToAttachedFile());
        if (file == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Map<String, String> map = new HashMap<>();
        map.put("fileName", file.getName());
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{talk_id}/file")
    public ResponseEntity takeFile(@PathVariable("talk_id") Long talkId) {
        Talk talk = talkService.findTalkById(talkId);

        File file = storageService.getFile(talk.getPathToAttachedFile());
        if (file == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        String mimeType = storageService.getTypeIfSupported(file);
        if (mimeType == null) {
            return new ResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        try {
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType(mimeType.split("/")[0], mimeType.split("/")[1]));
            header.setContentLength(file.length());
            header.set("Content-Disposition","attachment; filename=" + new String(file.getName().getBytes(), StandardCharsets.ISO_8859_1));
            return new ResponseEntity<>(inputStreamResource, header, HttpStatus.OK);
        } catch (IOException e) {
            log.debug(e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{talk_id}/file")
    public ResponseEntity upload(@PathVariable("talk_id") Long talkId,
                                 @RequestPart(value = "file") MultipartFile file,
                                 HttpServletRequest request) {
        Talk talk = talkService.findTalkById(talkId);

        if (file == null || file.isEmpty()) {
            return createError(HttpStatus.BAD_REQUEST, "save");
        }
        if (storageService.isFileSizeGreaterThanMaxSize(file)) {
            return createError(HttpStatus.PAYLOAD_TOO_LARGE, "maxSize");
        }
        if (!storageService.getTypeIfSupported(file)) {
            return createError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "pattern");
        }

        String filePath = "";
        try {
            filePath = storageService.saveFile(file);
        } catch (IOException e) {
            log.info(e);
        }
        talk.setPathToAttachedFile(filePath);
        talkService.update(talk);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/api/talk/{talk_id}/file")
    public ResponseEntity delete(@PathVariable("talk_id") Long talkId) {
        Talk talk = talkService.findTalkById(talkId);

        if (!storageService.deleteFile(talk.getPathToAttachedFile())) {
            return createError(HttpStatus.BAD_REQUEST, "delete");
        }

        talk.setPathToAttachedFile(null);
        talkService.update(talk);

        return new ResponseEntity(HttpStatus.OK);
    }

    private ResponseEntity createError(HttpStatus status, String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setError(message);
        return ResponseEntity.status(status).body(messageDto);
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