package web.controller;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import domain.model.Talk;
import domain.model.TalkStatus;
import service.businesslogic.api.CommentService;
import service.businesslogic.api.TalkService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.CommentDto;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.Submission;
import service.businesslogic.dto.TalkDto;
import service.businesslogic.dto.TalkStatusDto;
import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.fileStorage.impl.FileStorageServiceImpl;

@Log4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TalksController {

    protected static final String DEFAULT_TALK_STATUS = "New";
    private final UserService userService;
    private final TalkService talkService;
    private final FileStorageService storageService;
    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talks/{talkId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("talkId") long talkId) {
        List<CommentDto> comments = commentService.getAllByTalkId(talkId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/talks/{talkId}/comments")
    public ResponseEntity<MessageDto> saveComment(@PathVariable("talkId") long talkId,
                                                  @RequestBody CommentDto commentDto, BindingResult binding) {
        if (binding.hasFieldErrors()) {
            return badRequest().body(new MessageDto("fields_error"));
        }
        commentService.save(commentDto);
        MessageDto messageDto = new MessageDto();
        messageDto.setResult("successfully_updated");
        return ok(messageDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/talks/{talkId}/comments/{commentId}")
    public ResponseEntity<MessageDto> updateComment(@PathVariable("talkId") long talkId,
                                                    @PathVariable("commentId") long commentId,
                                                    @RequestBody CommentDto commentDto, BindingResult binding) {
        if (binding.hasFieldErrors()) {
            return badRequest().body(new MessageDto("fields_error"));
        }
        if (commentService.findById(commentId) == null) {
            return badRequest().body(new MessageDto("fields_error"));
        }
        commentDto.setId(commentId);
        commentService.update(commentDto);
        MessageDto messageDto = new MessageDto();
        messageDto.setResult("successfully_updated");
        return ok(messageDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talk/talksTitles")
    public ResponseEntity<List<String>> getTalksTitles() {
        List<String> talksTitles = talkService.findAll().stream().map(Talk::getTitle).collect(Collectors.toList());
        return ok(talksTitles);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talk/talksStatus")
    public ResponseEntity<List<TalkStatusDto>> getTalksStatus() {
        List<TalkStatusDto> talksStatus = Arrays.asList(TalkStatus.values())
                .stream()
                .map(m -> new TalkStatusDto(Long.valueOf(m.ordinal()), m.getName()))
                .collect(Collectors.toList());
        return ok(talksStatus);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talk")
    public ResponseEntity<List<Submission>> getSumbissions(HttpServletRequest request) {

        List<Submission> userTalkDtoList = talkService.getSubmissions(request.getRemoteUser());
        log.debug(userTalkDtoList);

        return ok(userTalkDtoList);
    }

    @GetMapping("/talk/{talkId}")
    public ResponseEntity<TalkDto> getTalkById(@PathVariable Long talkId, HttpServletRequest request) {
        String userMail = request.getRemoteUser();
        boolean isTalkOrganiser = userService.isTalkOrganiser(userMail, talkId);
        if (isTalkOrganiser) {
            TalkDto talkDto = talkService.findById(talkId);
            return ok(talkDto);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/talk")
    public ResponseEntity<MessageDto> updateTalkStatus(@RequestBody TalkStatusDto dto,
                                                       BindingResult bindingResult,
                                                       HttpServletRequest request) {
        String userMail = request.getRemoteUser();
        if (bindingResult.hasFieldErrors()) {
            return badRequest().body(new MessageDto("fields_error"));
        }
        if (userService.isTalkOrganiser(userMail, dto.getId())) {
            talkService.updateStatus(dto);
            MessageDto messageDto = new MessageDto();
            messageDto.setResult("successfully_updated");
            return ok(messageDto);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/talk/{id}")
    public ResponseEntity<MessageDto> updateTalk(@PathVariable("id") Long talkId,
                                                 @RequestBody TalkDto dto,
                                                 BindingResult bindingResult,
                                                 HttpServletRequest request) {
        dto.setId(talkId);
        if (bindingResult.hasFieldErrors()) {
            return badRequest().body(new MessageDto("fields_error"));
        }
        if (request.isUserInRole("ORGANISER")) {
            talkService.updateAsOrganiser(dto, userService.getByEmail(request.getRemoteUser()));
        } else if (request.isUserInRole("SPEAKER")) {
            talkService.updateAsSpeaker(dto, userService.getByEmail(request.getRemoteUser()));
        } else {
            return new ResponseEntity<>(new MessageDto("unauthorized"), HttpStatus.UNAUTHORIZED);
        }
        MessageDto messageDto = new MessageDto();
        messageDto.setResult("successfully_updated");
        return ok(messageDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/talk/{talk_id}/takeFileName",
            produces = "application/json")
    public ResponseEntity<Map<String, String>> getFileName(@PathVariable("talk_id") Long talkId) {
        Talk talk = talkService.findTalkById(talkId);

        File file = storageService.getFile(talk.getPathToAttachedFile());
        Map<String, String> map = new HashMap<>();
        map.put("fileName", file.getName());
        return ok(map);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/talk/{talk_id}/takeFile")
    public ResponseEntity<InputStreamResource> takeFile(@PathVariable("talk_id") Long talkId) {
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
            return badRequest().build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/talk/{talk_id}/uploadFile")
    public ResponseEntity upload(@PathVariable("talk_id") Long talkId,
                                 @RequestPart(value = "file") MultipartFile file,
                                 HttpServletRequest request) {
        String filePath = uploadFile(file);
        TalkDto talkDto = talkService.findById(talkId);
        talkService.addFile(talkDto, filePath);

        return ok().build();
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
    @DeleteMapping("/talk/{talk_id}/deleteFile")
    public ResponseEntity delete(@PathVariable("talk_id") Long talkId) {
        TalkDto talkDto = talkService.findById(talkId);
        String filePath = talkService.getFilePath(talkDto);

        storageService.deleteFile(filePath);
        talkService.deleteFile(talkDto, true);

        return ok().build();
    }

}
