package ua.rd.cm.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.rd.cm.services.ForgotMessagePreparator;
import ua.rd.cm.services.MailService;
import ua.rd.cm.web.controller.dto.UserDto;

@RestController
@RequestMapping("/api")
public class EmailController {

	private MailService mailService;

	@Autowired
	public EmailController(MailService mailService) {
		this.mailService = mailService;
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity forgotPassword(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
		HttpStatus httpStatus;
		if (bindingResult.hasErrors())
			httpStatus = HttpStatus.BAD_REQUEST;
		else {
			Map<String, Object> model = new HashMap<>();
			model.put("email", userDto.getEmail());
			model.put("name", "Good User name");
			mailService.sendEmail(new ForgotMessagePreparator(), model);
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity(httpStatus);
	}
}
