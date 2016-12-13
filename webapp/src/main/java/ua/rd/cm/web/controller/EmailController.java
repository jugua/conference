package ua.rd.cm.web.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ua.rd.cm.domain.User;
import ua.rd.cm.services.ForgotMessagePreparator;
import ua.rd.cm.services.MailService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.NewPasswordDto;

@RestController
@RequestMapping("/api")
public class EmailController {

	private MailService mailService;
	private UserService userService;
	private ObjectMapper objectMapper = new ObjectMapper();
	private ModelMapper modelMapper;
	
	@Autowired
	public EmailController(MailService mailService, UserService userService, ModelMapper modelMapper) {
		this.mailService = mailService;
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity forgotPassword(@RequestBody String mail) throws  IOException {
		HttpStatus httpStatus;
		ObjectNode node = objectMapper.readValue(mail, ObjectNode.class);
		
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
	
	@PostMapping("/change-password")
	public ResponseEntity changePassword(@Valid @RequestBody NewPasswordDto dto, BindingResult bindingResult, Principal principal) {
		HttpStatus httpStatus;
		if(bindingResult.hasFieldErrors() || isPasswordConfirmed(dto)) {
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (principal == null) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
        	User currentUser = userService.getByEmail(principal.getName());
        	currentUser.setPassword(dto.getPassword());
        	userService.updateUserProfile(currentUser);
			httpStatus = HttpStatus.OK;
		}
	
		return new ResponseEntity(httpStatus);
	}
	

	private boolean isPasswordConfirmed(NewPasswordDto dto) {
        return dto.getPassword().equals(dto.getConfirm());
    }
}
