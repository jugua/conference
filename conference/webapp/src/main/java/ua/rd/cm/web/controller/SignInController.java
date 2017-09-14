package ua.rd.cm.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SignInController {

    @PostMapping("/login")
    public void signIn() {
    }
}