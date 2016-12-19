package ua.rd.cm.web.security;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.CustomUserDetailsService;
import ua.rd.cm.services.UserService;


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

        UserDetails user = createUserDetails(currentUser);

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("{\"error\":\"password_auth_err\"}");
        }

        if (!isUserAccountConfirmed(currentUser)) {
            throw new BadCredentialsException("{\"error\":\"confirm_reg\"}");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

    private UserDetails createUserDetails(User user) {
        return  new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getUserRoles());
    }

    private boolean isUserAccountConfirmed(User currentUser) {
        return currentUser.getStatus().equals(User.UserStatus.CONFIRMED);
    }
}
