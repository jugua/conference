package ua.rd.cm.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import ua.rd.cm.services.FileStorageService;
import ua.rd.cm.services.MailService;
import ua.rd.cm.services.impl.FileStorageServiceImpl;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

/**
 * @author Yaroslav_Revin
 */

@Configuration
@ComponentScan(basePackages = {
        "ua.rd.cm.domain",
        "ua.rd.cm.services"
})
@Import(RepositoryConfig.class)
@PropertySource({"classpath:mail.properties", "classpath:fileStorage.properties"})
public class ServiceConfig {

    @Bean
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }

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
        return new ModelMapper();
    }

    @Bean
    public FileStorageService getFileStorageService(Environment environment) {
        FileStorageServiceImpl fileStorageService = new FileStorageServiceImpl();
        fileStorageService.setStoragePath(environment.getProperty("fileStorage.path"));
        return fileStorageService;
    }

    @Bean
    public MailService getMailService(JavaMailSender javaMailSender, freemarker.template.Configuration freemarkerConfiguration,
                                      Environment environment) {
        return new MailService(javaMailSender, freemarkerConfiguration,
                environment.getProperty("mail.url"),
                environment.getProperty("mail.sender") + " <" + environment.getProperty("mail.smtp.from") + ">");
    }


}