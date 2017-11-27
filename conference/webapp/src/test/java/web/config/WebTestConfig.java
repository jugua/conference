package web.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import service.businesslogic.api.CommentService;
import service.businesslogic.api.ConferenceService;
import service.businesslogic.api.ContactTypeService;
import service.businesslogic.api.SignInService;
import service.businesslogic.api.TalkService;
import service.businesslogic.api.TopicService;
import service.businesslogic.api.TypeService;
import service.businesslogic.api.UserInfoService;
import service.businesslogic.api.UserService;
import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.mail.MailService;
import service.businesslogic.impl.VerificationTokenService;
import service.businesslogic.api.LanguageService;
import service.businesslogic.api.LevelService;

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
    
    @Bean
    public SignInService mockSignInService() {
    	return mock(SignInService.class);
    }
    
    @Bean
    public CommentService mockCommentController() {
    	return mock(CommentService.class);
    }

}