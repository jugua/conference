package service.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import service.infrastructure.mail.MailService;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getMailSender(Environment environment) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getProperty("mail.host"));
        mailSender.setPort(environment.getProperty("mail.port", Integer.class));
        mailSender.setUsername(environment.getProperty("mail.username"));
        mailSender.setPassword(environment.getProperty("mail.password"));
        mailSender.setJavaMailProperties(loadJavaMailProperties(environment));
        return mailSender;
    }

    @Bean
    public MailService getMailService(JavaMailSender javaMailSender,
                                      freemarker.template.Configuration freemarkerConfiguration,
                                      Environment environment) {
        return new MailService(javaMailSender, freemarkerConfiguration,
                environment.getProperty("mail.url"),
                environment.getProperty("mail.sender") + " <" + environment.getProperty("mail.smtp.from") + ">");
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:fmtemplates/");
        return bean;
    }

    private Properties loadJavaMailProperties(Environment environment) {
        Properties result = new Properties();
        setProperty(result, environment, "mail.smtp.starttls.enable");
        setProperty(result, environment, "mail.smtp.auth");
        setProperty(result, environment, "mail.smtp.from");
        setProperty(result, environment, "mail.transport.protocol");
        setProperty(result, environment, "mail.debug");
        return result;
    }

    private void setProperty(Properties props, Environment env, String propName) {
        props.put(propName, env.getProperty(propName));
    }

}
