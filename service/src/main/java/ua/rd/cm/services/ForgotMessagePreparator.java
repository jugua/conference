package ua.rd.cm.services;

import javax.mail.internet.MimeMessage;

import lombok.NoArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;

@NoArgsConstructor
public class ForgotMessagePreparator extends CustomMimeMessagePreparator {

	@Override
	public void prepare(MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		 helper.setSubject("Password assistance");
         helper.setFrom("support@conference.com");
         helper.setTo((String)model.get("email"));
         helper.setText(getFreeMarkerTemplateContent(model), true);
    }

	@Override
	public String getTemplateName() {
		return "forgot_password_template.ftl";
	}
}
