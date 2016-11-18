package ua.rd.cm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import ua.rd.cm.services.CustomUserDetailsService;
import ua.rd.cm.services.CustomAuthenticationProvider;
import ua.rd.cm.web.security.CustomBasicAuthFilter;

/**
 * @author Yaroslav_Revin
 */

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "ua.rd.cm.web.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationManager authManager;


    //private CustomBasicAuthFilter basicAuthFilter ;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("user@user.com").password("user").roles("USER");

    }

    @Override
    public void configure (HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .mvcMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .mvcMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .antMatchers("/api/users/current").authenticated()
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .and()
                .csrf().disable()
                //.csrf()
                    //.csrfTokenRepository(csrfTokenRepository())
                    //.and()
//                .formLogin()
//                    .loginPage("/index.html")
//                    .loginProcessingUrl("/api/login")
//                    .successForwardUrl("/")
//                    .failureForwardUrl("/")
//                    .passwordParameter("password")
//                    .usernameParameter("mail")
//                    .permitAll()
//                    .and()
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
                .authenticationProvider(authenticationProvider)
                .addFilterAfter(new CustomBasicAuthFilter(authManager), BasicAuthenticationFilter.class);
                //.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

    @Bean(name = "authManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }


}
