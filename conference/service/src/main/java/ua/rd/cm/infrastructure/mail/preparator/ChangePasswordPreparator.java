package ua.rd.cm.infrastructure.mail.preparator;

import java.util.HashMap;

import ua.rd.cm.domain.User;

public class ChangePasswordPreparator extends CustomMimeMessagePreparator {

    @Override
    public String getTemplateName() {
        return "change_password_template.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        model.put("email", receiver.getEmail());
        model.put("subject", "Your password has been changed");
    }
}
