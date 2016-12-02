package ua.rd.cm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.rd.cm.services.*;
import ua.rd.cm.services.ContactTypeService;
import ua.rd.cm.services.PhotoService;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.PhotoService;

import static org.mockito.Mockito.mock;

@Configuration
public class WebTestConfig {
    @Bean
    public UserService mockUserService(){
        return mock(UserService.class);
    }
    @Bean
    public UserInfoService mockUserInfoService(){
        return mock(UserInfoService.class);
    }
    @Bean
    public ContactTypeService mockContactTypeService(){
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
    public StatusService mockStatusService() {
        return mock(StatusService.class);
    }
    @Bean
<<<<<<< HEAD
    public PhotoService mockPhotoService(){
=======
    public PhotoService mockPhotoService() {
>>>>>>> 33de745504d5114f6a5423e1066abf7bc09e17c2
        return mock(PhotoService.class);
    }
}