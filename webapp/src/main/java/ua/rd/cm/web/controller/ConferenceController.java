package ua.rd.cm.web.controller;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.services.ConferenceService;
import ua.rd.cm.services.TalkService;
import ua.rd.cm.web.controller.dto.ConferenceDto;
import ua.rd.cm.web.controller.dto.MessageDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anastasiia_Milinchuk on 2/8/2017.
 */
@Log4j
@RestController
@RequestMapping("/api/conference")
public class ConferenceController {
    private ConferenceService conferenceService;
    private ModelMapper mapper;

    @Autowired
    public ConferenceController(ConferenceService conferenceService, ModelMapper mapper) {
        this.conferenceService = conferenceService;
        this.mapper = mapper;
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
        // TODO
        conferenceService.save(conference);

        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/conferences/update/{id}")
    public ResponseEntity updateConference(@Valid @RequestBody ConferenceDto dto, BindingResult bindingResult, HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
        Conference conference = conferenceDtoToConference(dto);
        // TODO
        conferenceService.update(conference);

        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    private ConferenceDto conferenceToDto(Conference conference) {
        ConferenceDto conferenceDto = mapper.map(conference, ConferenceDto.class);
        Map<String, Integer> talks = new HashMap<>();
        for (Talk talk: conference.getTalks()) {
            String status = talk.getStatus().getName();
            Integer count = 0;
            if (talks.get(status) != null) {
                count = talks.get(status);
            }
            talks.put(status, ++count);
        }
        conferenceDto.setNewTalkCount(talks.get(TalkStatus.NEW.getName()));
        conferenceDto.setApprovedTalkCount(talks.get(TalkStatus.APPROVED.getName()));
        conferenceDto.setRejectedTalkCount(talks.get(TalkStatus.REJECTED.getName()));
        conferenceDto.setInProgressTalkCount(talks.get(TalkStatus.IN_PROGRESS.getName()));
        return conferenceDto;
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
