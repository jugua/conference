package service.infrastructure.mail.preparator;

import java.util.HashMap;

import domain.model.User;
import domain.model.VerificationToken;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NewEmailMessagePreparator extends CustomMimeMessagePreparator {
    private VerificationToken token;
    private String url;

    @Override
    public String getTemplateName() {
        return "new_email_template.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        model.put("email", receiver.getEmail());
        model.put("link", url + "/#/newEmailConfirm/" + token.getToken());
        model.put("subject", "Email Verification");
    }

}
