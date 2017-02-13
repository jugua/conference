package ua.rd.cm.web.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.rd.cm.domain.User;

import java.util.Collection;

public class AuthenticationFactory {
    public static Authentication createAuthentication(String password, User user) {
        UserDetails userDetails = createUserDetails(user);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user.getEmail(), password, authorities);
    }

    private static UserDetails createUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.getUserRoles()
        );
    }
}
