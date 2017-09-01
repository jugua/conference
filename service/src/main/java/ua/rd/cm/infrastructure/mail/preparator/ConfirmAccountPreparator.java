package ua.rd.cm.infrastructure.mail.preparator;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;

@AllArgsConstructor
public class ConfirmAccountPreparator extends CustomMimeMessagePreparator {
    private VerificationToken token;
    private String url;

    @Override
    public String getTemplateName() {
        return "confirmation-process.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        model.put("email", receiver.getEmail());
        model.put("link", url + "/#/registrationConfirm/" + token.getToken());
        model.put("subject", "Confirm Your Account");
    }

}