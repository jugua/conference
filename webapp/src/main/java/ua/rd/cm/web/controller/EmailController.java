package ua.rd.cm.web.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.security.auth.x500.X500Principal;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.services.preparator.ForgotMessagePreparator;
import ua.rd.cm.services.MailService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.VerificationTokenService;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.NewPasswordDto;
import ua.rd.cm.web.controller.dto.UserDto;
import ua.rd.cm.web.security.CustomAuthenticationProvider;

@RestController
@RequestMapping("/api")
public class EmailController {

	private MailService mailService;
	private UserService userService;
	private ObjectMapper objectMapper;
	private ModelMapper modelMapper;
	private VerificationTokenService tokenService;
	
	@Autowired
	CustomAuthenticationProvider authenticationProvider;
	
	@Autowired
	public EmailController(MailService mailService, UserService userService,
						   ModelMapper modelMapper, ObjectMapper objectMapper,
						   VerificationTokenService tokenService) {
		
		this.mailService = mailService;
		this.userService = userService;
		this.modelMapper = modelMapper;
		this.objectMapper = objectMapper;
		this.tokenService = tokenService;
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity forgotPassword(@RequestBody String mail) throws  IOException {
		HttpStatus httpStatus;
		ObjectNode node = objectMapper.readValue(mail, ObjectNode.class);
		
		if(node.get("mail") != null) {
			User currentUser = userService.getByEmail(node.get("mail").textValue());
			VerificationToken token = tokenService.createToken(currentUser, VerificationToken.TokenType.FORGOT_PASS);
			tokenService.setPreviousTokensExpired(token);
			tokenService.saveToken(token);
			
			Map<String, Object> model = new HashMap<>();
			model.put("email", currentUser.getEmail());
			model.put("name", currentUser.getFirstName());
			model.put("link", "http://localhost:8050/#/forgotPassword/" + token.getToken());
			mailService.sendEmail(new ForgotMessagePreparator(), model);
			httpStatus = HttpStatus.OK;
		} else {
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity(httpStatus);
	}     
	
	@GetMapping("/forgotPassword/{token}")
	public ResponseEntity changePassword(@PathVariable String token) {
		VerificationToken verificationToken = tokenService.getToken(token);
		
		if (!tokenService.isTokenValid(verificationToken, VerificationToken.TokenType.FORGOT_PASS)) {
            return ResponseEntity.badRequest().body(prepareMessageDto("invalid_link"));
        } else if (tokenService.isTokenExpired(verificationToken)) {
            return ResponseEntity.status(HttpStatus.GONE).body(prepareMessageDto("expired_link"));
        } else {
            User user = verificationToken.getUser();

            setTokenStatusExpired(verificationToken);
            authenticateUser(verificationToken.getUser());

            return ResponseEntity.ok().build();
        }
	}
	
	@PostMapping("/forgotPassword/{token}")
	public ResponseEntity changePassword(@PathVariable String token, @Valid @RequestBody NewPasswordDto dto, BindingResult bindingResult) {
		VerificationToken verificationToken = tokenService.getToken(token);
		if(!isPasswordConfirmed(dto)) 
			return ResponseEntity.badRequest().build();
		
		User currentuser = verificationToken.getUser();
		currentuser.setPassword(dto.getPassword());
		userService.updateUserProfile(currentuser);
			
		return ResponseEntity.ok().build();
	}
	
	
	private void setTokenStatusExpired(VerificationToken verificationToken) {
        verificationToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        tokenService.updateToken(verificationToken);
    }
	
	private MessageDto prepareMessageDto(String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setError(message);
        return  messageDto;
    }
	
	private void authenticateUser(User user) {
		String username = user.getEmail();
        String credentials = user.getPassword();

        Authentication auth = authenticationProvider
        		.authenticate(new UsernamePasswordAuthenticationToken(username, credentials));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
	
	
	private boolean isPasswordConfirmed(NewPasswordDto dto) {
        return dto.getPassword().equals(dto.getConfirm());
    }
}
