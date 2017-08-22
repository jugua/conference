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
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.services.exception.FileValidationException;
import ua.rd.cm.services.impl.FileStorageServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import static ua.rd.cm.services.impl.FileStorageServiceImpl.FileType.PHOTO;

@RestController
@RequestMapping("/api/user/current/photo")
@Log4j
public class MyInfoController {
    private UserService userService;
    private FileStorageService fileStorageService;

    @Autowired
    public MyInfoController(UserService userService, FileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @ExceptionHandler(FileValidationException.class)
    public ResponseEntity<MessageDto> handleFileValidationException(FileValidationException ex) {
        MessageDto message = new MessageDto();
        message.setError(ex.getMessage());
        return new ResponseEntity<>(message, ex.getHttpStatus());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable("id") Long userId) {
        User user = userService.find(userId);
        File file = fileStorageService.getFile(user.getPhoto());

        String mimeType = fileStorageService.getPhotoTypeIfSupported(file);

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
    public ResponseEntity upload(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        User currentUser = userService.getByEmail(request.getRemoteUser());

        String previousPhotoPath = currentUser.getPhoto();
        String newPhotoPath = "";
        try {
            newPhotoPath = fileStorageService.saveFile(file, PHOTO);
            if (!"".equals(newPhotoPath)) {
                if(previousPhotoPath != null) {
                    fileStorageService.deleteFile(previousPhotoPath);
                }
                currentUser.setPhoto(newPhotoPath);
                userService.updateUserProfile(currentUser);

                MessageDto messageDto = new MessageDto();
                messageDto.setResult("api/user/current/photo/" + currentUser.getId());
                return ResponseEntity.status(HttpStatus.OK).body(messageDto);
            }
        } catch (IOException e) {
            log.info(e);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public ResponseEntity delete(HttpServletRequest request) {
        User currentUser = userService.getByEmail(request.getRemoteUser());

        fileStorageService.deleteFile(currentUser.getPhoto());

        currentUser.setPhoto(null);
        userService.updateUserProfile(currentUser);

        return new ResponseEntity(HttpStatus.OK);
    }


    public void fillForm(){

    }

    public void showForm(){

    }
}