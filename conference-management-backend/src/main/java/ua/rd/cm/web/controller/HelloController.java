package ua.rd.cm.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/helloworld")
public class HelloController {

    @GetMapping
    @ResponseBody
    public String[] hello(){
        return new String[]{"helloworld"};
    }
}
