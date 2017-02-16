package ua.rd.cm.services.preparator;

import javax.mail.internet.MimeMessage;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;

import java.util.HashMap;

@AllArgsConstructor
public class ForgotMessagePreparator extends CustomMimeMessagePreparator {
    private VerificationToken token;
    private String url;

    @Override
    public String getTemplateName() {
        return "forgot_password_template.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("email", receiver.getEmail());
        model.put("name", receiver.getFirstName());
        model.put("link", url + "/#/forgotPassword/" + token.getToken());
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("Password assistance");
        helper.setFrom((String) model.get("from"));
        helper.setTo((String) model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}