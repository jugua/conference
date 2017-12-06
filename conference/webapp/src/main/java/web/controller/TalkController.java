package web.controller;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import domain.model.Talk;
import domain.model.TalkStatus;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import service.businesslogic.api.CommentService;
import service.businesslogic.api.LanguageService;
import service.businesslogic.api.LevelService;
import service.businesslogic.api.TalkService;
import service.businesslogic.api.TopicService;
import service.businesslogic.api.TypeService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.CommentDto;
import service.businesslogic.dto.Submission;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.TalkDto;
import service.businesslogic.exception.ResourceNotFoundException;
import service.businesslogic.exception.TalkValidationException;
import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.fileStorage.exception.FileValidationException;
import service.infrastructure.fileStorage.impl.FileStorageServiceImpl;

@Log4j
@RestController
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
    private CommentService commentService;

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
    @GetMapping("/talks/{talkId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("talkId")long talkId){
		List<CommentDto> comments = commentService.getAllByTalkId(talkId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/talks/{talkId}/comments")
    public ResponseEntity<MessageDto> saveComment(@PathVariable("talkId")long talkId,
    		@RequestBody CommentDto commentDto, BindingResult binding){
    	MessageDto message = new MessageDto();
    	if(binding.hasFieldErrors()) {
    		message.setError("fields_error");
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}
		commentService.save(commentDto);
		message.setResult("successfully_updated");
		return new ResponseEntity<>(message,HttpStatus.OK);
    }
    
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/talks/{talkId}/comments/{commentId}")
    public ResponseEntity<MessageDto> updateComment(@PathVariable("talkId")long talkId, @PathVariable("commentId")long commentId,
    		@RequestBody CommentDto commentDto, BindingResult binding){
    	MessageDto message = new MessageDto();
    	if(binding.hasFieldErrors()) {
    		message.setError("fields_error");
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}
    	if(commentService.findById(commentId)==null) {
    		message.setError("fields_error");
    		return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    	}
    	commentDto.setId(commentId);
		commentService.update(commentDto);
		message.setResult("successfully_updated");
		return new ResponseEntity<>(message,HttpStatus.OK);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talk/talksTitles")
    public ResponseEntity<List<String>> getTalksTitles() {
        List<String> talksTitles = talkService.findAll().stream().map(m -> m.getTitle()).collect(Collectors.toList());
        return new ResponseEntity<>(talksTitles, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talk/talksStatus")
    public ResponseEntity<List<String>> getTalksStatus() {
        List<String> talksStatus = Arrays.asList(TalkStatus.values()).stream().map(m -> m.getName()).collect(Collectors.toList());
        return new ResponseEntity<>(talksStatus, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talk")
    public ResponseEntity<List<Submission>> getTalks(HttpServletRequest request) {
        List<Submission> userTalkDtoList = talkService.getTalksForSpeaker(request.getRemoteUser());
        System.out.println(userTalkDtoList);
        
        return new ResponseEntity<>(userTalkDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ORGANISER')")
    @GetMapping("/talk/{talkId}")
    public ResponseEntity<TalkDto> getTalkById(@PathVariable Long talkId) {
        TalkDto talkDto = talkService.findById(talkId);
        return new ResponseEntity<>(talkDto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/talk/{id}")
    public ResponseEntity<MessageDto> updateTalk(@PathVariable("id") Long talkId,
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
    @GetMapping(value = "/talk/{talk_id}/takeFileName",
            produces = "application/json")
    public ResponseEntity<Map<String,String>> getFileName(@PathVariable("talk_id") Long talkId) {
        Talk talk = talkService.findTalkById(talkId);

        File file = storageService.getFile(talk.getPathToAttachedFile());
        Map<String, String> map = new HashMap<>();
        map.put("fileName", file.getName());
        return new ResponseEntity(map, HttpStatus.OK);
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
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
    @DeleteMapping("/talk/{talk_id}/deleteFile")
    public ResponseEntity delete(@PathVariable("talk_id") Long talkId) {
        TalkDto talkDto = talkService.findById(talkId);
        String filePath = talkService.getFilePath(talkDto);

        storageService.deleteFile(filePath);
        talkService.deleteFile(talkDto, true);

        return new ResponseEntity(HttpStatus.OK);
    }

    private ResponseEntity<MessageDto> prepareResponse(HttpStatus status, MessageDto message) {
        return ResponseEntity.status(status).body(message);
    }
}