package ua.rd.cm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import ua.rd.cm.web.security.AccessDeniedHandlerImpl;
import ua.rd.cm.web.security.AuthenticationEntryPointImpl;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                    .authenticationEntryPoint(new AuthenticationEntryPointImpl())
                    .accessDeniedHandler(accessDeniedHandler())
                    .and()
                .csrf().disable();
    }

    private AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorCode(HttpServletResponse.SC_UNAUTHORIZED);
        accessDeniedHandler.setForbiddenMsg(AuthenticationEntryPointImpl.DEFAULT_UNAUTHORIZED_MSG);
        return accessDeniedHandler;
    }
}
