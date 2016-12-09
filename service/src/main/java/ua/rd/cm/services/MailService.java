package ua.rd.cm.services;

import freemarker.template.Configuration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private JavaMailSender mailSender;
    private Configuration freemarkerConfiguration;
    
    @Autowired
    public MailService(JavaMailSender mailSender, Configuration freemarkerConfiguration) {
        this.mailSender = mailSender;
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    
    public void sendEmail(CustomMimeMessagePreparator preparator, Map<String, String> model) {
    	preparator.setModel(model);
    	preparator.setFreemarkerConfiguration(freemarkerConfiguration);
    	
    	try {
    		mailSender.send(preparator);
    	
    	} catch (MailException e) {
    		System.out.println("so sad (");
    	}
    }
    
}
