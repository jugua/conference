package ua.rd.cm.services;

import java.io.IOException;
import java.util.Map;

import freemarker.template.TemplateException;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;

public abstract class CustomMimeMessagePreparator implements MimeMessagePreparator{

	protected Map<String, String> model;
	private Configuration freemarkerConfiguration;
	
	public CustomMimeMessagePreparator(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}
	
	public abstract String getTemplateName();
	
	public String getFreeMarkerTemplateContent(Map<String, Object> model){
        StringBuffer content = new StringBuffer();
        try{
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate(getTemplateName()),model));
            return content.toString();
        } catch (IOException | TemplateException e){
            System.out.println("Exception occured while processing fmtemplate:"+e.getMessage());
        }
		return "";
    }

	public void setModel(Map<String, String> model) {
		this.model = model;
	}

	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}
}
