package ua.rd.cm.services.preparator;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class ConfirmAccountPreparator extends CustomMimeMessagePreparator{
    @Override
    public String getTemplateName() {
        return "confirmation-process.ftl";
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject("Confirm Your Account");
        helper.setFrom("support@conference.com");
        helper.setTo((String)model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}