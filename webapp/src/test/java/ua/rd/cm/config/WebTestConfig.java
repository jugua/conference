package ua.rd.cm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.rd.cm.services.ContactTypeService;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.UserService;

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
}