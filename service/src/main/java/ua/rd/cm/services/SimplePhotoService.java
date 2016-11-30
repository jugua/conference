package ua.rd.cm.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class SimplePhotoService implements PhotoService {

    public static final String ROOT = "/";
    public static final String FOLDER = "var/lib/cm/user/photos/";

    @Override
    public String savePhoto(MultipartFile photo, String fileNameId) {
        try {
            File dir = getDir();
            File searchFile = find(dir, fileNameId);

            if (searchFile != null) {
                if (!searchFile.delete()) {
                    return null;
                }
            }
            return saveFile(photo, fileNameId, dir);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean deletePhoto(String fileNameId) {
        try {
            File dir = getDir();
            File searchFile = find(dir, fileNameId);
            return (searchFile != null && searchFile.delete());
        } catch (IOException e) {
            return false;
        }
    }

    private File getDir() throws IOException {
        File dir = new File(ROOT + FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private File find(File currentFile, String fileName) {
        if (fileName.equalsIgnoreCase(getFileName(currentFile.getName()))) {
            return currentFile;
        }
        if (currentFile.isDirectory()) {
            for (String childName : currentFile.list()) {
                File childFile = find(new File(currentFile.getAbsolutePath()
                        + File.separator + childName), fileName);
                if (childFile != null) {
                    return childFile;
                }
            }
        }

        return null;
    }

    private String saveFile(MultipartFile file, String fileNameId, File dir)
            throws IOException {
        File serverFile = putFileIntoDir(dir, fileNameId,
                getFileFormat(file.getOriginalFilename()), file.getBytes());

        return serverFile.getAbsolutePath();
    }

    private File putFileIntoDir(File dir,
                                String fileNameId,
                                String format,
                                byte[] bytes) throws IOException {
        File serverFile = new File(dir.getAbsolutePath() + File.separator +
                fileNameId + format);
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();
        return serverFile;
    }

    private String getFileFormat(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return null;
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
}
