package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.model.Language;
import lombok.AllArgsConstructor;
import service.businesslogic.api.LanguageService;
import service.businesslogic.api.LevelService;
import service.businesslogic.api.TopicService;
import service.businesslogic.api.TypeService;
import service.businesslogic.dto.LevelDto;
import service.businesslogic.dto.TopicDto;
import service.businesslogic.dto.TypeDto;

@RestController
@RequestMapping("/")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ResourceController {
    private final TypeService typeService;
    private final TopicService topicService;
    private final LevelService levelService;
    private final LanguageService languageService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("types")
    public ResponseEntity<List<TypeDto>> getTypes() {
        return new ResponseEntity<>(typeService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("topics")
    public ResponseEntity<String[]> getTopics() {
    	String[] topics = topicService.findAll().stream().map(m -> m.getName()).toArray(String[]::new);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("levels")
    public ResponseEntity<List<LevelDto>> getLevels() {
        return new ResponseEntity<>(levelService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("languages")
    public ResponseEntity<List<Language>> getLanguages() {
        return new ResponseEntity<>(languageService.findAll(), HttpStatus.OK);
    }


}
