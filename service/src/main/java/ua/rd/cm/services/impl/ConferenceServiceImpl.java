package ua.rd.cm.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.dto.CreateConferenceDto;
import ua.rd.cm.repository.ConferenceRepository;
import ua.rd.cm.repository.specification.AndSpecification;
import ua.rd.cm.repository.specification.OrSpecification;
import ua.rd.cm.repository.specification.conference.ConferenceById;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateEarlierThanNow;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateIsNull;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateLaterOrEqualToNow;
import ua.rd.cm.services.ConferenceService;
import ua.rd.cm.services.exception.ConferenceNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ConferenceServiceImpl implements ConferenceService {
    private final ModelMapper modelMapper;
    private final ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceServiceImpl(ModelMapper modelMapper, ConferenceRepository conferenceRepository) {
        this.modelMapper = modelMapper;
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Conference findById(Long id) {
        List<Conference> results = conferenceRepository.findBySpecification(new ConferenceById(id));
        if (results.isEmpty()) {
            throw new ConferenceNotFoundException();
        }
        return results.get(0);
    }

    @Override
    public void save(CreateConferenceDto dto) {
        Conference conference = modelMapper.map(dto, Conference.class);
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
    @Transactional(readOnly = true)
    public List<Conference> findAll() {
        return conferenceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Conference> findPast() {
        return conferenceRepository.findBySpecification(
                new AndSpecification<>(
                        new ConferenceEndDateIsNull(false),
                        new ConferenceEndDateEarlierThanNow()
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
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
                    isActive = (conference.getCallForPaperEndDate().isAfter(now) || conference.getCallForPaperEndDate().isEqual(now))
                            && (conference.getCallForPaperStartDate().isBefore(now) || conference.getCallForPaperStartDate().isEqual(now));
                } else {
                    isActive = true;
                }

                conference.setCallForPaperActive(isActive);
            }
        }
    }
}
