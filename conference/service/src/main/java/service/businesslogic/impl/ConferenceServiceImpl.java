package service.businesslogic.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import domain.model.Conference;
import domain.repository.ConferenceRepository;
import service.businesslogic.api.ConferenceService;
import service.businesslogic.dto.ConferenceDto;
import service.businesslogic.dto.ConferenceDtoBasic;
import service.businesslogic.dto.CreateConferenceDto;
import service.businesslogic.exception.ConferenceNotFoundException;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ConferenceServiceImpl implements ConferenceService {

    private final ModelMapper modelMapper;
    private final ConferenceRepository conferenceRepository;

    @Override
    @Transactional(readOnly = true)
    public Conference getById(Long id) {
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
    @Transactional(readOnly = true)
    public List<Conference> getAll() {
        return conferenceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDto> getPast() {
        return conferenceRepository.findAllByEndDateIsLessThan(LocalDate.now())
                .stream().map(this::conferenceToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConferenceDtoBasic> getPastBasic() {
        return conferenceRepository.findAllByEndDateIsLessThan(LocalDate.now())
                .stream().map(this::conferenceToDtoBasic)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConferenceDtoBasic> getUpcomingBasic() {
        List<Conference> conferences = conferenceRepository.findAllByStartDateIsGreaterThanEqual(LocalDate.now());

        startCallForPapers(conferences);

        return conferences.stream().map(this::conferenceToDtoBasic)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDto> getUpcoming() {
        List<Conference> conferences = conferenceRepository.findAllByStartDateIsGreaterThanEqual(LocalDate.now());
        startCallForPapers(conferences);
        return conferences.stream().map(this::conferenceToDto).collect(Collectors.toList());
    }

    public ConferenceDto conferenceToDto(Conference conference) {
        ConferenceDto conferenceDto = modelMapper.map(conference, ConferenceDto.class);
        conferenceDto.setCallForPaperStartDate(stringOf(conference.getCallForPaperStartDate()));
        conferenceDto.setCallForPaperEndDate(stringOf(conference.getCallForPaperEndDate()));
        conferenceDto.setStartDate(stringOf(conference.getStartDate()));
        conferenceDto.setEndDate(stringOf(conference.getEndDate()));
        conferenceDto.setNotificationDue(stringOf(conference.getNotificationDue()));

        conferenceDto.setNewTalkCount(conference.draftCount());
        conferenceDto.setInProgressTalkCount(conference.pendingCount());
        conferenceDto.setApprovedTalkCount(conference.acceptedCount());
        conferenceDto.setRejectedTalkCount(conference.notAcceptedCount());

        return conferenceDto;
    }

    public Conference conferenceDtoToConference(ConferenceDto conferenceDto) {
        return modelMapper.map(conferenceDto, Conference.class);
    }

    @Override
    public List<ConferenceDto> conferenceToDto(Set<Conference> conferences) {
        return conferences.stream().map(this::conferenceToDto).collect(Collectors.toList());
    }

    private void startCallForPapers(List<Conference> conferences) {
        conferences.stream()
                .filter(Conference::callForPapersShouldBeStarted)
                .forEach(Conference::startCallForPaper);
    }

    private ConferenceDtoBasic conferenceToDtoBasic(Conference conference) {
        return modelMapper.map(conference, ConferenceDtoBasic.class);
    }

    private String stringOf(LocalDate localDate) {
        if (localDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(formatter);
        }
        return null;
    }

}
