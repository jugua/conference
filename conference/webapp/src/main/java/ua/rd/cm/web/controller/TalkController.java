package ua.rd.cm.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.dto.TalkDto;
import ua.rd.cm.infrastructure.fileStorage.FileStorageService;
import ua.rd.cm.infrastructure.fileStorage.exception.FileValidationException;
import ua.rd.cm.services.businesslogic.TalkService;
import ua.rd.cm.services.businesslogic.TopicService;
import ua.rd.cm.services.businesslogic.TypeService;
import ua.rd.cm.services.businesslogic.UserService;
import ua.rd.cm.services.exception.ResourceNotFoundException;
import ua.rd.cm.services.exception.TalkValidationException;
import ua.rd.cm.infrastructure.fileStorage.impl.FileStorageServiceImpl;
import ua.rd.cm.services.resources.LanguageService;
import ua.rd.cm.services.resources.LevelService;

@Log4j
@RestController
@RequestMapping("/talk")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TalkController {
    private static final String ORGANISER = "ORGANISER";

    public static final String DEFAULT_TALK_STATUS = "New";
    private final UserService userService;
    private final TalkService talkService;
    private final FileStorageService storageService;
    private final TypeService typeService;
    private final TopicService topicService;
    private final LevelService levelService;
    private final LanguageService languageService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageDto> handleResourceNotFound(ResourceNotFoundException ex) {
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

    @ExceptionHandler(FileValidationException.class)
    public ResponseEntity<MessageDto> handleFileValidationException(FileValidationException ex) {
        MessageDto message = new MessageDto();
        message.setError(ex.getMessage());
        return new ResponseEntity<>(message, ex.getHttpStatus());
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
    public ResponseEntity updateTalk(@PathVariable("id") Long talkId,
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
    @GetMapping(value = "/{talk_id}/takeFileName",
            produces = "application/json")
    public ResponseEntity getFileName(@PathVariable("talk_id") Long talkId) {
        Talk talk = talkService.findTalkById(talkId);

        File file = storageService.getFile(talk.getPathToAttachedFile());
        Map<String, String> map = new HashMap<>();
        map.put("fileName", file.getName());
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")

    @GetMapping(value = "/{talk_id}/takeFile")
    public ResponseEntity takeFile(@PathVariable("talk_id") Long talkId) {
        TalkDto talkDto = talkService.findById(talkId);
        String filePath = talkService.getFilePath(talkDto);

        File file = storageService.getFile(filePath);

        String mimeType = storageService.getFileTypeIfSupported(file);

        try {
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType(mimeType.split("/")[0], mimeType.split("/")[1]));
            header.setContentLength(file.length());
            header.set("Content-Disposition", "attachment; filename=" + new String(file.getName().getBytes(), StandardCharsets.ISO_8859_1));
            return new ResponseEntity<>(inputStreamResource, header, HttpStatus.OK);
        } catch (IOException e) {
            log.debug(e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("isAuthenticated()")

    @PostMapping("/{talk_id}/uploadFile")
    public ResponseEntity upload(@PathVariable("talk_id") Long talkId,
                                 @RequestPart(value = "file") MultipartFile file,
                                 HttpServletRequest request) {
      String filePath = uploadFile(file);
        TalkDto talkDto = talkService.findById(talkId);
        talkService.addFile(talkDto, filePath);

        return new ResponseEntity(HttpStatus.OK);
    }

    private String uploadFile(MultipartFile file) {
        try {
            return storageService.saveFile(file, FileStorageServiceImpl.FileType.FILE);
        } catch (IOException e) {
            log.warn(e);
            return null;
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{talk_id}/deleteFile")
    public ResponseEntity delete(@PathVariable("talk_id") Long talkId) {
        TalkDto talkDto = talkService.findById(talkId);
        String filePath = talkService.getFilePath(talkDto);

        storageService.deleteFile(filePath);
        talkService.deleteFile(talkDto, true);

        return new ResponseEntity(HttpStatus.OK);
    }

    private ResponseEntity prepareResponse(HttpStatus status, MessageDto message) {
        return ResponseEntity.status(status).body(message);
    }
}