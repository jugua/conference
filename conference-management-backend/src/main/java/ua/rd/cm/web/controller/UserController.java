package ua.rd.cm.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.User;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody User user, BindingResult bindingResult){

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @RequestMapping("/current")
    public String current(){
        return "lol";
    }
}
