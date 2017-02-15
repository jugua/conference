package ua.rd.cm.services.preparator;

import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.domain.User;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;

public class ChangePasswordPreparator extends CustomMimeMessagePreparator {

    @Override
    public String getTemplateName() {
        return "change_password_template.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        model.put("email",receiver.getEmail());
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("Your password has been changed");
        helper.setTo((String)model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}
