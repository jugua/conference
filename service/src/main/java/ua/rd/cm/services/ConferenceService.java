package ua.rd.cm.services;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.dto.ConferenceDto;
import ua.rd.cm.dto.ConferenceDtoBasic;
import ua.rd.cm.dto.CreateConferenceDto;

import java.time.LocalDate;
import java.util.List;

public interface ConferenceService {

    Conference findById(Long id);

    Long save(CreateConferenceDto conference);

    void update(Conference conference);

    void remove(Conference conference);

    List<Conference> findAll();

    List<Conference> findPast();

    List<Conference> findUpcoming();

    Conference conferenceDtoToConference(ConferenceDto conferenceDto);

    List<ConferenceDto> conferenceListToDto(List<Conference> conferences);

    String convertDateToString(LocalDate localDate);

    ConferenceDto conferenceToDto(Conference conference);

    List<ConferenceDtoBasic> conferenceListToDtoBasic(List<Conference> conferences);

    ConferenceDtoBasic conferenceToDtoBasic(Conference conference);
}
