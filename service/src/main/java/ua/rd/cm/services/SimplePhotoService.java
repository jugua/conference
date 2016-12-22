package ua.rd.cm.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class SimplePhotoService implements PhotoService {

    public static final String ROOT = "/";
    public static final String FOLDER = "home/cm/user/photos";

    @Override
    public String savePhoto(MultipartFile photo, String fileNameId, String oldFileAbsolutePath) {
        if (oldFileAbsolutePath != null) {
            File searchFile = new File(oldFileAbsolutePath);
            if (!searchFile.delete()) {
                return null;
            }
        }
        File dir = getDir(ROOT + FOLDER);
        return saveFile(photo, fileNameId, dir);
    }

    @Override
    public boolean deletePhoto(String fileAbsolutePath) {
        if (fileAbsolutePath == null) {
            return false;
        }
        File searchFile = new File(fileAbsolutePath);
        return searchFile.delete();
    }

    @Override
    public File getPhoto(String fileAbsolutePath) {
        if (fileAbsolutePath == null) {
            return null;
        }
        File searchFile=new File(fileAbsolutePath);
        return searchFile;
    }


    private File getDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    private String saveFile(MultipartFile file, String fileNameId, File dir) {
        File serverFile = new File(dir.getAbsolutePath() +
                                    File.separator +
                                    fileNameId +
                                    getFileFormat(file.getOriginalFilename()));

        try (BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(serverFile))) {
            stream.write(file.getBytes());
            stream.close();

            return serverFile.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }

    private String getFileFormat(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return "";
        }
        return fileName.substring(index);
    }
}
