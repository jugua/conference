package ua.rd.cm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.PhotoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.PhotoDto;

import java.io.*;
import java.net.URLConnection;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user/current/photo")
public class PhotoController {
    private UserService userService;
    private PhotoService photoService;

    public static final long MAX_SIZE = 2097152;
    public static final List<String> SUPPORTED_TYPES = Arrays.asList(
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE
    );

    @Autowired
    public PhotoController(UserService userService, PhotoService photoService) {
        this.userService = userService;
        this.photoService = photoService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable("id") Long userId) {
        User user = userService.find(userId);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        File file = photoService.getPhoto(user.getPhoto());
        if (file == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        String mimeType = getTypeIfSupported(file);
        if (mimeType == null) {
            return new ResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            HttpHeaders header = new HttpHeaders();

            header.setContentType(new MediaType(mimeType.split("/")[0], mimeType.split("/")[1]));
            header.setContentLength(file.length());

            return new ResponseEntity<>(inputStreamResource, header, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity upload(PhotoDto photoDto, Principal principal) {
        MultipartFile file = photoDto.getFile();
        User currentUser = userService.getByEmail(principal.getName());

        if (file == null || file.isEmpty()) {
            return createError(HttpStatus.BAD_REQUEST, "save");
        }
        if (file.getSize() > MAX_SIZE) {
            return createError(HttpStatus.PAYLOAD_TOO_LARGE, "maxSize");
        }
        if (getTypeIfSupported(file) == null) {
            return createError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "pattern");
        }

        String path = photoService.savePhoto(file, currentUser.getId().toString(), currentUser.getPhoto());

        if (path == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        currentUser.setPhoto(path);
        userService.updateUserProfile(currentUser);

        return createAnswer(HttpStatus.OK, "api/user/current/photo/" + currentUser.getId());
    }

    @DeleteMapping
    public ResponseEntity delete(Principal principal) {
        User currentUser = userService.getByEmail(principal.getName());

        if (!photoService.deletePhoto(currentUser.getPhoto())) {
            return createError(HttpStatus.BAD_REQUEST, "delete");
        }

        currentUser.setPhoto(null);
        userService.updateUserProfile(currentUser);

        return new ResponseEntity(HttpStatus.OK);
    }

    private ResponseEntity createError(HttpStatus status, String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setError(message);
        return ResponseEntity.status(status).body(message);
    }

    private ResponseEntity createStatus(HttpStatus status, String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setStatus(message);
        return ResponseEntity.status(status).body(message);
    }

    private ResponseEntity createAnswer(HttpStatus status, String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setAnswer(message);
        return ResponseEntity.status(status).body(message);
    }

    private String getTypeIfSupported(File file) {
        try {
            return getTypeIfSupported(new FileInputStream(file));
        } catch (IOException e) {
            return null;
        }
    }

    private String getTypeIfSupported(MultipartFile file) {
        if (!file.getOriginalFilename().matches("([^\\s]+(\\.(?i)(jp(e)?g|gif|png))$)")) {
            return null;
        }

        try {
            return getTypeIfSupported(file.getInputStream());
        } catch (IOException e) {
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
            return null;
        }
    }
}