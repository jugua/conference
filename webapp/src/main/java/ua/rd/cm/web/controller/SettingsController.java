package ua.rd.cm.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.SettingsDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.io.IOException;
import java.security.Principal;

/**
 * @author Olha_Melnyk
 */
@RestController
@RequestMapping("/api/user/current")
public class SettingsController {
    private ObjectMapper mapper;
    private UserService userService;
    private Logger logger = Logger.getLogger(SettingsController.class);

    @Autowired
    public SettingsController(ObjectMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping("/password")
    public ResponseEntity changePassword(@Valid @RequestBody SettingsDto dto, Principal principal, BindingResult bindingResult, HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getByEmail(principal.getName());

        if (user == null) {
            logger.error("Request for [api/user/current/password] is failed: User entity for current principal is not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!checkCurrentPasswordMatches(dto, user)) {
            logger.error("Changing password failed: current password doesn't match user's password. [HttpServletRequest: " + request.toString() + "]");
            messageDto.setAnswer("wrong_password");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageDto);
        }
        if (!checkPasswordConfirmed(dto)) {
            logger.error("Changing password failed: confirmed password doesn't match new password. [HttpServletRequest: " + request.toString() + "]");
            messageDto.setAnswer("password_math");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messageDto);
        }
        if (bindingResult.hasFieldErrors()) {
            System.out.println("Field error: " + bindingResult.getFieldError());
            System.out.println("Field error default message: " + bindingResult.getFieldError().getDefaultMessage());
            logger.error("Request for [api/user/current/password] is failed: validation is failed. [HttpServletRequest: " + request.toString() + "]");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fields_error");
        }
        user.setPassword(dto.getNewPassword());
        userService.updateUserProfile(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/email")
    public String changeEmail(@RequestBody String mail, Principal principal) {
        String email = parseMail(mail);

        if (email == null) {

        }

        return mail;
    }

    private String parseMail(String mail) {
        try {
            JsonNode node = mapper.readValue(mail, ObjectNode.class).get("mail");

            if (node == null) {
                return null;
            }
            return node.textValue();
        } catch (IOException e) {
            return null;
        }
    }

    private boolean checkCurrentPasswordMatches(SettingsDto dto, User user) {
        return dto.getCurrentPassword().equals(user.getPassword());
    }

    private boolean checkPasswordConfirmed(SettingsDto dto) {
        return dto.getNewPassword().equals(dto.getConfirmedNewPassword());
    }
}
