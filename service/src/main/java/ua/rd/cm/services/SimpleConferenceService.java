package ua.rd.cm.services;

import org.springframework.stereotype.Service;
import ua.rd.cm.domain.Conference;

import java.util.List;

/**
 * Created by Anastasiia_Milinchuk on 2/8/2017.
 */
@Service
public class SimpleConferenceService implements ConferenceService {
    @Override
    public void save(Conference conference) {
        
    }

    @Override
    public void update(Conference conference) {

    }

    @Override
    public void remove(Conference conference) {

    }

    @Override
    public List<Conference> findAll() {
        return null;
    }

    @Override
    public List<Conference> findPast() {
        return null;
    }

    @Override
    public List<Conference> findUpcoming() {
        return null;
    }
}
