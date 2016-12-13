package ua.rd.cm.services.preparator;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @author Olha_Melnyk
 */
public class ChangePasswordPreparator extends CustomMimeMessagePreparator {

    @Override
    public String getTemplateName() {
        return "change_password_template.ftl";
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
