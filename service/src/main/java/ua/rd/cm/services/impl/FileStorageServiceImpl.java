package ua.rd.cm.services.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.web.multipart.MultipartFile;
import ua.rd.cm.services.FileStorageService;

import java.io.*;
import java.util.regex.Pattern;

@Log4j
public class FileStorageServiceImpl implements FileStorageService {
    private static final int MAX_FILE_VERSION_TO_CREATE = 100;
    private static final int MAX_LENGTH_OF_VERSION_SUBSTR = 3;

    private static class FolderCreationException extends IOException {
    }

    private static class FileCreationException extends IOException {
    }

    @Setter
    @Getter
    private String storagePath;

    @Override
    public boolean deleteFile(String fileAbsolutePath) {
        if (fileAbsolutePath == null) {
            return false;
        }
        File searchFile = new File(fileAbsolutePath);
        return searchFile.isFile() && searchFile.delete();
    }

    @Override
    public File getFile(String fileAbsolutePath) {
        if (fileAbsolutePath == null) {
            return null;
        }
        File searchFile = new File(fileAbsolutePath);
        return searchFile.isFile() ? searchFile : null;
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String serverFileName = getSuitableVersionedFilename(file.getOriginalFilename());
        if (!"".equals(serverFileName)) {
            File serverFile = new File(serverFileName);
            try (BufferedOutputStream stream =
                         new BufferedOutputStream(new FileOutputStream(serverFile))) {
                stream.write(file.getBytes());
                return serverFile.getAbsolutePath();
            } catch (IOException e) {
                log.error(e);
                throw e;
            }
        }
        return "";
    }

    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return "";
        }
        return fileName.substring(index);
    }

    private String getSuitableVersionedFilename(String fileName) throws FileCreationException {
        String extension = getExtension(fileName);
        String fileNameWithoutExtension = fileName.substring(0, fileName.length() - extension.length());
        int version = 0;
        int lastIndexOfV = fileNameWithoutExtension.lastIndexOf('v');
        if (lastIndexOfV > 0) {
            String potentialVersion = fileNameWithoutExtension.substring(lastIndexOfV + 1, fileNameWithoutExtension.length());
            if (potentialVersion.length() > MAX_LENGTH_OF_VERSION_SUBSTR && Pattern.matches("^\\d*$", potentialVersion)) {
                version = Integer.parseInt(potentialVersion);
            }
        }
        if (version > 0) {
            fileNameWithoutExtension = fileNameWithoutExtension.substring(0, lastIndexOfV);
        }

        String proposedFullPath = "";
        for (; version <= MAX_FILE_VERSION_TO_CREATE; version++) {
            String proposedFileName = fileNameWithoutExtension + (version > 0 ? " v" + version : "") + extension;
            try {
                proposedFullPath = getFolderForFile(proposedFileName) + proposedFileName;
                File proposedFile;
                proposedFile = new File(proposedFullPath);
                if (!proposedFile.exists())
                    break;
            } catch (FolderCreationException e) {
                log.warn(e);
            }
        }
        if (version > MAX_FILE_VERSION_TO_CREATE || "".equals(proposedFullPath))
            throw new FileCreationException();
        return proposedFullPath;
    }

    private String getFolderForFile(String filename) throws FolderCreationException {
        String folderPath = getStoragePath() + File.separator + Integer.toString(filename.hashCode() % 32) + File.separator;
        File folder = new File(folderPath);

        if (folder.exists()) {
            if (folder.isFile())
                throw new FolderCreationException();
        } else {
            if (!folder.mkdirs())
                throw new FolderCreationException();
        }
        return folderPath ;
    }

}
