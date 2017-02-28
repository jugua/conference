package ua.rd.cm.services;

import freemarker.template.Configuration;

import java.util.List;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.preparator.CustomMimeMessagePreparator;

@Log4j
@Service
public class MailService {
    private JavaMailSender mailSender;
    private Configuration freemarkerConfiguration;

    @Autowired
    public MailService(JavaMailSender mailSender, Configuration freemarkerConfiguration) {
        this.mailSender = mailSender;
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    public void sendEmail(User receiver, CustomMimeMessagePreparator preparator) {
        preparator.prepareModel(receiver);
        preparator.setFreemarkerConfiguration(freemarkerConfiguration);
        try {
            mailSender.send(preparator);
        } catch (MailException e) {
            log.error("Couldn't send email:", e);
        }
    }

    public void notifyUsers(List<User> receivers, CustomMimeMessagePreparator preparator) {
        receivers.forEach(receiver -> sendEmail(receiver, preparator));
    }
}