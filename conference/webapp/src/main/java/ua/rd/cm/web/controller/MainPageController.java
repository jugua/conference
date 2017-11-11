package ua.rd.cm.web.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.domain.Role;
import ua.rd.cm.dto.ConferenceDto;
import ua.rd.cm.dto.ConferenceDtoBasic;
import ua.rd.cm.dto.CreateConferenceDto;
import ua.rd.cm.dto.CreateTopicDto;
import ua.rd.cm.dto.CreateTypeDto;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.dto.TalkDto;
import ua.rd.cm.services.businesslogic.ConferenceService;
import ua.rd.cm.services.businesslogic.TopicService;
import ua.rd.cm.services.businesslogic.TypeService;

@Log4j
@RestController
@RequestMapping("/")
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("conference/{id}")
    public ResponseEntity conferenceById(@PathVariable long id) {
        Conference conference = conferenceService.findById(id);
        return ok(conference);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("conference/{id}/talks")
    public ResponseEntity talksByConferenceId(@PathVariable long id) {
        Collection<TalkDto> talkDtos = conferenceService.findTalksByConferenceId(id);
        return ok(talkDtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("conference")
    public ResponseEntity newConference(@Valid @RequestBody CreateConferenceDto dto) {
        Long id = conferenceService.save(dto);
        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        return ok(messageDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("conference/update")
    public ResponseEntity updateConference(@Valid @RequestBody ConferenceDto dto, BindingResult bindingResult) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(dto.getId());
        Conference conference = conferenceService.conferenceDtoToConference(dto);

        if (bindingResult.hasErrors()) {
            messageDto.setError("field_error");
            return ok(messageDto);
        } else {
            messageDto.setError("successfully_updated");
            conferenceService.update(conference);
            return ok(messageDto);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("type")
    public ResponseEntity createNewType(@Valid @RequestBody CreateTypeDto typeDto) {
        Long id = typeService.save(typeDto);
        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        return ok(messageDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("topic")
    public ResponseEntity createNewTopic(@Valid @RequestBody CreateTopicDto topicDto) {
        Long id = topicService.save(topicDto);
        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        return ok(messageDto);
    }

    private ResponseEntity responseEntityConferencesByRole(HttpServletRequest request, List<Conference> conferences) {
        if (request.isUserInRole(Role.ADMIN) || request.isUserInRole(Role.ORGANISER)) {
            List<ConferenceDto> conferencesDto = conferenceService.conferenceListToDto(conferences);
            return new ResponseEntity<>(conferencesDto, HttpStatus.OK);
        }
        List<ConferenceDtoBasic> conferenceDtoBasics = conferenceService.conferenceListToDtoBasic(conferences);
        return ok(conferenceDtoBasics);
    }

    private <T> ResponseEntity<T> ok(T body) {
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
