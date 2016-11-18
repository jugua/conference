package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Principal;

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

//    @PostMapping
//    public String signIn(@Valid @RequestBody SignInDto dto, BindingResult bindingResult){
//        System.out.println("Alah");
//        System.out.println(dto);
//        if (bindingResult.hasFieldErrors()){
//            return "redirect:/my-talk";
//        }
//        if (!userService.isEmailExist(dto.getEmail())){
//            return "redirect:/my-talk";
//        }
//        User user=userService.getByEmail(dto.getEmail());
//        if(!user.getPassword().equals(dto.getPassword())){
//            return "redirect:/my-talk";
//        }
//
//        System.out.println("USER="+user);
//        return "redirect:/#/account";
//    }

    @PostMapping
    public ResponseEntity signIn(@Valid @RequestBody SignInDto dto,
                                         BindingResult bindingResult, HttpServletRequest httpServletRequest) throws URISyntaxException {
        System.out.println("Alah");
        System.out.println(dto);
        if (bindingResult.hasFieldErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!userService.isEmailExist(dto.getEmail())){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        User user=userService.getByEmail(dto.getEmail());
        if(!user.getPassword().equals(dto.getPassword())){
            System.out.println("UNANTHORIZED in sign in");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        }
        System.out.println("USER="+user);
        Principal principal=new Principal() {
            @Override
            public String getName() {
                return user.getEmail();
            }
        };

        httpServletRequest.setAttribute("JSESSIONID",principal);

//        URI url=new URI("https://www.google.com.ua/");
//        HttpHeaders headers=new HttpHeaders();
//        headers.setLocation(url);
//        return new ResponseEntity<>(headers,HttpStatus.OK);
        return new ResponseEntity(HttpStatus.OK);
    }

}
