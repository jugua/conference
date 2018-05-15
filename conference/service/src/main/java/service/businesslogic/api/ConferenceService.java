package service.businesslogic.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import domain.model.Conference;
import service.businesslogic.dto.ConferenceDto;
import service.businesslogic.dto.ConferenceDtoBasic;
import service.businesslogic.dto.CreateConferenceDto;
import service.businesslogic.dto.TalkDto;

public interface ConferenceService {

    Conference findById(Long id);

    Long save(CreateConferenceDto conference);

    void update(ConferenceDto conference);

    List<Conference> findAll();

    List<ConferenceDto> findPast();

    List<ConferenceDto> findUpcoming();

    List<ConferenceDtoBasic> findPastBasic();

    List<ConferenceDtoBasic> findUpcomingBasic();

    List<ConferenceDto> conferenceToDto(Set<Conference> conferences);

    Collection<TalkDto> findTalksByConferenceId(long id);

}
