package web.controller;

import static service.infrastructure.fileStorage.impl.FileStorageServiceImpl.FileType.PHOTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import domain.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import service.businesslogic.api.UserInfoService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.UserInfoDto;
import service.businesslogic.exception.NoSuchUserException;
import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.fileStorage.exception.FileValidationException;

@RestController
@RequestMapping("/myinfo")
@Log4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MyInfoPageController {
    private UserService userService;
    private FileStorageService fileStorageService;
    private final UserInfoService userInfoService;

    @ExceptionHandler(FileValidationException.class)
    public ResponseEntity<MessageDto> handleFileValidationException(FileValidationException ex) {
        MessageDto message = new MessageDto();
        message.setError(ex.getMessage());
        return new ResponseEntity<>(message, ex.getHttpStatus());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<UserInfoDto> getUser(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            UserInfoDto userInfoDto = userService.getUserDtoByEmail(principal.getName());
            return new ResponseEntity<>(userInfoDto, HttpStatus.ACCEPTED);
        } catch (NoSuchUserException ex) {
            log.error("Request for [myinfo] is failed: User entity for current principal is not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity updateUserInfo(@Valid @RequestBody UserInfoDto dto,
                                         Principal principal, BindingResult bindingResult) {
        HttpStatus status;
        if (bindingResult.hasFieldErrors()) {
            status = HttpStatus.BAD_REQUEST;
        } else if (principal == null) {
            status = HttpStatus.UNAUTHORIZED;
        } else {
            userInfoService.update(principal.getName(), dto);
            status = HttpStatus.OK;
        }
        return new ResponseEntity(status);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/photo/{id}")
    public ResponseEntity<InputStreamResource> getPhoto(@PathVariable("id") Long userId) {
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
    @PostMapping(value = "/photo")
    public ResponseEntity<MessageDto> uploadPhoto(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        User currentUser = userService.getByEmail(request.getRemoteUser());

        String previousPhotoPath = currentUser.getPhoto();
        String newPhotoPath = "";
        try {
            newPhotoPath = fileStorageService.saveFile(file, PHOTO);
            if (!"".equals(newPhotoPath)) {
                if (previousPhotoPath != null) {
                    fileStorageService.deleteFile(previousPhotoPath);
                }
                currentUser.setPhoto(newPhotoPath);
                userService.updateUserProfile(currentUser);

                MessageDto messageDto = new MessageDto();
                messageDto.setResult("/photo/" + currentUser.getId());
                return ResponseEntity.status(HttpStatus.OK).body(messageDto);
            }
        } catch (IOException e) {
            log.info(e);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/photo")
    public ResponseEntity deletePhoto(HttpServletRequest request) {
        User currentUser = userService.getByEmail(request.getRemoteUser());

        fileStorageService.deleteFile(currentUser.getPhoto());

        currentUser.setPhoto(null);
        userService.updateUserProfile(currentUser);

        return new ResponseEntity(HttpStatus.OK);
    }


    public void fillForm() {

    }

    public void showForm() {

    }
}