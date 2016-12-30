package ua.rd.cm.services.preparator;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class SubmitNewTalkPreparator extends CustomMimeMessagePreparator {
    @Override
    public String getTemplateName() {
        return null;
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject("A new talk has been submitted");
        helper.setFrom("support@conference.com");
        helper.setTo((String)model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}
