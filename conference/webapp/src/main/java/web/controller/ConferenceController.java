package web.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.services.businesslogic.ConferenceService;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/conference")
public class ConferenceController {
	
	private ConferenceService conferenceService;

	@PreAuthorize("isAuthenticated()")
    @GetMapping("/conferencesNames")
    public ResponseEntity<List<String>> getUsersNames(){
    	List<String> conferencesNames = conferenceService.findAll()
    													 .stream()
    													 .map(m -> m.getTitle())
    													 .collect(Collectors.toList());
		return new ResponseEntity<>(conferencesNames,HttpStatus.OK);
    }
    
}
