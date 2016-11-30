package ua.rd.cm.web.controller;

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

@RestController
@RequestMapping("/api/user/current/photo")
public class PhotoController {
    private UserService userService;

    public static final String ROOT = "/";
    public static final String FOLDER = "var/lib/cm/user/photos/";
    public static final long MAX_SIZE = 2097152;

    @Autowired
    public PhotoController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity upload(PhotoDto photoDto, Principal principal) {
        MessageDto message = new MessageDto();
        HttpStatus status;
        MultipartFile file = photoDto.getFile();
        User currentUser = userService.getByEmail(principal.getName());

        if (file.isEmpty()) {
            message.setError("save");
            status = HttpStatus.BAD_REQUEST;
        } else if (file.getSize() > MAX_SIZE) {
            message.setError("maxSize");
            status = HttpStatus.PAYLOAD_TOO_LARGE;
        } else if (!file.getOriginalFilename()
                .matches("([^\\s]+(\\.(?i)(jp(e)?g|gif|png))$)")) {
            message.setError("pattern");
            status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else {
            try {
                String path = saveFile(file, currentUser);

                currentUser.setPhoto(path);
                userService.updateUserProfile(currentUser);

                message.setStatus(path);
                status = HttpStatus.OK;
            } catch (IOException e) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            } //TODO: update
        }

        return ResponseEntity.status(status).body(message);
    }

    @DeleteMapping
    public ResponseEntity delete(Principal principal){
        MessageDto message = new MessageDto();
        HttpStatus status;
        User currentUser = userService.getByEmail(principal.getName());

        try {
            File serverFile = find(getDir().getAbsolutePath(),
                    currentUser.getId().toString());

            if (serverFile != null && serverFile.delete()) {
                currentUser.setPhoto(null);
                userService.updateUserProfile(currentUser);

                status = HttpStatus.OK;
            } else {
                message.setError("delete");
                status = HttpStatus.BAD_REQUEST;
            }
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.status(status).body(message);
    }

    private File getDir() throws IOException {
        File dir= new File(ROOT + FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private String saveFile(MultipartFile file, User currentUser) throws IOException {
        File serverFile = putFileIntoDir(currentUser, file.getBytes(),
                getDir(), getFileFormat(file.getOriginalFilename()));

        return serverFile.getAbsolutePath();
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

    private String getFileFormat(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return null; //TODO: handle
        }
        return fileName.substring(index);
    }

    private String getFileName(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        }
        return fileName.substring(0, index);
    }

    private File find(String path, String fileName) {
        File f = new File(path);

        if (fileName.equalsIgnoreCase(getFileName(f.getName()))) {
            return f;
        }
        if (f.isDirectory()) {
            for (String aChild : f.list()) {
                File ff = find(path + File.separator + aChild, fileName);
                if (ff != null) return ff;
            }
        }

        return null;
    }
}