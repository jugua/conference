package ua.rd.cm.web.controller;

import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.services.ConferenceService;
import ua.rd.cm.web.controller.dto.ConferenceDto;
import ua.rd.cm.web.controller.dto.ConferenceDtoBasic;
import ua.rd.cm.web.controller.dto.MessageDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
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
    @GetMapping("/upcoming")
    public ResponseEntity getUpcomingConferences(HttpServletRequest request) {
        if(request.getUserPrincipal() == null){
            MessageDto message = new MessageDto();
            message.setError("unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
        List<Conference> conferences = conferenceService.findUpcoming();
        return getResponseEntityConferencesByRole(request, conferences);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/past")
    public ResponseEntity getPastConferences(HttpServletRequest request) {
        if(request.getUserPrincipal() == null){
            MessageDto message = new MessageDto();
            message.setError("unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
        List<Conference> conferences = conferenceService.findPast();
        return getResponseEntityConferencesByRole(request, conferences);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public ResponseEntity newConference(@Valid @RequestBody ConferenceDto dto, BindingResult bindingResult,
                                        HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
        Conference conference = conferenceDtoToConference(dto);
        // TODO: check conferenceDto
        conferenceService.save(conference);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity updateConference(@Valid @RequestBody ConferenceDto dto, BindingResult bindingResult, HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
        Conference conference = conferenceDtoToConference(dto);
        // TODO: check conferenceDto
        conferenceService.update(conference);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }


    private ResponseEntity getResponseEntityConferencesByRole(HttpServletRequest request, List<Conference> conferences) {
        if (request.isUserInRole(Role.ADMIN) || request.isUserInRole(Role.ORGANISER)) {
            List<ConferenceDto> conferencesDto = conferenceListToDto(conferences);
            return new ResponseEntity<>(conferencesDto, HttpStatus.OK);
        }
        List<ConferenceDtoBasic> conferenceDtoBasics = conferenceListToDtoBasic(conferences);
        return new ResponseEntity<>(conferenceDtoBasics, HttpStatus.OK);
    }

    private ConferenceDtoBasic conferenceToDtoBasic(Conference conference) {
        ConferenceDtoBasic conferenceDtoBasic = mapper.map(conference, ConferenceDtoBasic.class);
        conferenceDtoBasic.setConferenceInPast(isConferenceInPast(conference));
        return conferenceDtoBasic;
    }

    private List<ConferenceDtoBasic> conferenceListToDtoBasic(List<Conference> conferences) {
        List<ConferenceDtoBasic> conferenceDtoBasics = new ArrayList<>();
        if (conferences != null) {
            for (Conference conf : conferences) {
                conferenceDtoBasics.add(conferenceToDtoBasic(conf));
            }
        }
        return conferenceDtoBasics;
    }

    private ConferenceDto conferenceToDto(Conference conference) {
        ConferenceDto conferenceDto = mapper.map(conference, ConferenceDto.class);
        conferenceDto.setConferenceInPast(isConferenceInPast(conference));
        if (conference.getTalks() != null) {
            Map<String, Integer> talks = new HashMap<>();
            for (Talk talk : conference.getTalks()) {
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
        }
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
        Conference conference = mapper.map(conferenceDto, Conference.class);
        return conference;
    }

    private boolean isConferenceInPast(Conference  conference){
        return conference.getCallForPaperEndDate() != null &&
            conference.getCallForPaperEndDate().isBefore(LocalDate.now());
    }
}
