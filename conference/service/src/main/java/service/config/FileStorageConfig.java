package service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.fileStorage.impl.FileStorageServiceImpl;

@Configuration
public class FileStorageConfig {

    @Bean
    public FileStorageService getFileStorageService(Environment environment) {
        FileStorageServiceImpl fileStorageService = new FileStorageServiceImpl();
        fileStorageService.setStoragePath(environment.getProperty("fileStorage.path"));
        return fileStorageService;
    }

}
