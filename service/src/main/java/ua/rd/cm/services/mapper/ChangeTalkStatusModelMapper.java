package ua.rd.cm.services.mapper;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;
import java.util.HashMap;
import java.util.Map;

public class ChangeTalkStatusModelMapper implements MessageModelMapper{

    private User currentOrganiser;
    private Talk currentTalk;

    public ChangeTalkStatusModelMapper(User currentOrganiser, Talk currentTalk) {
        this.currentOrganiser = currentOrganiser;
        this.currentTalk = currentTalk;
    }

    @Override
    public Map<String, Object> prepareModel(User receiver) {
        final Map<String, Object> model = new HashMap<>();
        model.put("receiverName", receiver.getFirstName());
        model.put("speakerLastName", currentTalk.getUser().getLastName());
        model.put("speakerFirstName", currentTalk.getUser().getFirstName());
        model.put("email", receiver.getEmail());
        model.put("talkTitle", currentTalk.getTitle());
        model.put("currentOrganiserLastName", currentOrganiser.getLastName());
        model.put("currentOrganiserFirstName", currentOrganiser.getFirstName());
        return model;
    }
}
