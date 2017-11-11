package ua.rd.cm.dto.converter;

import java.util.ArrayList;
import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;
import ua.rd.cm.dto.TalkDto;

@Component
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TalksConverter {

    private ModelMapper modelMapper;

    public Collection<TalkDto> toDto(Collection<Talk> talks) {
        Collection<TalkDto> talksDto = new ArrayList<>();
        if (CollectionUtils.isEmpty(talks)) {
            return talksDto;
        }

        for (Talk talk : talks) {
            talksDto.add(toDto(talk));
        }
        return talksDto;
    }

    public TalkDto toDto(Talk talk) {
        TalkDto dto = modelMapper.map(talk, TalkDto.class);
        dto.setSpeakerFullName(talk.getUser().getFullName());
        dto.setStatusName(talk.getStatus().getName());
        dto.setDate(talk.getTime().toString());
        if (talk.getConference() != null) {
            Conference conference = talk.getConference();
            dto.setConferenceId(conference.getId());
            dto.setConferenceName(conference.getTitle());
        }

        User organiser = talk.getOrganiser();
        if (organiser != null) {
            dto.setAssignee(organiser.getFullName());
        }
        return dto;
    }

}
