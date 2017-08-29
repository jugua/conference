package ua.rd.cm.web.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.dto.*;
import ua.rd.cm.services.ConferenceService;
import ua.rd.cm.services.TopicService;
import ua.rd.cm.services.TypeService;


@Log4j
@RestController
@RequestMapping("/api/")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MainPageController {

    private final TypeService typeService;
    private final TopicService topicService;
    private final ConferenceService conferenceService;

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

    private ResponseEntity responseEntityConferencesByRole(HttpServletRequest request, List<Conference> conferences) {
        if (request.isUserInRole(Role.ADMIN) || request.isUserInRole(Role.ORGANISER)) {
            List<ConferenceDto> conferencesDto = conferenceService.conferenceListToDto(conferences);
            return new ResponseEntity<>(conferencesDto, HttpStatus.OK);
        }
        List<ConferenceDtoBasic> conferenceDtoBasics = conferenceService.conferenceListToDtoBasic(conferences);
        return new ResponseEntity<>(conferenceDtoBasics, HttpStatus.OK);
    }


}

