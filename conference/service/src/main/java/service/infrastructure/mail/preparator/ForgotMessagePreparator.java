package service.infrastructure.mail.preparator;

import java.util.HashMap;

import domain.model.User;
import domain.model.VerificationToken;
import lombok.AllArgsConstructor;

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