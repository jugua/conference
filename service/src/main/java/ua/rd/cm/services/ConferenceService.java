package ua.rd.cm.services;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.dto.CreateConferenceDto;

import java.util.List;

public interface ConferenceService {

    Conference findById(Long id);

    Long save(CreateConferenceDto conference);

    void update(Conference conference);

    void remove(Conference conference);

    List<Conference> findAll();

    List<Conference> findPast();

    List<Conference> findUpcoming();
}
