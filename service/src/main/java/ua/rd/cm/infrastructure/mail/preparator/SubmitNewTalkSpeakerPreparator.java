package ua.rd.cm.infrastructure.mail.preparator;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.User;

@AllArgsConstructor
public class SubmitNewTalkSpeakerPreparator extends CustomMimeMessagePreparator {

    @Override
    public String getTemplateName() {
        return "submitted_talk_speaker.ftl";
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        model.put("email", receiver.getEmail());
        model.put("subject", "Your talk's status has been updated");
    }

}
