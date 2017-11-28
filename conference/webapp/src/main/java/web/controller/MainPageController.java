package web.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import domain.model.Conference;
import domain.model.Role;
import domain.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import service.businesslogic.api.ConferenceService;
import service.businesslogic.api.TopicService;
import service.businesslogic.api.TypeService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.ConferenceDto;
import service.businesslogic.dto.CreateConferenceDto;
import service.businesslogic.dto.CreateTopicDto;
import service.businesslogic.dto.CreateTypeDto;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.TalkDto;

@Log4j
@RestController
@RequestMapping("/")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MainPageController {

    private final TypeService typeService;
    private final TopicService topicService;
    private final ConferenceService conferenceService;
    private UserService userService;

    @GetMapping("conference/upcoming")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity upcomingConferences(HttpServletRequest request) {
        if (request.isUserInRole(Role.ADMIN) || request.isUserInRole(Role.ORGANISER)) {
            return ok(conferenceService.findUpcoming());
        }
        return ok(conferenceService.findUpcomingBasic());
    }

    @GetMapping("conference/past")
    public ResponseEntity pastConferences(HttpServletRequest request) {
        if (request.isUserInRole(Role.ADMIN) || request.isUserInRole(Role.ORGANISER)) {
            return ok(conferenceService.findPast());
        }
        return ok(conferenceService.findPastBasic());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("conference/{id}")
    public ResponseEntity<Conference> conferenceById(@PathVariable long id) {
        Conference conference = conferenceService.findById(id);
        return ok(conference);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("conference")
    public ResponseEntity<List<ConferenceDto>> conferenceById(HttpServletRequest request) {
    	User user = userService.getByEmail(request.getRemoteUser());
       	List<ConferenceDto> conferences = conferenceService.conferenceToDto(user.getOrganizerConferences());
       	return new ResponseEntity<>(conferences,HttpStatus.OK);
    	
    
    }
    

    @PreAuthorize("isAuthenticated()")
    @GetMapping("conference/{id}/talks")
    public ResponseEntity<Collection<TalkDto>> talksByConferenceId(@PathVariable long id) {
        Collection<TalkDto> talkDtos = conferenceService.findTalksByConferenceId(id);
        return ok(talkDtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("conference")
    public ResponseEntity<MessageDto> newConference(@Valid @RequestBody CreateConferenceDto dto) {
        Long id = conferenceService.save(dto);
        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        return ok(messageDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("conference/update")
    public ResponseEntity<MessageDto> updateConference(@Valid @RequestBody ConferenceDto dto, BindingResult bindingResult) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(dto.getId());

        if (bindingResult.hasErrors()) {
            messageDto.setError("field_error");
            return ok(messageDto);
        } else {
            messageDto.setError("successfully_updated");
            conferenceService.update(dto);
            return ok(messageDto);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("type")
    public ResponseEntity<MessageDto> createNewType(@Valid @RequestBody CreateTypeDto typeDto) {
        Long id = typeService.save(typeDto);
        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        return ok(messageDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("topic")
    public ResponseEntity<MessageDto> createNewTopic(@Valid @RequestBody CreateTopicDto topicDto) {
        Long id = topicService.save(topicDto);
        MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        return ok(messageDto);
    }

    private <T> ResponseEntity<T> ok(T body) {
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
