package ua.rd.cm.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ua.rd.cm.services.ForgotMessagePreparator;
import ua.rd.cm.services.MailService;
import ua.rd.cm.services.UserService;

@RestController
@RequestMapping("/api")
public class EmailController {

	private MailService mailService;
	private UserService userService;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	public EmailController(MailService mailService, UserService userService) {
		this.mailService = mailService;
		this.userService = userService;
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity forgotPassword(@RequestBody String mail) throws  IOException {
		HttpStatus httpStatus;
		ObjectNode node = mapper.readValue(mail, ObjectNode.class);
		
		if(node.get("mail") != null) {
			String userEmail = node.get("mail").textValue();
			
			Map<String, Object> model = new HashMap<>();
			model.put("email", userEmail);
			model.put("name", userService.getByEmail(userEmail).getFirstName());
			mailService.sendEmail(new ForgotMessagePreparator(), model);
			httpStatus = HttpStatus.OK;
		} else {
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity(httpStatus);
	}     
	

}
