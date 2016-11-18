package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public void signIn(){
    }

}
