package ua.rd.cm.services;

import java.util.List;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.dto.CreateConferenceDto;

public interface ConferenceService {

    Conference findById(Long id);

    Long save(CreateConferenceDto conference);

    void update(Conference conference);

    void remove(Conference conference);

    List<Conference> findAll();

    List<Conference> findPast();

    List<Conference> findUpcoming();
}
