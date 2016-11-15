package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.RegistrationDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private ModelMapper mapper;
    private UserService userService;

    @Autowired
    public UserController(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegistrationDto dto, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors() || !isPasswordConfirmed(dto)){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (userService.isEmailExist(dto.getEmail())){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        userService.save(dtoToEntity(dto));
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    private User dtoToEntity(RegistrationDto dto) {
        return mapper.map(dto, User.class);
    }

    private boolean isPasswordConfirmed(RegistrationDto dto) {
        return dto.getPassword().equals(dto.getConfirm());
    }
}
