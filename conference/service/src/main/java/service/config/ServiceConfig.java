package service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import domain.config.RepositoryConfig;
import domain.model.Conference;
import domain.model.Talk;
import service.businesslogic.dto.CreateConferenceDto;
import service.businesslogic.dto.TalkDto;
import service.businesslogic.dto.converter.CreateConferenceToConference;

@Configuration
@ComponentScan(basePackages = {"service.businesslogic", "service.infrastructure."})
@PropertySource("classpath:default/app.properties")
@PropertySource(value = "file:${catalina.home}/conference/app.properties", ignoreResourceNotFound = true)
@Import({RepositoryConfig.class, MailConfig.class, FileStorageConfig.class})
public class ServiceConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(CreateConferenceDto.class, Conference.class).
                setPostConverter(new CreateConferenceToConference());
        modelMapper.createTypeMap(TalkDto.class, Talk.class);
        modelMapper.createTypeMap(Talk.class, TalkDto.class);
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}