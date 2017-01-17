package ua.rd.cm.services.preparator;

import java.io.IOException;
import java.util.Map;
import freemarker.template.TemplateException;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import ua.rd.cm.domain.User;

@NoArgsConstructor
@Setter
public abstract class CustomMimeMessagePreparator implements MimeMessagePreparator {

	protected Map<String, Object> model;
	private Configuration freemarkerConfiguration;
	
	public abstract String getTemplateName();

	public abstract void prepareModel(User receiver);
	
	public String getFreeMarkerTemplateContent(Map<String, Object> model) {
        StringBuilder content = new StringBuilder();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate(getTemplateName()),model));
            return content.toString();
        } catch (IOException | TemplateException e) {
            System.out.println("Exception occured while processing fmtemplate:" + e.getMessage());
			return "";
        }
    }
}
