package web.config;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static web.util.TestData.ORGANISER_EMAIL;
import static web.util.TestData.SPEAKER_EMAIL;
import static web.util.TestData.organizer;
import static web.util.TestData.speaker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import service.businesslogic.api.CommentService;
import service.businesslogic.api.ConferenceService;
import service.businesslogic.api.ContactTypeService;
import service.businesslogic.api.LanguageService;
import service.businesslogic.api.LevelService;
import service.businesslogic.api.SignInService;
import service.businesslogic.api.TalkService;
import service.businesslogic.api.TopicService;
import service.businesslogic.api.TypeService;
import service.businesslogic.api.UserInfoService;
import service.businesslogic.api.UserService;

@Configuration
public class ServiceMockConfig {

    @Bean
    public ConferenceService mockConferenceService() {
        return mock(ConferenceService.class);
    }

    @Bean
    public UserService userServiceMock() {
        UserService mock = mock(UserService.class);
        when(mock.getByEmail(eq(SPEAKER_EMAIL))).thenReturn(speaker());
        when(mock.getByEmail(eq(ORGANISER_EMAIL))).thenReturn(organizer());
        return mock;
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
    public SignInService mockSignInService() {
        return mock(SignInService.class);
    }

    @Bean
    public CommentService mockCommentController() {
        return mock(CommentService.class);
    }

}