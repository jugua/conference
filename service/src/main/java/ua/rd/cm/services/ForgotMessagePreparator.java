package ua.rd.cm.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

import freemarker.template.Configuration;

public class ForgotMessagePreparator extends CustomMimeMessagePreparator{



	public ForgotMessagePreparator(Configuration freemarkerConfiguration) {
		super(freemarkerConfiguration);
	}

	@Override
	public void prepare(MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		 helper.setSubject("Forgot password");
         helper.setFrom("conference_management@");
        // helper.setTo(order.getCustomerInfo().getEmail());
         
         
	}

	@Override
	public String getTemplateName() {
		return "";
	}
}
