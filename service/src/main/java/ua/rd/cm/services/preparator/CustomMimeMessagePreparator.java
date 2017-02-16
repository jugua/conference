package ua.rd.cm.services.preparator;

import java.io.IOException;
import java.util.Map;

import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import ua.rd.cm.domain.User;

import javax.mail.internet.MimeMessage;

@Log4j
public abstract class CustomMimeMessagePreparator implements MimeMessagePreparator {
    protected Map<String, Object> model;
    @Setter
    private Configuration freemarkerConfiguration;

    public abstract String getTemplateName();

    public abstract void prepareModel(User receiver);

    public void setSender(String sender) {
        model.put("from", sender);
    }

    private String getFreeMarkerTemplateContent(Map<String, Object> model) {
        String content = "";
        try {
            content = FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate(getTemplateName()), model);
        } catch (IOException | TemplateException e) {
            log.error("Exception occurred while processing template ", e);
        }
        return content;
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject((String) model.get("subject"));
        helper.setFrom((String) model.get("from"));
        helper.setTo((String) model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}
