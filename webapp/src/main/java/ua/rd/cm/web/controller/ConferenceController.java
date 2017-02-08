package ua.rd.cm.web.controller;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.services.ConferenceService;
import ua.rd.cm.services.TalkService;
import ua.rd.cm.web.controller.dto.ConferenceDto;
import ua.rd.cm.web.controller.dto.MessageDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anastasiia_Milinchuk on 2/8/2017.
 */
@Log4j
@RestController
@RequestMapping("/api/conference")
public class ConferenceController {
    private ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/conferences/upcoming")
    public ResponseEntity getUpcomingConferences() {
        List<Conference> conferences = conferenceService.findUpcoming();
        List<ConferenceDto> conferencesDto = conferenceListToDto(conferences);
        return new ResponseEntity<>(conferencesDto, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/conferences/past")
    public ResponseEntity getPastConferences() {
        List<Conference> conferences = conferenceService.findPast();
        List<ConferenceDto> conferencesDto = conferenceListToDto(conferences);
        return new ResponseEntity<>(conferencesDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/conferences/new")
    public ResponseEntity newConference(@Valid @RequestBody ConferenceDto dto, BindingResult bindingResult, HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
        Conference conference = conferenceDtoToConference(dto);

        conferenceService.save(conference);

        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/conferences/update/{id}")
    public ResponseEntity updateConference(@Valid @RequestBody ConferenceDto dto, BindingResult bindingResult, HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
        Conference conference = conferenceDtoToConference(dto);

        conferenceService.update(conference);

        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    private ConferenceDto conferenceToDto(Conference conference) {
        return null;
    }

    private List<ConferenceDto> conferenceListToDto(List<Conference> conferences) {
        List<ConferenceDto> conferenceDtos = new ArrayList<>();
        if (conferences != null) {
            for (Conference conf : conferences) {
                conferenceDtos.add(conferenceToDto(conf));
            }
        }
        return conferenceDtos;
    }

    private Conference conferenceDtoToConference(ConferenceDto conferenceDto) {
        return null;
    }


}
