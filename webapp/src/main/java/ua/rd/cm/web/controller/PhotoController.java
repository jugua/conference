package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.PhotoDto;
import ua.rd.cm.web.controller.dto.RegistrationDto;
import ua.rd.cm.web.controller.dto.UserDto;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;

@RestController
@RequestMapping("/api/user/current/photo")
public class PhotoController {
    private ModelMapper mapper;
    private UserService userService;

    @Autowired
    public PhotoController(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity upload(PhotoDto photoDto) {

        MultipartFile file = photoDto.getFile();

        if (!file.isEmpty()) {
            System.out.println("lol");

            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = "C://";

                File dir = new File(rootPath + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + "photo.jpg");
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                //200 { "answer": <url>} - on success upload or update photo
                return new ResponseEntity(HttpStatus.OK);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

//    @DeleteMapping
//    public ResponseEntity delete(@Valid @RequestBody RegistrationDto dto,
//                            BindingResult bindingResult){
//    }

    /*

     Method : POST     -  saves current user photo
     Headers: token : <token> - for Node.js only
     Body:  file

    Answer
      400  { "error": "save"} - any error of saving file on server
     401 { "error": <error>} in case no token in headers
     403 {error} - JSON with any other type of db or server error                description
     413 { "error": "maxSize"} - if file size more then 2mb
     415 { "error": "pattern"} - filetype not jpe(g), gif, png
     200 { "answer": <url>} - on success upload or update photo (current          user photo field have link to new photo) ; url   - relative path to      new image saved in db

    Error types:
     "no-current-user" - invalid  token
     "no-token"  - no token (not authorized)


    Method : DELETE    -  delete current user photo
     Headers: token : <token> - for Node.js only

    Answer
     400  { "error": "delete"} - any error of deleting file on server
     401 { "error": <error>} in case no token in headers
     403 {error} - JSON with any other type of db or server error                description
     200 { } - on success delete photo

    Error types:
     "no-current-user" - invalid  token
     "no-token"  - no token (not authorized)

     */
}
