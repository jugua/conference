package web.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.mail.MailService;

@Configuration
public class InfrastructureMockConfig {

    @Bean
    public FileStorageService mockPhotoService() {
        return mock(FileStorageService.class);
    }

    @Bean
    public MailService mockMailService() {
        return mock(MailService.class);
    }

}