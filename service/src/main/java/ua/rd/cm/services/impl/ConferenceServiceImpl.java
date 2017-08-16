package ua.rd.cm.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.dto.CreateConferenceDto;
import ua.rd.cm.repository.ConferenceRepository;
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
        Conference conference = conferenceRepository.findById(id);
        if (conference == null) {
            throw new ConferenceNotFoundException();
        }
        return conference;
    }

    @Override
    public Long save(CreateConferenceDto dto) {
        Conference conference = modelMapper.map(dto, Conference.class);
        conferenceRepository.save(conference);
        return conference.getId();
    }

    @Override
    public void update(Conference conference) {
        conferenceRepository.save(conference);
    }

    @Override
    public void remove(Conference conference) {
        conferenceRepository.delete(conference);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Conference> findAll() {
        return conferenceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Conference> findPast() {
        return conferenceRepository.findAllByEndDateIsLessThan(LocalDate.now());

    }

    @Override
    @Transactional(readOnly = true)
    public List<Conference> findUpcoming() {
        List<Conference> conferences = conferenceRepository.findAllByEndDateIsLessThan(LocalDate.now());
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
