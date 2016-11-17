package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.RegistrationDto;
import ua.rd.cm.web.controller.dto.SignInDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class SignInController {
    private ModelMapper mapper;
    private UserService userService;

    @Autowired
    public SignInController(ModelMapper mapper, UserService userService) {
        System.out.println("great");
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity signIn(@RequestBody SignInDto dto, BindingResult bindingResult){
        System.out.println("Alah");
        System.out.println(dto);
        if (bindingResult.hasFieldErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!userService.isEmailExist(dto.getEmail())){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        if(userService.getByEmail(dto.getEmail()).getPassword().equals(dto.getPassword())){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
