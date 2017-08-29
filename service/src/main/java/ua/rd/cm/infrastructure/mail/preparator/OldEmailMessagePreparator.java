package ua.rd.cm.infrastructure.mail.preparator;

import java.time.LocalDateTime;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.User;

@AllArgsConstructor
public class OldEmailMessagePreparator extends CustomMimeMessagePreparator {
    private String oldEmail;

    @Override
    public String getTemplateName() {
        return "old_email_template.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        model.put("oldEmail", oldEmail);
        model.put("email", oldEmail);
        model.put("newEmail", receiver.getEmail());
        model.put("dateTime", LocalDateTime.now().toString());
        model.put("subject", "Email address has been changed");
    }

}
