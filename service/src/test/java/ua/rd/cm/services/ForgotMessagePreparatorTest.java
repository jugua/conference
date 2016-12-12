/*package ua.rd.cm.services;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ForgotMessagePreparatorTest extends ServiceTestConfig{

	@Autowired
	private MailService mailService;
	
	@Test
	public void testGetTemplateName() {
		assertEquals("forgot_password_template.ftl", new ForgotMessagePreparator().getTemplateName());
	}

	@Test
	public void testPrepare() {
		
	}

	@Test
	public void testGetFreeMarkerTemplateContent() {
	}
	
	@Test
	public void testSendEmail() {
		Map<String, Object> model = new HashMap<>();
		model.put("email", "speaker@speaker.com");
		model.put("name", "VALERA");
		model.put("tittle", "WHY YOU FORGOT PASSWORD ? ");
		
		mailService.sendEmail(new ForgotMessagePreparator(), model);
		
	}

}
*/