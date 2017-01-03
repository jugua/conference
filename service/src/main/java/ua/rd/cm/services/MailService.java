package ua.rd.cm.services;

import freemarker.template.Configuration;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.mapper.MessageModelMapper;
import ua.rd.cm.services.preparator.CustomMimeMessagePreparator;

@Service
public class MailService {
    private JavaMailSender mailSender;
    private Configuration freemarkerConfiguration;
    
    @Autowired
    public MailService(JavaMailSender mailSender, Configuration freemarkerConfiguration) {
        this.mailSender = mailSender;
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    public void sendEmail(CustomMimeMessagePreparator preparator, Map<String, Object> model) {
    	preparator.setModel(model);
    	preparator.setFreemarkerConfiguration(freemarkerConfiguration);
    	try {
    		mailSender.send(preparator);
    	} catch (MailException e) {
    		System.out.println("so sad (");
    	}
    }

    public void notifyUsers(
            List<User> receivers,
            MessageModelMapper modelMapper,
            CustomMimeMessagePreparator preparator
    ) {
        receivers.forEach(receiver -> sendEmail(preparator, modelMapper.prepareModel(receiver)));
    }
}