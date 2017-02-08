package ua.rd.cm.services;

import ua.rd.cm.domain.Conference;

import java.util.List;

/**
 * Created by Anastasiia_Milinchuk on 2/8/2017.
 */
public interface ConferenceService {
    void save(Conference conference);

    void update(Conference conference);

    void remove(Conference conference);

    List<Conference> findAll();

    List<Conference> findPast();

    List<Conference> findUpcoming();
}
