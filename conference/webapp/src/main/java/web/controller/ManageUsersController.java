package web.controller;

import static org.springframework.http.ResponseEntity.ok;

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

import domain.model.Role;
import domain.model.User;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.UserBasicDto;

@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/manageUsers")
@Log4j
public class ManageUsersController {
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getAllUsersForAdmin")
    public ResponseEntity getAllUsersForAdmin(HttpServletRequest request) {
        User currentUser = getAuthorizedUser(request);
        if (currentUser == null) {
            return new ResponseEntity<>(new MessageDto("unauthorized"), HttpStatus.UNAUTHORIZED);
        }
        List<UserBasicDto> userDtoList = userService.getUserBasicDtoByRoleExpectCurrent(
                currentUser, Role.ROLE_ORGANISER, Role.ROLE_SPEAKER);
        return ok(userDtoList);
    }

    private User getAuthorizedUser(HttpServletRequest request) {
        boolean inRole = request.isUserInRole(Role.ROLE_ADMIN);
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
