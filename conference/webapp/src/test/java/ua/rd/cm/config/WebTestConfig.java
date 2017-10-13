package ua.rd.cm.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ua.rd.cm.infrastructure.fileStorage.FileStorageService;
import ua.rd.cm.infrastructure.mail.MailService;
import ua.rd.cm.services.businesslogic.*;
import ua.rd.cm.services.businesslogic.impl.VerificationTokenService;
import ua.rd.cm.services.resources.LanguageService;
import ua.rd.cm.services.resources.LevelService;

@Configuration
public class WebTestConfig {
    @Bean
    public ConferenceService mockConferenceService() {
        return mock(ConferenceService.class);
    }

    @Bean
    public UserService mockUserService() {
        return mock(UserService.class);
    }

    @Bean
    public UserInfoService mockUserInfoService() {
        return mock(UserInfoService.class);
    }

    @Bean
    public ContactTypeService mockContactTypeService() {
        return mock(ContactTypeService.class);
    }

    @Bean
    public TalkService mockTalkService() {
        return mock(TalkService.class);
    }

    @Bean
    public TopicService mockTopicService() {
        return mock(TopicService.class);
    }

    @Bean
    public TypeService mockTypeService() {
        return mock(TypeService.class);
    }

    @Bean
    public LevelService mockLevelService() {
        return mock(LevelService.class);
    }

    @Bean
    public LanguageService mockLanguageService() {
        return mock(LanguageService.class);
    }

    @Bean
    public FileStorageService mockPhotoService() {
        return mock(FileStorageService.class);
    }

    @Bean
    public MailService mockMailService() {
        return mock(MailService.class);
    }

    @Bean
    public PasswordEncoder mockPasswordEncoder() {
        return mock(PasswordEncoder.class);
    }

    @Bean
    public VerificationTokenService mockVerificationTokenService() {
        return mock(VerificationTokenService.class);
    }

}