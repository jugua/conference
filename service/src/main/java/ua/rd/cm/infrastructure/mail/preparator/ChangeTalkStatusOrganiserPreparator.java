package ua.rd.cm.infrastructure.mail.preparator;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;

@AllArgsConstructor
public class ChangeTalkStatusOrganiserPreparator extends CustomMimeMessagePreparator {

    private User currentOrganiser;
    private Talk currentTalk;

    @Override
    public String getTemplateName() {
        String template;
        switch (currentTalk.getStatus().getName()) {
            case "In Progress": {
                template = "talk_status_updated_to_in-progress_organizer.ftl";
                break;
            }
            case "Approved": {
                template = "talk_status_updated_to_approved_organizer.ftl";
                break;
            }
            case "Rejected": {
                template = "talk_status_updated_to_rejected_organizer.ftl";
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
        model.put("receiverName", receiver.getFirstName());
        model.put("speakerLastName", currentTalk.getUser().getLastName());
        model.put("speakerFirstName", currentTalk.getUser().getFirstName());
        model.put("email", receiver.getEmail());
        model.put("talkTitle", currentTalk.getTitle());
        model.put("currentOrganiserLastName", currentOrganiser.getLastName());
        model.put("currentOrganiserFirstName", currentOrganiser.getFirstName());
        model.put("subject", "A talk's status has been updated");
    }

}
