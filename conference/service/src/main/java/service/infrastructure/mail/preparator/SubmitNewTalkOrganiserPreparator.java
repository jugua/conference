package service.infrastructure.mail.preparator;

import java.util.HashMap;

import domain.model.Talk;
import domain.model.User;
import lombok.AllArgsConstructor;

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