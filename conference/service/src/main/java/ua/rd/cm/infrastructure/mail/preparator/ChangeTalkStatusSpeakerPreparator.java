package ua.rd.cm.infrastructure.mail.preparator;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;

@AllArgsConstructor
public class ChangeTalkStatusSpeakerPreparator extends CustomMimeMessagePreparator {
    private Talk talk;

    @Override
    public String getTemplateName() {
        String template;
        switch (talk.getStatus().getName()) {
            case "In Progress": {
                template = "reviewed_talk_speaker.ftl";
                break;
            }
            case "Approved": {
                template = defineApprovedTemplate();
                break;
            }
            case "Rejected": {
                template = "rejected_talk_speaker.ftl";
                break;
            }
            default: {
                template = ""; //TODO template for wrong message or exception
                break;
            }
        }
        return template;
    }

    @Override
    public void prepareModel(User receiver) {
        model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        model.put("email", receiver.getEmail());
        if (isThereComment()) {
            model.put("comment", talk.getOrganiserComment());
        }
        model.put("subject", "Your talk's status has been updated");
    }

    private String defineApprovedTemplate() {
        return (isThereComment()) ? "approved_talk_comment_speaker.ftl" : "approved_talk_speaker.ftl";
    }

    private boolean isThereComment() {
        return talk.getOrganiserComment() != null && talk.getOrganiserComment().length() > 0;
    }
}