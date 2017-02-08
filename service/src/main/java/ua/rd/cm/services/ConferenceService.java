package ua.rd.cm.services;

import ua.rd.cm.domain.Conference;

import java.util.List;

public interface ConferenceService {

    Conference findById(Long id);

    void save(Conference conference);

    void update(Conference conference);

    void remove(Conference conference);

    List<Conference> findAll();

    List<Conference> findPast();

    List<Conference> findOpen();

    List<Conference> findOngoing();
}
