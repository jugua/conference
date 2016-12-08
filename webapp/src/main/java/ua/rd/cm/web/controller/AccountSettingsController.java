package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Olha_Melnyk
 */
@RestController
@RequestMapping("/api/user/current")
public class AccountSettingsController {
    private ModelMapper mapper;

}
