package ua.rd.cm.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.domain.Role;
import ua.rd.cm.dto.*;
import ua.rd.cm.services.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Log4j
@RestController
@RequestMapping("/")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MainPageController {

    private final TypeService typeService;
    private final TopicService topicService;
    private final ConferenceService conferenceService;
    private final LevelService levelService;
    private final LanguageService languageService;

    @GetMapping("conference/upcoming")
    public ResponseEntity upcomingConferences(HttpServletRequest request) {
        List<Conference> conferences = conferenceService.findUpcoming();
        return responseEntityConferencesByRole(request, conferences);
    }

    @GetMapping("conference/past")
    public ResponseEntity pastConferences(HttpServletRequest request) {
        List<Conference> conferences = conferenceService.findPast();
        return responseEntityConferencesByRole(request, conferences);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("conference")
    public ResponseEntity newConference(@Valid @RequestBody CreateConferenceDto dto) {
        Long id = conferenceService.save(dto);
        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("conference/update/{id}")
    public ResponseEntity updateConference(@Valid @RequestBody ConferenceDto dto, BindingResult bindingResult, HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
        Conference conference = conferenceService.conferenceDtoToConference(dto);
        // TODO: check conferenceDto
        conferenceService.update(conference);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("type")
    public ResponseEntity createNewType(@Valid @RequestBody CreateTypeDto typeDto) {
        Long id = typeService.save(typeDto);
        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("topic")
    public ResponseEntity createNewTopic(@Valid @RequestBody CreateTopicDto topicDto) {
        Long id = topicService.save(topicDto);
        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("type")
    public ResponseEntity getTypes() {
        return new ResponseEntity<>(typeService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("topic")
    public ResponseEntity getTopics() {
        return new ResponseEntity<>(topicService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("level")
    public ResponseEntity getLevels() {
        return new ResponseEntity<>(levelService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("lang")
    public ResponseEntity getLanguages() {
        return new ResponseEntity<>(languageService.findAll(), HttpStatus.OK);
    }

    private ResponseEntity responseEntityConferencesByRole(HttpServletRequest request, List<Conference> conferences) {
        if (request.isUserInRole(Role.ADMIN) || request.isUserInRole(Role.ORGANISER)) {
            List<ConferenceDto> conferencesDto = conferenceService.conferenceListToDto(conferences);
            return new ResponseEntity<>(conferencesDto, HttpStatus.OK);
        }
        List<ConferenceDtoBasic> conferenceDtoBasics = conferenceService.conferenceListToDtoBasic(conferences);
        return new ResponseEntity<>(conferenceDtoBasics, HttpStatus.OK);
    }


}

