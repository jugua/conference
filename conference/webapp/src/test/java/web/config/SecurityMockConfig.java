package web.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import service.businesslogic.impl.VerificationTokenService;
import web.security.WithTokenGetRequestProcessor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityMockConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    @Bean
    public WithTokenGetRequestProcessor withTokenGetRequestProcessor(VerificationTokenService tokenService) {
        //TODO: probably we have to use mock here
        return new WithTokenGetRequestProcessor(tokenService);
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
