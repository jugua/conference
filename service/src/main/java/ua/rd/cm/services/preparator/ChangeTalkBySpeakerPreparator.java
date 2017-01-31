package ua.rd.cm.services.preparator;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@AllArgsConstructor
public class ChangeTalkBySpeakerPreparator extends CustomMimeMessagePreparator{

    private Talk currentTalk;

    @Override
    public String getTemplateName() {
        return "talk_update_by_speaker.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("receiverName",currentTalk.getOrganiser().getFirstName());
        model.put("speakerFullName",currentTalk.getUser().getFullName());
        model.put("talkTitle", currentTalk.getTitle());
        model.put("link", "http://localhost:8050/#/talks/" + currentTalk.getId());
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("A talk has been updated");
        helper.setFrom("support@conference.com");
        helper.setTo((String)model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}
