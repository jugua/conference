package ua.rd.cm.services.preparator;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;

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
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("Your talk's status has been updated");
        helper.setFrom("support@conference.com");
        helper.setTo((String) model.get("email"));
        helper.setText(getFreeMarkerTemplateContent(model), true);
    }

    private String defineApprovedTemplate() {
        return (isThereComment()) ? "approved_talk_comment_speaker.ftl" : "approved_talk_speaker.ftl";
    }

    private boolean isThereComment() {
        return talk.getOrganiserComment() != null && talk.getOrganiserComment().length() > 0;
    }
}