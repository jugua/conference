package ua.rd.cm.services.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.web.multipart.MultipartFile;
import ua.rd.cm.services.FileStorageService;
import ua.rd.cm.services.exception.FileValidationException;
import ua.rd.cm.services.exception.ResourceNotFoundException;

import javax.activation.MimetypesFileTypeMap;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static ua.rd.cm.services.exception.FileValidationException.*;
import static ua.rd.cm.services.exception.ResourceNotFoundException.FILE_NOT_FOUND;

@Log4j
public class FileStorageServiceImpl implements FileStorageService {
    private static final int MAX_FILE_VERSION_TO_CREATE = 100;
    private static final int MAX_LENGTH_OF_VERSION_SUBSTR = 3;
    private static final long MAX_SIZE = 314_572_800;


    private static final List<String> LIST_TYPE = Arrays.asList(
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "application/vnd.ms-powerpoint",
            "application/vnd.oasis.opendocument.presentation"
    );

    @Setter
    @Getter
    private String storagePath;

    @Override
    public void deleteFile(String fileAbsolutePath) {
        if (fileAbsolutePath == null) {
            throw new FileValidationException(DELETE);
        }
        File searchFile = new File(fileAbsolutePath);
        if (!(searchFile.isFile() && searchFile.delete())) {
            throw new FileValidationException(DELETE);
        }
    }

    @Override
    public File getFile(String fileAbsolutePath) {
        if (fileAbsolutePath == null) {
            throw new ResourceNotFoundException(FILE_NOT_FOUND);
        }
        File searchFile = new File(fileAbsolutePath);
        if (!searchFile.isFile() || searchFile == null) {
            throw new ResourceNotFoundException(FILE_NOT_FOUND);
        }
        return searchFile;
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

    private String getSuitableVersionedFilename(String fileName) throws IOException {
        String extension = getExtension(fileName);
        String fileNameWithoutExtension = fileName.substring(0, fileName.length() - extension.length());
        int version = 0;
        int lastIndexOfV = fileNameWithoutExtension.lastIndexOf('v');
        if (lastIndexOfV > 0) {
            String potentialVersion = fileNameWithoutExtension.substring(lastIndexOfV + 1, fileNameWithoutExtension.length());
            if (potentialVersion.length() < MAX_LENGTH_OF_VERSION_SUBSTR && Pattern.matches("^\\d*$", potentialVersion)) {
                version = Integer.parseInt(potentialVersion);
            }
        }
        if (version > 0) {
            fileNameWithoutExtension = fileNameWithoutExtension.substring(0, lastIndexOfV - 1);
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
            } catch (IOException e) {
                log.warn(e);
            }
        }
        if (version > MAX_FILE_VERSION_TO_CREATE || "".equals(proposedFullPath))
            throw new IOException("Couldn't choose versioned filename. File couldn't be created");
        return proposedFullPath;
    }

    private String getFolderForFile(String filename) throws IOException {
        String folderPath = getStoragePath() + File.separator + Integer.toString(filename.hashCode() % 32) + File.separator;
        File folder = new File(folderPath);

        if (folder.exists() && folder.isFile()) {
            throw new IOException("Couldn't create new folder, because exist file with such name:" + folderPath);
        }
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IOException("Couldn't create new folder:" + folderPath);
        }
        return folderPath;
    }

    @Override
    public void checkFileValidation(MultipartFile file) {
        ifFileIsEmpty(file);
        isFileSizeGreaterThanMaxSize(file);
        getTypeIfSupported(file);
    }

    private void ifFileIsEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileValidationException(EMPTY);
        }
    }

    public String getTypeIfSupported(MultipartFile file) {
        if (!file.getOriginalFilename().matches("^.+(\\.(?i)(docx|ppt|pptx|pdf|odp))$")) {
            throw new FileValidationException(UNSUPPORTED_MEDIA_TYPE);
        }
        String mimeType = file.getContentType();

        if (mimeType == null || !LIST_TYPE.contains(mimeType)) {
            throw new FileValidationException(UNSUPPORTED_MEDIA_TYPE);
        }

        return mimeType;
    }

    @Override
    public String getTypeIfSupported(File file) {
        String mimeType = new MimetypesFileTypeMap().getContentType(file);

        if (mimeType == null) {
            throw new FileValidationException(UNSUPPORTED_MEDIA_TYPE);
        }

        return mimeType;

    }

    public boolean isFileSizeGreaterThanMaxSize(MultipartFile multipartFile) {
        return multipartFile.getSize() > MAX_SIZE;
    }
}
