package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.ConferenceRepository;
import ua.rd.cm.repository.specification.AndSpecification;
import ua.rd.cm.repository.specification.OrSpecification;
import ua.rd.cm.repository.specification.conference.ConferenceById;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateIsNull;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateEarlierThanNow;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateLaterOrEqualToNow;
import ua.rd.cm.services.ConferenceService;
import ua.rd.cm.services.exception.ConferenceNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public Conference findById(Long id) {
        List<Conference> results = conferenceRepository.findBySpecification(new ConferenceById(id));
        if (results.isEmpty()) {
            throw new ConferenceNotFoundException();
        }
        return results.get(0);
    }

    @Override
    public void save(Conference conference) {
        conferenceRepository.save(conference);
    }

    @Override
    public void update(Conference conference) {
        conferenceRepository.update(conference);
    }

    @Override
    public void remove(Conference conference) {
        conferenceRepository.remove(conference);
    }

    @Override
    public List<Conference> findAll() {
        return conferenceRepository.findAll();
    }

    @Override
    public List<Conference> findPast() {
        return conferenceRepository.findBySpecification(
                new AndSpecification<>(
                        new ConferenceEndDateIsNull(false),
                        new ConferenceEndDateEarlierThanNow()
                )
        );
    }

    @Override
    public List<Conference> findUpcoming() {
        List<Conference> conferences = conferenceRepository.findBySpecification(
                new OrSpecification<>(
                        new ConferenceEndDateIsNull(true),
                        new ConferenceEndDateLaterOrEqualToNow()
                )
            );
        fillCallForPaperDatesActive(conferences);
        return conferences;
    }

    private void fillCallForPaperDatesActive(List<Conference> conferences) {
        if (conferences != null) {
            for (Conference conference : conferences) {
                LocalDate now = LocalDate.now();
                boolean isActive;
                if (conference.getCallForPaperEndDate() != null && conference.getCallForPaperStartDate() != null) {
                    isActive = (conference.getCallForPaperEndDate().isAfter(now))
                            && (conference.getCallForPaperStartDate().isBefore(now));
                } else {
                    isActive = true;
                }

                conference.setCallForPaperActive(isActive);
            }
        }
    }
}
