package ua.rd.cm.services.mapper;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;

import java.util.HashMap;
import java.util.Map;

public class NewTalkOrganiserModelMapper implements MessageModelMapper{

    private Talk currentTalk;

    public NewTalkOrganiserModelMapper(Talk currentTalk) {
        this.currentTalk = currentTalk;
    }

    @Override
    public Map<String, Object> prepareModel(User receiver) {
        final Map<String, Object> model = new HashMap<>();
        model.put("receiverName", receiver.getFirstName());
        model.put("speakerLastName", currentTalk.getUser().getLastName());
        model.put("speakerFirstName", currentTalk.getUser().getFirstName());
        model.put("email", receiver.getEmail());
        model.put("link", "link"); //TODO link generator
        return model;
    }
}
