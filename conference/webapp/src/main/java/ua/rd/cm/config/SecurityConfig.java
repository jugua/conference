package ua.rd.cm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import ua.rd.cm.web.security.BasicAuthFilterImpl;
import ua.rd.cm.web.security.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "ua.rd.cm.web.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
    @Autowired
    private BasicAuthFilterImpl basicAuthFilterImpl;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .mvcMatchers(HttpMethod.POST, "/login").permitAll()
                .mvcMatchers(HttpMethod.POST, "/registration").permitAll()
                .mvcMatchers(HttpMethod.POST, "/talk").permitAll()
                .mvcMatchers(HttpMethod.POST, "/").permitAll()
                .antMatchers("/myinfo").authenticated()
                .and()
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .addFilterBefore(basicAuthFilterImpl, BasicAuthenticationFilter.class);
    }

    @Bean
    public BasicAuthFilterImpl authenticationFilter(AuthenticationManager authenticationManager) {
        return new BasicAuthFilterImpl(authenticationManager);
    }

    @Bean(name = "authManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }
}
