package ua.rd.cm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yaroslav_Revin
 */

public class IndexController {

    @RequestMapping("/api/login")
    public String login(){
        return "redirect:/";
    }
}
