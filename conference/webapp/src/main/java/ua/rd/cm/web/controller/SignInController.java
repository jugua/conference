package ua.rd.cm.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.dto.NewPasswordDto;
import ua.rd.cm.services.businesslogic.UserService;
import ua.rd.cm.services.businesslogic.impl.VerificationTokenService;

import javax.validation.Valid;

@RestController
@RequestMapping
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class SignInController {

    private WithTokenGetRequestProcessor withTokenGetRequestProcessor;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenService tokenService;
    private UserService userService;

    @PostMapping("/login")
    public void signIn() {
    }

    @GetMapping("/changePassword/{token}")
    public ResponseEntity changePassword(@PathVariable String token) {
        return withTokenGetRequestProcessor.process(
                token, VerificationToken.TokenType.FORGOT_PASS, verificationToken -> {
                    //NOP
                });
    }

    @PostMapping("/changePassword/{token}")
    public ResponseEntity changePassword(@PathVariable String token, @Valid @RequestBody NewPasswordDto dto,
                                         BindingResult bindingResult) {
        VerificationToken verificationToken = tokenService.getToken(token);
        if (!isPasswordConfirmed(dto))
            return ResponseEntity.badRequest().build();

        User currentUser = verificationToken.getUser();
        currentUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        userService.updateUserProfile(currentUser);

        return ResponseEntity.ok().build();
    }

    private boolean isPasswordConfirmed(NewPasswordDto dto) {
        return dto.getPassword().equals(dto.getConfirm());
    }
}