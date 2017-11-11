package ua.rd.cm.services.businesslogic;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.dto.ConferenceDto;
import ua.rd.cm.dto.ConferenceDtoBasic;
import ua.rd.cm.dto.CreateConferenceDto;
import ua.rd.cm.dto.TalkDto;


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
