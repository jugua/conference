package ua.rd.cm.infrastructure.mail.preparator;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;

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
        model.put("subject", "A talk has been updated");
    }

}
