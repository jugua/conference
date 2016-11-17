package ua.rd.cm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Yaroslav_Revin
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("user@user.com").password("user").roles("USER");
    }

    @Override
    public void configure (HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/api/login").permitAll()
                    .mvcMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .loginPage("/index.html")
                    .loginProcessingUrl("/api/login")
                    .successForwardUrl("/")
                    .failureForwardUrl("/")
                    .passwordParameter("password")
                    .usernameParameter("mail")
                    .permitAll()
                    .and()
                .httpBasic()
                    .disable()
                .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll();
    }
}
