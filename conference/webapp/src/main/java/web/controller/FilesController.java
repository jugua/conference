package web.controller;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import domain.model.Talk;
import service.businesslogic.api.TalkService;
import service.businesslogic.dto.TalkDto;
import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.fileStorage.impl.FileStorageServiceImpl;

@Log4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class FilesController {

    private final TalkService talkService;
    private final FileStorageService storageService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/talk/{talk_id}/takeFileName", produces = "application/json")
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

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/talk/{talk_id}/deleteFile")
    public ResponseEntity delete(@PathVariable("talk_id") Long talkId) {
        TalkDto talkDto = talkService.findById(talkId);
        String filePath = talkService.getFilePath(talkDto);

        storageService.deleteFile(filePath);
        talkService.deleteFile(talkDto, true);

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

}
