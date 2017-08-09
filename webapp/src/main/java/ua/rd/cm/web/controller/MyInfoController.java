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
import ua.rd.cm.domain.User;
import ua.rd.cm.services.FileStorageService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.MessageDto;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user/current/photo")
@Log4j
public class MyInfoController {
    private UserService userService;
    private FileStorageService fileStorageService;

    private static final long MAX_SIZE = 2097152;
    private static final List<String> SUPPORTED_TYPES = Arrays.asList(
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE
    );

    @Autowired
    public MyInfoController(UserService userService, FileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable("id") Long userId) {
        User user = userService.find(userId);

        File file = fileStorageService.getFile(user.getPhoto());

        String mimeType = getTypeIfSupported(file);
        if (mimeType == null) {
            return new ResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        try {
            InputStream inputStream = new FileInputStream(file);
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            HttpHeaders header = new HttpHeaders();

            header.setContentType(new MediaType(mimeType.split("/")[0], mimeType.split("/")[1]));
            header.setContentLength(file.length());

            return new ResponseEntity<>(inputStreamResource, header, HttpStatus.OK);
        } catch (IOException e) {
            log.debug(e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity upload(MultipartFile file, HttpServletRequest request) {
        User currentUser = userService.getByEmail(request.getRemoteUser());

        if (file == null || file.isEmpty()) {
            return createError(HttpStatus.BAD_REQUEST, "save");
        }
        if (file.getSize() > MAX_SIZE) {
            return createError(HttpStatus.PAYLOAD_TOO_LARGE, "maxSize");
        }
        if (getTypeIfSupported(file) == null) {
            return createError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "pattern");

        }

        String previousPhotoPath = currentUser.getPhoto();
        String newPhotoPath = "";
        try {
            newPhotoPath = fileStorageService.saveFile(file);
            if (!"".equals(newPhotoPath)) {
                fileStorageService.deleteFile(previousPhotoPath);
                currentUser.setPhoto(newPhotoPath);
                userService.updateUserProfile(currentUser);
                return createResult(HttpStatus.OK, "api/user/current/photo/" + currentUser.getId());
            }
        } catch (IOException e) {
            log.info(e);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);

    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public ResponseEntity delete(HttpServletRequest request) {
        User currentUser = userService.getByEmail(request.getRemoteUser());

        if (!fileStorageService.deleteFile(currentUser.getPhoto())) {
            return createError(HttpStatus.BAD_REQUEST, "delete");
        }

        currentUser.setPhoto(null);
        userService.updateUserProfile(currentUser);

        return new ResponseEntity(HttpStatus.OK);
    }


    public void fillForm(){

    }

    public void showForm(){

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
        try {
            return getTypeIfSupported(new FileInputStream(file));
        } catch (IOException e) {
            log.debug(e);
            return null;
        }
    }

    private String getTypeIfSupported(MultipartFile file) {
        if (!file.getOriginalFilename().matches("^.+\\.(?i)(jp(e)?g|gif|png)$")) {
            return null;
        }

        try {
            return getTypeIfSupported(file.getInputStream());
        } catch (IOException e) {
            log.debug(e);
            return null;
        }
    }

    private String getTypeIfSupported(InputStream stream) {
        try (InputStream inputStream =
                     new BufferedInputStream(stream)) {
            String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
            if (mimeType == null || !SUPPORTED_TYPES.contains(mimeType)) {
                return null;
            }

            return mimeType;
        } catch (IOException e) {
            log.debug(e);
            return null;
        }
    }
}