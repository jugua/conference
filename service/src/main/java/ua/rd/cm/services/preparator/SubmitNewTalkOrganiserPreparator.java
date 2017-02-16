package ua.rd.cm.services.preparator;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@AllArgsConstructor
public class SubmitNewTalkOrganiserPreparator extends CustomMimeMessagePreparator {
    private Talk currentTalk;
    private String url;

    @Override
    public String getTemplateName() {
        return "submitted_talk_organizer.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("receiverName", receiver.getFirstName());
        model.put("speakerLastName", currentTalk.getUser().getLastName());
        model.put("speakerFirstName", currentTalk.getUser().getFirstName());
        model.put("email", receiver.getEmail());
        model.put("link", url + "/#/talks/" + currentTalk.getId());
        model.put("subject", "A new talk has been submitted");
    }

}