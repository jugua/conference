package ua.rd.cm.services.preparator;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@AllArgsConstructor
public class ChangeTalkStatusPreparator extends CustomMimeMessagePreparator {

    private User currentOrganiser;
    private Talk currentTalk;

    @Override
    public String getTemplateName() {
        return null;
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("receiverName", receiver.getFirstName());
        model.put("speakerLastName", currentTalk.getUser().getLastName());
        model.put("speakerFirstName", currentTalk.getUser().getFirstName());
        model.put("email", receiver.getEmail());
        model.put("talkTitle", currentTalk.getTitle());
        model.put("currentOrganiserLastName", currentOrganiser.getLastName());
        model.put("currentOrganiserFirstName", currentOrganiser.getFirstName());
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("A talk's status has been updated");
        helper.setFrom("support@conference.com");
        helper.setTo((String)model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}
