package service.infrastructure.mail.preparator;

import java.util.HashMap;

import domain.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvitePreparator extends CustomMimeMessagePreparator{

    private String url;
	
	@Override
	public String getTemplateName() {
		return "registration-process.ftl";
	}

	@Override
	public void prepareModel(User receiver) {
		 model = new HashMap<>();
	     model.put("name", receiver.getFirstName());
	     model.put("email", receiver.getEmail());
	     model.put("link",url);
	     model.put("subject", "Registration page");
	}

}
