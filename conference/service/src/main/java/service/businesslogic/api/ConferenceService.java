package service.businesslogic.api;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import domain.model.Conference;
import service.businesslogic.dto.ConferenceDto;
import service.businesslogic.dto.ConferenceDtoBasic;
import service.businesslogic.dto.CreateConferenceDto;
import service.businesslogic.dto.TalkDto;


public interface ConferenceService {

    Conference findById(Long id);

    Long save(CreateConferenceDto conference);

    void update(ConferenceDto conference);

    void remove(Conference conference);

    List<Conference> findAll();

    List<ConferenceDto> findPast();

    List<ConferenceDto> findUpcoming();

    List<ConferenceDtoBasic> findPastBasic();

    List<ConferenceDtoBasic> findUpcomingBasic();

    Conference conferenceDtoToConference(ConferenceDto conferenceDto);

    String convertDateToString(LocalDate localDate);

    ConferenceDto conferenceToDto(Conference conference);

    Collection<TalkDto> findTalksByConferenceId(long id);

}
