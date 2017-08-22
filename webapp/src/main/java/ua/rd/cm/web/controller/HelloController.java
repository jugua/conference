package ua.rd.cm.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/helloworld")
public class HelloController {

    @GetMapping
    @ResponseBody
    public String[] hello(){
        return new String[]{"helloworld"};
    }
}
