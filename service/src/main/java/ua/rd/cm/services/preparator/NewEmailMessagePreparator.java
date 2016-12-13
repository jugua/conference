package ua.rd.cm.services.preparator;

import lombok.NoArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.services.preparator.CustomMimeMessagePreparator;

import javax.mail.internet.MimeMessage;

@NoArgsConstructor
public class NewEmailMessagePreparator extends CustomMimeMessagePreparator {
    @Override
    public String getTemplateName() {
        return "new_email_template.ftl";
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject("Password assistance");
        helper.setFrom("support@conference.com");
        helper.setTo((String)model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}
