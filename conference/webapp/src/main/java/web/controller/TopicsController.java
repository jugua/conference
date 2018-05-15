package web.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import service.businesslogic.api.TopicService;
import service.businesslogic.dto.CreateTopicDto;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.TopicDto;

@Log4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TopicsController {

    private final TopicService topicService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("topics")
    public ResponseEntity<List<TopicDto>> getTopics() {
        return new ResponseEntity<>(topicService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/topic")
    public ResponseEntity<MessageDto> create(@Valid @RequestBody CreateTopicDto topicDto) {
        Long id = topicService.save(topicDto);
        return ok(new MessageDto(id));
    }

}
