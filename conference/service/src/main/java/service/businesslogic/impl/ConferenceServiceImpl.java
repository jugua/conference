package service.businesslogic.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import domain.model.Conference;
import domain.model.Talk;
import domain.model.TalkStatus;
import domain.repository.ConferenceRepository;
import service.businesslogic.api.ConferenceService;
import service.businesslogic.dto.ConferenceDto;
import service.businesslogic.dto.ConferenceDtoBasic;
import service.businesslogic.dto.CreateConferenceDto;
import service.businesslogic.dto.TalkDto;
import service.businesslogic.dto.converter.TalksConverter;
import service.businesslogic.exception.ConferenceNotFoundException;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ConferenceServiceImpl implements ConferenceService {

    private final ModelMapper modelMapper;
    private final ConferenceRepository conferenceRepository;
    private final TalksConverter talksConverter;

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
    public void update(ConferenceDto dto) {
        conferenceRepository.save(conferenceDtoToConference(dto));
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
    public List<ConferenceDto> findPast() {
        return conferenceRepository.findAllByEndDateIsLessThan(LocalDate.now())
                .stream().map(this::conferenceToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConferenceDtoBasic> findPastBasic() {
        return conferenceRepository.findAllByEndDateIsLessThan(LocalDate.now())
                .stream().map(this::conferenceToDtoBasic)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConferenceDtoBasic> findUpcomingBasic() {
        List<Conference> conferences = conferenceRepository
                .findAllByStartDateIsGreaterThanEqual(LocalDate.now());
        fillCallForPaperDatesActive(conferences);
        return conferences.stream().map(this::conferenceToDtoBasic)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDto> findUpcoming() {
        List<Conference> conferences = conferenceRepository
                .findAllByStartDateIsGreaterThanEqual(LocalDate.now());
        fillCallForPaperDatesActive(conferences);
        return conferences.stream().map(this::conferenceToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TalkDto> findTalksByConferenceId(long id) {
        Conference conference = findById(id);
        Collection<Talk> talks = conference == null ? Collections.emptyList() : conference.getTalks();

        return talksConverter.toDto(talks);
    }

    public ConferenceDto conferenceToDto(Conference conference) {
        ConferenceDto conferenceDto = modelMapper.map(conference, ConferenceDto.class);
        conferenceDto.setCallForPaperStartDate(convertDateToString(conference.getCallForPaperStartDate()));
        conferenceDto.setCallForPaperEndDate(convertDateToString(conference.getCallForPaperEndDate()));
        conferenceDto.setStartDate(convertDateToString(conference.getStartDate()));
        conferenceDto.setEndDate(convertDateToString(conference.getEndDate()));
        conferenceDto.setNotificationDue(convertDateToString(conference.getNotificationDue()));
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

            conferenceDto.setApprovedTalkCount(talks.get(TalkStatus.ACCEPTED.getName()));
            conferenceDto.setRejectedTalkCount(talks.get(TalkStatus.NOT_ACCEPTED.getName()));
            conferenceDto.setInProgressTalkCount(talks.get(TalkStatus.PENDING.getName()));
        }

        conferenceDto.setNewTalkCount(conference.draftCount());

        return conferenceDto;
    }

    public String convertDateToString(LocalDate localDate) {
        if (localDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(formatter);
        }
        return null;
    }

    public Conference conferenceDtoToConference(ConferenceDto conferenceDto) {
        return modelMapper.map(conferenceDto, Conference.class);
    }

    @Override
    public List<ConferenceDto> conferenceToDto(Set<Conference> conferences) {
        return conferences.stream().map(this::conferenceToDto).collect(Collectors.toList());
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

    private ConferenceDtoBasic conferenceToDtoBasic(Conference conference) {
        return modelMapper.map(conference, ConferenceDtoBasic.class);
    }
}
