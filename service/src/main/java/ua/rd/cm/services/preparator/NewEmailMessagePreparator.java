package ua.rd.cm.services.preparator;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@AllArgsConstructor
public class NewEmailMessagePreparator extends CustomMimeMessagePreparator {

    private VerificationToken token;

    @Override
    public String getTemplateName() {
        return "new_email_template.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("name" , receiver.getFirstName());
        model.put("email", receiver.getEmail());
        model.put("link", "http://localhost:8050/#/newEmailConfirm/" + token.getToken());
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("Email Verification");
        helper.setFrom("support@conference.com");
        helper.setTo((String)model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}
