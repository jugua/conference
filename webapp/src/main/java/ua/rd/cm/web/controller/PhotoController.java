package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.rd.cm.services.UserService;

@RestController
@RequestMapping("/api/user/current/photo")
public class PhotoController {
    private ModelMapper mapper;
    private UserService userService;

    @Autowired
    public PhotoController(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

}
