package ua.rd.cm.config;

import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.dto.CreateConferenceDto;
import ua.rd.cm.dto.TalkDto;
import ua.rd.cm.dto.converter.CreateConferenceToConference;
import ua.rd.cm.infrastructure.fileStorage.FileStorageService;
import ua.rd.cm.infrastructure.fileStorage.impl.FileStorageServiceImpl;
import ua.rd.cm.infrastructure.mail.MailService;

@Configuration
@ComponentScan(basePackages = {"ua.rd.cm.services"})
@PropertySources({
        @PropertySource("classpath:default/app.properties"),
        @PropertySource(value = "file:${catalina.home}/conference/app.properties", ignoreResourceNotFound = true)
})
@Import(RepositoryConfig.class)
public class ServiceConfig {

    @Bean
    public JavaMailSender getMailSender(Environment environment) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getProperty("mail.host"));
        mailSender.setPort(environment.getProperty("mail.port", Integer.class));
        mailSender.setUsername(environment.getProperty("mail.username"));
        mailSender.setPassword(environment.getProperty("mail.password"));

        Properties javaMailProperties = new Properties();
        setProperty(javaMailProperties, environment, "mail.smtp.starttls.enable");
        setProperty(javaMailProperties, environment, "mail.smtp.auth");
        setProperty(javaMailProperties, environment, "mail.smtp.from");
        setProperty(javaMailProperties, environment, "mail.transport.protocol");
        setProperty(javaMailProperties, environment, "mail.debug");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    private void setProperty(Properties props, Environment env, String propName) {
        props.put(propName, env.getProperty(propName));
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:fmtemplates/");
        return bean;
    }

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

    @Bean
    public FileStorageService getFileStorageService(Environment environment) {
        FileStorageServiceImpl fileStorageService = new FileStorageServiceImpl();
        fileStorageService.setStoragePath(environment.getProperty("fileStorage.path"));
        return fileStorageService;
    }

    @Bean
    public MailService getMailService(JavaMailSender javaMailSender, freemarker.template.Configuration
            freemarkerConfiguration,
                                      Environment environment) {
        return new MailService(javaMailSender, freemarkerConfiguration,
                environment.getProperty("mail.url"),
                environment.getProperty("mail.sender") + " <" + environment.getProperty("mail.smtp.from") + ">");
    }
}