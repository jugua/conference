package ua.rd.cm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import ua.rd.cm.services.CustomAuthenticationProvider;
import ua.rd.cm.web.security.CsrfHeaderFilter;
import ua.rd.cm.web.security.CustomBasicAuthFilter;

/**
 * @author Yaroslav_Revin
 */

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "ua.rd.cm.web.security")
@Import(CustomAuthenticationProvider.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CustomBasicAuthFilter basicAuthFilter;

    @Autowired
    private CsrfHeaderFilter csrfHeaderFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure (HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .mvcMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .mvcMatchers(HttpMethod.POST, "/api/user").permitAll()
                    .antMatchers("/api/user/current").authenticated()
                    .antMatchers("/api/helloworld").authenticated()
                    .and()
                .csrf()
                    .csrfTokenRepository(csrfTokenRepository())
                    .and()
                .httpBasic()
                    .and()
                .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                .addFilterBefore(basicAuthFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(csrfHeaderFilter, CsrfFilter.class);
    }

    @Bean(name = "authManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomBasicAuthFilter basicAuthFilter() throws Exception {
        return new CustomBasicAuthFilter(authManager);
    }

    @Bean
    public CsrfHeaderFilter csrfHeaderFilter(){
        return new CsrfHeaderFilter();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }


}
