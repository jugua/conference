package ua.rd.cm.services.businesslogic.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.dto.ConferenceDto;
import ua.rd.cm.dto.ConferenceDtoBasic;
import ua.rd.cm.dto.CreateConferenceDto;
import ua.rd.cm.repository.ConferenceRepository;
import ua.rd.cm.services.businesslogic.ConferenceService;
import ua.rd.cm.services.exception.ConferenceNotFoundException;


@Service
@Transactional
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ConferenceServiceImpl implements ConferenceService {

    private final ModelMapper modelMapper;
    private final ConferenceRepository conferenceRepository;

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
        List<Conference> conferences = conferenceRepository.findAllByStartDateIsGreaterThanEqual(LocalDate.now());
        fillCallForPaperDatesActive(conferences);
        return conferences;
    }


    public ConferenceDtoBasic conferenceToDtoBasic(Conference conference) {
        return modelMapper.map(conference, ConferenceDtoBasic.class);
    }

    public List<ConferenceDtoBasic> conferenceListToDtoBasic(List<Conference> conferences) {
        List<ConferenceDtoBasic> conferenceDtoBasics = new ArrayList<>();
        if (conferences != null) {
            for (Conference conf : conferences) {
                conferenceDtoBasics.add(conferenceToDtoBasic(conf));
            }
        }
        return conferenceDtoBasics;
    }

    public ConferenceDto conferenceToDto(Conference conference) {
        ConferenceDto conferenceDto = modelMapper.map(conference, ConferenceDto.class);
        conferenceDto.setCallForPaperStartDate(convertDateToString(conference.getCallForPaperStartDate()));
        conferenceDto.setCallForPaperEndDate(convertDateToString(conference.getCallForPaperEndDate()));
        conferenceDto.setStartDate(convertDateToString(conference.getStartDate()));
        conferenceDto.setEndDate(convertDateToString(conference.getEndDate()));
        if (conference.getTalks() != null) {
            Map<String, Integer> talks = new HashMap<>();
            for (Talk talk : conference.getTalks()) {
                String status = talk.getStatus().getName();
                Integer count = 0;
                if (talks.get(status) != null) {
                    count = talks.get(status);
                }
                talks.put(status, ++count);
            }

            conferenceDto.setNewTalkCount(talks.get(TalkStatus.NEW.getName()));
            conferenceDto.setApprovedTalkCount(talks.get(TalkStatus.APPROVED.getName()));
            conferenceDto.setRejectedTalkCount(talks.get(TalkStatus.REJECTED.getName()));
            conferenceDto.setInProgressTalkCount(talks.get(TalkStatus.IN_PROGRESS.getName()));
        }
        return conferenceDto;
    }

    public String convertDateToString(LocalDate localDate) {
        if (localDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(formatter);
        }
        return null;
    }

    public List<ConferenceDto> conferenceListToDto(List<Conference> conferences) {
        List<ConferenceDto> conferenceDtos = new ArrayList<>();
        if (conferences != null) {
            for (Conference conf : conferences) {
                conferenceDtos.add(conferenceToDto(conf));
            }
        }
        return conferenceDtos;
    }

    public Conference conferenceDtoToConference(ConferenceDto conferenceDto) {
        return modelMapper.map(conferenceDto, Conference.class);
    }

    private void fillCallForPaperDatesActive(List<Conference> conferences) {
        if (conferences != null) {
            for (Conference conference : conferences) {
                LocalDate now = LocalDate.now();
                LocalDate callForPaperEndDate = conference.getCallForPaperEndDate();

                boolean isActive;
                if (callForPaperEndDate != null && conference.getCallForPaperStartDate() != null) {
                    isActive = (callForPaperEndDate.isAfter(now) || callForPaperEndDate.isEqual(now))
                            && (conference.getCallForPaperStartDate().isBefore(now)
                            || conference.getCallForPaperStartDate().isEqual(now));
                } else {
                    isActive = true;
                }

                conference.setCallForPaperActive(isActive);
            }
        }
    }
}
