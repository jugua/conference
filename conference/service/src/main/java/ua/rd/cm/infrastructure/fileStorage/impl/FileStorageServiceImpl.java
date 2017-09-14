package ua.rd.cm.infrastructure.fileStorage.impl;

import static ua.rd.cm.infrastructure.fileStorage.exception.FileValidationException.*;
import static ua.rd.cm.services.exception.ResourceNotFoundException.FILE_NOT_FOUND;

import java.io.*;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import ua.rd.cm.infrastructure.fileStorage.FileStorageService;
import ua.rd.cm.infrastructure.fileStorage.exception.FileValidationException;
import ua.rd.cm.services.exception.ResourceNotFoundException;

@Log4j
public class FileStorageServiceImpl implements FileStorageService {
    private static final int MAX_FILE_VERSION_TO_CREATE = 100;
    private static final int MAX_LENGTH_OF_VERSION_SUBSTR = 3;
    private static final long MAX_SIZE_FILE = 314_572_800;
    private static final long MAX_SIZE_PHOTO = 2097152;

    private static final List<String> SUPPORTED_PHOTO_TYPES = Arrays.asList(
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE
    );

    private static final List<String> SUPPORTED_ATTACHED_FILE_TYPES = Arrays.asList(
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
        if (!searchFile.isFile()) {
            throw new ResourceNotFoundException(FILE_NOT_FOUND);
        }
        return searchFile;
    }

    @Override
    public String saveFile(MultipartFile file, FileType fileType) throws IOException {
        if (file == null) {
            return null;
        }
        checkFileValidation(file, fileType);

        String serverFileName = getSuitableVersionedFilename(file.getOriginalFilename());
        if ("".equals(serverFileName)) {
            return "";
        } else {
            File serverFile = new File(serverFileName);
            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                stream.write(file.getBytes());
                return serverFile.getAbsolutePath();
            } catch (IOException e) {
                log.error(e);
                throw e;
            }
        }
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

    public void checkFileValidation(MultipartFile file, FileType fileType) {
        ifFileIsEmpty(file);
        isFileSizeGreaterThanMaxSize(file, fileType);
        isTypeSupported(file, fileType);
    }

    private void isTypeSupported(MultipartFile file, FileType fileType) {
        if (fileType == FileType.FILE) {
            isAttachedFileTypeSupported(file);
        } else {
            isAttachedPhotoTypeSupported(file);
        }
    }

    private void ifFileIsEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileValidationException(EMPTY);
        }
    }

    private void isAttachedFileTypeSupported(MultipartFile file) {
        if (!(file.getOriginalFilename().matches("^.+(\\.(?i)(docx|ppt|pptx|pdf|odp))$"))) {
            throw new FileValidationException(UNSUPPORTED_MEDIA_TYPE);
        }
        String mimeType = file.getContentType();

        if (mimeType == null || !SUPPORTED_ATTACHED_FILE_TYPES.contains(mimeType)) {
            throw new FileValidationException(UNSUPPORTED_MEDIA_TYPE);
        }
    }

    @Override
    public String getFileTypeIfSupported(File file) {
        String mimeType = new MimetypesFileTypeMap().getContentType(file);

        if (mimeType == null) {
            throw new FileValidationException(UNSUPPORTED_MEDIA_TYPE);
        }

        return mimeType;

    }

    @Override
    public String getPhotoTypeIfSupported(File file) {
        try {
            return isAttachedPhotoTypeSupported(new FileInputStream(file));
        } catch (IOException e) {
            log.debug(e);
            return null;
        }
    }

    private void isFileSizeGreaterThanMaxSize(MultipartFile multipartFile, FileType fileType) {
        long max_size = fileType == FileType.FILE ? MAX_SIZE_FILE : fileType == FileType.PHOTO ? MAX_SIZE_PHOTO : 0L;
        if (multipartFile.getSize() > max_size) {
            throw new FileValidationException(MAX_SIZE);
        }
    }

    private void isAttachedPhotoTypeSupported(MultipartFile file) {
        if (!file.getOriginalFilename().matches("^.+\\.(?i)(jp(e)?g|gif|png)$")) {
            throw new FileValidationException(UNSUPPORTED_MEDIA_TYPE);
        }
        try {
            isAttachedPhotoTypeSupported(file.getInputStream());
        } catch (IOException e) {
            log.debug(e);
        }
    }


    private String isAttachedPhotoTypeSupported(InputStream stream) {
        try (InputStream inputStream = new BufferedInputStream(stream)) {
            String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
            if (mimeType == null || !SUPPORTED_PHOTO_TYPES.contains(mimeType)) {
                throw new FileValidationException(UNSUPPORTED_MEDIA_TYPE);
            }
            return mimeType;
        } catch (IOException e) {
            log.debug(e);
            return null;
        }
    }

    public enum FileType {
        FILE, PHOTO
    }

}
