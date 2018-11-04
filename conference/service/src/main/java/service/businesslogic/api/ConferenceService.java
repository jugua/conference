package service.businesslogic.api;

import java.util.List;
import java.util.Set;

import domain.model.Conference;
import service.businesslogic.dto.ConferenceDto;
import service.businesslogic.dto.ConferenceDtoBasic;
import service.businesslogic.dto.CreateConferenceDto;

public interface ConferenceService {

    List<Conference> getAll();

    Conference getById(Long id);

    List<ConferenceDto> getUpcoming();

    List<ConferenceDto> getPast();

    Long save(CreateConferenceDto conference);

    void update(ConferenceDto conference);

    @Deprecated //TODO: this method is part of representation. Move it on upper level.
    List<ConferenceDtoBasic> getPastBasic();

    @Deprecated //TODO: this method is part of representation. Move it on upper level.
    List<ConferenceDtoBasic> getUpcomingBasic();

    @Deprecated //TODO: this method is part of representation. It should not be present in this interface.
    List<ConferenceDto> conferenceToDto(Set<Conference> conferences);

}
