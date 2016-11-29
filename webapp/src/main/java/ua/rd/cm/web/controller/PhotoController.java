package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.PhotoDto;

import java.io.*;
import java.security.Principal;
import java.util.Properties;

@RestController
@RequestMapping("/api/user/current/photo")
public class PhotoController {
    private ModelMapper mapper;
    private UserService userService;

    public static final String ROOT = "root";
    public static final String FOLDER = "folder";
    public static final long MAX_SIZE = 2097152;

    @Autowired
    public PhotoController(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity upload(PhotoDto photoDto, Principal principal) {

        MessageDto message = new MessageDto();
        HttpStatus status;

        MultipartFile file = photoDto.getFile();
        User currentUser = userService.getByEmail(principal.getName());

        if (!file.isEmpty()) {

            if (file.getSize() > MAX_SIZE) {
                message.setError("maxSize");
                status = HttpStatus.PAYLOAD_TOO_LARGE;
                return ResponseEntity.status(status).body(message);
            }
            if (file.getName().matches("([^\\s]+(\\.(?i)(jp(e)?g|gif|png))$)")) {
                message.setError("pattern");
                status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
                return ResponseEntity.status(status).body(message);
            }

            try {
                byte[] bytes = file.getBytes();
                File dir = createDir();
                File serverFile = putFileIntoDir(currentUser, bytes, dir,
                        getImageFormat(file.getOriginalFilename()));

                currentUser.setPhoto(serverFile.getAbsolutePath());
                userService.updateUserProfile(currentUser);

                message.setStatus(serverFile.getAbsolutePath());
                status = HttpStatus.OK;
                return ResponseEntity.status(status).body(message);
            } catch (IOException e) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        } else {
            message.setError("save");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(message);
        }
    }

    private File createDir() throws IOException {
        Properties propFile = getFromPropFile();
        File dir = new File(propFile.getProperty(ROOT) + propFile.getProperty(FOLDER));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private File putFileIntoDir(User currentUser, byte[] bytes, File dir, String format) throws IOException {
        File serverFile = new File(dir.getAbsolutePath()
                + File.separator + currentUser.getId() + format);
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();
        return serverFile;
    }

    private Properties getFromPropFile() throws IOException {
        Properties propFile = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("webapp/src/main/resources/path.properties");
            propFile.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return propFile;
    }

    private String getImageFormat(String fileName) {
        System.out.println("fileName=" + fileName);
        int index = fileName.lastIndexOf('.');
        System.out.println("index=" + index);
        if (index == -1) {
            return null;
        }
        String format = fileName.substring(index);
        return format;
    }

//    @DeleteMapping
//    public ResponseEntity delete(@Valid @RequestBody RegistrationDto dto,
//                            BindingResult bindingResult){
//    }

}
