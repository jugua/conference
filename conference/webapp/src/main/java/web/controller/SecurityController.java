package web.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import service.businesslogic.api.SignInService;
import service.businesslogic.dto.LoginDto;

@RestController
@RequestMapping
public class SecurityController {

    @Autowired
    private SignInService signInService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto> signIn(@RequestHeader(value = "authorization") String authorizationData) {
        String email = extractEmailFrom(decodeBase64(authorizationData));
        LoginDto loginDto = signInService.login(email, null);
        return new ResponseEntity<>(loginDto, HttpStatus.OK);
    }

    private String decodeBase64(String message) {
        String[] values = message.split(" ");
        byte[] valueDecoded = Base64.getDecoder().decode(values[1]);
        String decodedString = new String(valueDecoded);
        return decodedString;
    }

    private String extractEmailFrom(String message) {
        return message.split(":")[0];
    }

}