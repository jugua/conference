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

import service.businesslogic.api.TypeService;
import service.businesslogic.dto.CreateTypeDto;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.TypeDto;

@Log4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TalkTypesController {

    private final TypeService typeService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/types")
    public ResponseEntity<List<TypeDto>> getTypes() {
        return new ResponseEntity<>(typeService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/type")
    public ResponseEntity<MessageDto> create(@Valid @RequestBody CreateTypeDto typeDto) {
        Long id = typeService.save(typeDto);
        return ok(new MessageDto(id));
    }

}
