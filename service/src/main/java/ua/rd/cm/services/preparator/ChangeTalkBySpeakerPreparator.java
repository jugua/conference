package ua.rd.cm.services.preparator;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@AllArgsConstructor
public class ChangeTalkBySpeakerPreparator extends CustomMimeMessagePreparator {
    private Talk currentTalk;
    private String url;

    @Override
    public String getTemplateName() {
        return "talk_update_by_speaker.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("receiverName", currentTalk.getOrganiser().getFirstName());
        model.put("speakerFullName", currentTalk.getUser().getFullName());
        model.put("link", url + "/#/talks/" + currentTalk.getId());
        model.put("email", currentTalk.getOrganiser().getEmail());
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("A talk has been updated");
        helper.setTo((String) model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }
}
