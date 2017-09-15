package ua.rd.cm.services.businesslogic;

import java.util.List;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;
import ua.rd.cm.dto.TalkDto;

public interface TalkService {

    Talk save(TalkDto talkDto, User user, String multipartFilePath);

    void addFile(TalkDto talkDto, String multipartFilePath);

    void deleteFile(TalkDto talkDto, boolean deleteFile);

    void updateAsOrganiser(TalkDto talkDto, User user);

    void updateAsSpeaker(TalkDto talkDto, User user);

    List<Talk> findAll();

    List<Talk> findByUserId(Long id);

    Talk findTalkById(Long id);

    TalkDto findById(Long id);

    List<TalkDto> getTalksForSpeaker(String userEmail);

    List<TalkDto> getTalksForOrganiser();

    String getFilePath(TalkDto talkDto);
}
