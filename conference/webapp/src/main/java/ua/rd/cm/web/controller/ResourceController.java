package ua.rd.cm.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.rd.cm.services.businesslogic.TopicService;
import ua.rd.cm.services.businesslogic.TypeService;
import ua.rd.cm.services.resources.LanguageService;
import ua.rd.cm.services.resources.LevelService;

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
    public ResponseEntity getTypes() {
        return new ResponseEntity<>(typeService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("topics")
    public ResponseEntity getTopics() {
        return new ResponseEntity<>(topicService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("levels")
    public ResponseEntity getLevels() {
        return new ResponseEntity<>(levelService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("languages")
    public ResponseEntity getLanguages() {
        return new ResponseEntity<>(languageService.findAll(), HttpStatus.OK);
    }


}
