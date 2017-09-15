package ua.rd.cm.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import ua.rd.cm.domain.User;
import ua.rd.cm.services.businesslogic.UserService;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User currentUser = userService.getByEmail(username);

        if (currentUser == null) {
            throw new BadCredentialsException("{\"error\":\"login_auth_err\"}");
        }

        if (!userService.isAuthenticated(currentUser, password)) {
            throw new BadCredentialsException("{\"error\":\"password_auth_err\"}");
        }

        if (!isUserAccountConfirmed(currentUser)) {
            throw new BadCredentialsException("{\"error\":\"confirm_reg\"}");
        }
        return AuthenticationFactory.createAuthentication(currentUser);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

    private boolean isUserAccountConfirmed(User currentUser) {
        return currentUser.getStatus().equals(User.UserStatus.CONFIRMED);
    }
}
