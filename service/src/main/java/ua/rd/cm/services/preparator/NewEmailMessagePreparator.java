package ua.rd.cm.services.preparator;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;

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
