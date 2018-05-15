package web.controller;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import domain.model.Talk;
import domain.model.TalkStatus;
import domain.model.User;
import service.businesslogic.api.CommentService;
import service.businesslogic.api.TalkService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.CommentDto;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.Submission;
import service.businesslogic.dto.TalkDto;
import service.businesslogic.dto.TalkStatusDto;

@Log4j
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TalksController {

    private final UserService userService;
    private final TalkService talkService;
    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talks/{talkId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("talkId") long talkId) {
        List<CommentDto> comments = commentService.getAllByTalkId(talkId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/talks/{talkId}/comments")
    public ResponseEntity<MessageDto> saveComment(@PathVariable("talkId") long talkId,
                                                  @RequestBody CommentDto commentDto, BindingResult binding) {
        if (binding.hasFieldErrors()) {
            return badRequest().body(new MessageDto("fields_error"));
        }
        commentService.save(commentDto);
        MessageDto messageDto = new MessageDto();
        messageDto.setResult("successfully_updated");
        return ok(messageDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/talks/{talkId}/comments/{commentId}")
    public ResponseEntity<MessageDto> updateComment(@PathVariable("talkId") long talkId,
                                                    @PathVariable("commentId") long commentId,
                                                    @RequestBody CommentDto commentDto, BindingResult binding) {
        if (binding.hasFieldErrors()) {
            return badRequest().body(new MessageDto("fields_error"));
        }
        if (commentService.findById(commentId) == null) {
            return badRequest().body(new MessageDto("fields_error"));
        }
        commentDto.setId(commentId);
        commentService.update(commentDto);
        MessageDto messageDto = new MessageDto();
        messageDto.setResult("successfully_updated");
        return ok(messageDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talk/talksTitles")
    public ResponseEntity<List<String>> getTalksTitles() {
        List<String> talksTitles = talkService.getAll().stream().map(Talk::getTitle).collect(Collectors.toList());
        return ok(talksTitles);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talk/talksStatus")
    public ResponseEntity<List<TalkStatusDto>> getTalksStatus() {
        List<TalkStatusDto> talksStatus = Arrays.asList(TalkStatus.values())
                .stream()
                .map(m -> new TalkStatusDto(Long.valueOf(m.ordinal()), m.getName()))
                .collect(Collectors.toList());
        return ok(talksStatus);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/talk")
    public ResponseEntity<List<Submission>> getSumbissions(HttpServletRequest request) {

        List<Submission> userTalkDtoList = talkService.getSubmissions(request.getRemoteUser());
        log.debug(userTalkDtoList);

        return ok(userTalkDtoList);
    }

    @GetMapping("/talk/{talkId}")
    public ResponseEntity<TalkDto> getTalkById(@PathVariable Long talkId, HttpServletRequest request) {
        User user = userService.findUserByEmail(request.getRemoteUser());
        if (user == null || !user.isOrganizerForTalk(talkId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        TalkDto talkDto = talkService.findById(talkId);
        return ok(talkDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/talk")
    public ResponseEntity<MessageDto> updateTalkStatus(@RequestBody TalkStatusDto dto,
                                                       BindingResult bindingResult,
                                                       HttpServletRequest request) {
        if (bindingResult.hasFieldErrors()) {
            return badRequest().body(new MessageDto("fields_error"));
        }
        User user = userService.findUserByEmail(request.getRemoteUser());
        if (user == null || !user.isOrganizerForTalk(dto.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        talkService.updateStatus(dto);
        MessageDto messageDto = new MessageDto();
        messageDto.setResult("successfully_updated");
        return ok(messageDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/talk/{id}")
    public ResponseEntity<MessageDto> updateTalk(@PathVariable("id") Long talkId,
                                                 @RequestBody TalkDto dto,
                                                 BindingResult bindingResult,
                                                 HttpServletRequest request) {
        if (bindingResult.hasFieldErrors()) {
            return badRequest().body(new MessageDto("fields_error"));
        }
        if (userHasNoRequiredRoles(request)) {
            return new ResponseEntity<>(new MessageDto("unauthorized"), HttpStatus.UNAUTHORIZED);
        }

        dto.setId(talkId);
        if (request.isUserInRole("ORGANISER")) {
            talkService.updateAsOrganiser(dto, userService.getByEmail(request.getRemoteUser()));
        } else {
            talkService.updateAsSpeaker(dto, userService.getByEmail(request.getRemoteUser()));
        }
        MessageDto messageDto = new MessageDto();
        messageDto.setResult("successfully_updated");
        return ok(messageDto);
    }

    private boolean userHasNoRequiredRoles(HttpServletRequest request) {
        return !request.isUserInRole("ORGANISER") && !request.isUserInRole("SPEAKER");
    }

}
