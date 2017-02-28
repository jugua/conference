package ua.rd.cm.web.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.services.FileStorageService;
import ua.rd.cm.services.TalkService;
import ua.rd.cm.web.controller.dto.MessageDto;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@RestController
public class AttachedFileController {
    private TalkService talkService;
    private FileStorageService storageService;

    private static final long MAX_SIZE = 314_572_800;
    private static final List<String> LIST_TYPE = Arrays.asList(
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "application/vnd.ms-powerpoint",
            "application/vnd.oasis.opendocument.presentation"
    );

    @Autowired
    public AttachedFileController(TalkService talkService, FileStorageService storageService) {
        this.talkService = talkService;
        this.storageService = storageService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/api/talk/{talk_id}/filename",
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
    @GetMapping(value = "/api/talk/{talk_id}/file")
    public ResponseEntity takeFile(@PathVariable("talk_id") Long talkId) {
        Talk talk = talkService.findTalkById(talkId);

        File file = storageService.getFile(talk.getPathToAttachedFile());
        if (file == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        String mimeType = getTypeIfSupported(file);
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
    @PostMapping("/api/talk/{talk_id}/file")
    public ResponseEntity upload(@PathVariable("talk_id") Long talkId,
                                 @RequestPart(value = "file") MultipartFile file,
                                 HttpServletRequest request) {
        Talk talk = talkService.findTalkById(talkId);

        if (file == null || file.isEmpty()) {
            return createError(HttpStatus.BAD_REQUEST, "save");
        }
        if (file.getSize() > MAX_SIZE) {
            return createError(HttpStatus.PAYLOAD_TOO_LARGE, "maxSize");
        }
        if (getTypeIfSupported(file) == null) {
            return createError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "pattern");
        }

        String filePath = "";
        try {
            filePath = storageService.saveFile(file);
        } catch (IOException e) {
            e.printStackTrace();
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

    private ResponseEntity createResult(HttpStatus status, String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setResult(message);
        return ResponseEntity.status(status).body(messageDto);
    }


    private String getTypeIfSupported(File file) {
            String mimeType = new MimetypesFileTypeMap().getContentType(file);
            return mimeType;
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
