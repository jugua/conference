package ua.rd.cm.services.preparator;

import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.domain.User;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

public class SubmitNewTalkSpeakerPreparator extends CustomMimeMessagePreparator{

    @Override
    public String getTemplateName() {
        return "submitted_talk_speaker.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        model.put("email", receiver.getEmail());
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("Your talk's status has been updated");
        helper.setFrom("support@conference.com");
        helper.setTo((String)model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}
