package ua.rd.cm.web.controller;

import lombok.AllArgsConstructor;
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
import ua.rd.cm.dto.*;
import ua.rd.cm.services.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j
@RestController
@RequestMapping("/api/")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MainPageController {
    private final ModelMapper mapper;
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
        Conference conference = conferenceDtoToConference(dto);
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
            List<ConferenceDto> conferencesDto = conferenceListToDto(conferences);
            return new ResponseEntity<>(conferencesDto, HttpStatus.OK);
        }
        List<ConferenceDtoBasic> conferenceDtoBasics = conferenceListToDtoBasic(conferences);
        return new ResponseEntity<>(conferenceDtoBasics, HttpStatus.OK);
    }

    private ConferenceDtoBasic conferenceToDtoBasic(Conference conference) {
        return mapper.map(conference, ConferenceDtoBasic.class);
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
        conferenceDto.setCallForPaperStartDate(convertDateToString(conference.getCallForPaperStartDate()));
        conferenceDto.setCallForPaperEndDate(convertDateToString(conference.getCallForPaperEndDate()));
        conferenceDto.setStartDate(convertDateToString(conference.getStartDate()));
        conferenceDto.setEndDate(convertDateToString(conference.getEndDate()));
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

    private String convertDateToString(LocalDate localDate) {
        if (localDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(formatter);
        }
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
        return mapper.map(conferenceDto, Conference.class);
    }
}

