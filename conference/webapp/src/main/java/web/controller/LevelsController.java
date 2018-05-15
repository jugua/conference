package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import service.businesslogic.api.LevelService;
import service.businesslogic.dto.LevelDto;

@RestController
@RequestMapping("/levels")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class LevelsController {

    private final LevelService levelService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<LevelDto>> getLevels() {
        return new ResponseEntity<>(levelService.findAll(), HttpStatus.OK);
    }

}
