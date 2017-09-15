package ua.rd.cm.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.dto.UserBasicDto;
import ua.rd.cm.services.businesslogic.UserService;

@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/manageUsers")
@Log4j
public class ManageUsersController {
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getAllUsersForAdmin")
    public ResponseEntity getAllUsersForAdmin(HttpServletRequest request) {
        MessageDto message = new MessageDto();
        User currentUser = getAuthorizedUser(request);
        if (currentUser == null) {
            message.setError("unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
        List<UserBasicDto> userDtoList = userService.getUserBasicDtoByRoleExpectCurrent(
                currentUser, Role.ORGANISER, Role.SPEAKER);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    private User getAuthorizedUser(HttpServletRequest request) {
        boolean inRole = request.isUserInRole(Role.ADMIN);
        if (inRole) {
            String userEmail = request.getUserPrincipal().getName();
            User user = userService.getByEmail(userEmail);
            if ((user != null) && (user.getEmail() != null)) {
                return user;
            }
        }
        return null;
    }
}
