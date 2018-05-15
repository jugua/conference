package web.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import domain.model.Conference;
import domain.model.User;
import service.businesslogic.api.ConferenceService;
import service.businesslogic.api.TalkService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.ConferenceDto;
import service.businesslogic.dto.ConferenceDtoBasic;
import service.businesslogic.dto.CreateConferenceDto;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.TalkDto;

@Log4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/conference")
public class ConferencesController {

    private final UserService userService;
    private final ConferenceService conferenceService;
    private final TalkService talkService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/conferencesNames")
    public ResponseEntity<List<String>> getConferenceNames() {
        List<String> conferencesNames = conferenceService.getAll()
                .stream()
                .map(Conference::getTitle)
                .collect(Collectors.toList());
        return new ResponseEntity<>(conferencesNames, HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ConferenceDtoBasic>> upcomingConferences(HttpServletRequest request) {
        return ok(conferenceService.getUpcomingBasic());
    }

    @GetMapping("/past")
    public ResponseEntity<List<ConferenceDtoBasic>> pastConferences(HttpServletRequest request) {
        return ok(conferenceService.getPastBasic());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<Conference> conferenceById(@PathVariable long id) {
        Conference conference = conferenceService.getById(id);
        return ok(conference);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<ConferenceDto>> conferenceById(HttpServletRequest request) {
        User user = userService.getByEmail(request.getRemoteUser());
        List<ConferenceDto> conferences = conferenceService.conferenceToDto(user.getOrganizerConferences());
        return ok(conferences);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/talks")
    public ResponseEntity<Collection<TalkDto>> talksByConferenceId(@PathVariable long id) {
        Collection<TalkDto> talkDtos = talkService.getByConferenceId(id);
        return ok(talkDtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MessageDto> newConference(@Valid @RequestBody CreateConferenceDto dto) {
        Long id = conferenceService.save(dto);
        return ok(new MessageDto(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update")
    public ResponseEntity<MessageDto> updateConference(@Valid @RequestBody ConferenceDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ok(new MessageDto(dto.getId(), "field_error"));
        } else {
            conferenceService.update(dto);
            return ok(new MessageDto(dto.getId(), "successfully_updated"));
        }
    }

}
