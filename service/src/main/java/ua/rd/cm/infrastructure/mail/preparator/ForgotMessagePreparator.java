package ua.rd.cm.infrastructure.mail.preparator;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;

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
        model.put("subject", "Password assistance");
    }

}