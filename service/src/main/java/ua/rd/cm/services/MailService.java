package ua.rd.cm.services;

import freemarker.template.Configuration;

import java.util.List;

import lombok.Getter;
import lombok.Value;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.preparator.CustomMimeMessagePreparator;

@Log4j
public class MailService {
    private JavaMailSender mailSender;
    private Configuration freemarkerConfiguration;
    @Getter
    private String url;
    private String senderName;


    public MailService(JavaMailSender mailSender, Configuration freemarkerConfiguration,
                       String url, String senderName) {
        this.mailSender = mailSender;
        this.freemarkerConfiguration = freemarkerConfiguration;
        this.url = url;
        this.senderName = senderName;
    }

    @Async
    public void sendEmail(User receiver, CustomMimeMessagePreparator preparator) {
        preparator.prepareModel(receiver);
        preparator.setFreemarkerConfiguration(freemarkerConfiguration);
        preparator.setSender(senderName);
        try {
            mailSender.send(preparator);
        } catch (MailException e) {
            log.error("Exception occurred while send email ", e);
        }
    }

    public void notifyUsers(List<User> receivers, CustomMimeMessagePreparator preparator) {
        receivers.forEach(receiver -> sendEmail(receiver, preparator));
    }
}