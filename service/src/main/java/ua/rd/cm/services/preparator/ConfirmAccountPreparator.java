package ua.rd.cm.services.preparator;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@AllArgsConstructor
public class ConfirmAccountPreparator extends CustomMimeMessagePreparator{

    private VerificationToken token;

    @Override
    public String getTemplateName() {
        return "confirmation-process.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("link", "http://localhost:8050/#/registrationConfirm/" + token.getToken());
        model.put("name" , receiver.getFirstName());
        model.put("email", receiver.getEmail());
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("Confirm Your Account");
        helper.setTo((String)model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}