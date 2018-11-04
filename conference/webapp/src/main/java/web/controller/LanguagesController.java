package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import domain.model.Language;
import service.businesslogic.api.LanguageService;

@RestController
@RequestMapping("/languages")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LanguagesController {

    private final LanguageService languageService;

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Language>> getLanguages() {
        return new ResponseEntity<>(languageService.getAll(), HttpStatus.OK);
    }

}
