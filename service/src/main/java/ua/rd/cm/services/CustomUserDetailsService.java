package ua.rd.cm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * @author Yaroslav_Revin
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ua.rd.cm.domain.User user = userService.getByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("{\"error\":\"login_auth_err\"}");
        }
        UserDetails userDetails = new User(user.getEmail(), user.getPassword(), user.getUserRoles());
        return userDetails;
    }
}
