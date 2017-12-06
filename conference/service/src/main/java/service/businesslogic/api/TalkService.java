package service.businesslogic.api;

import java.util.List;

import domain.model.Talk;
import domain.model.User;
import service.businesslogic.dto.Submission;
import service.businesslogic.dto.TalkDto;
import service.businesslogic.dto.TalkStatusDto;

public interface TalkService {

    Talk save(TalkDto talkDto, User user, String multipartFilePath);

    void addFile(TalkDto talkDto, String multipartFilePath);

    void deleteFile(TalkDto talkDto, boolean deleteFile);

    void updateStatus(TalkStatusDto dto);
    
    void updateAsOrganiser(TalkDto talkDto, User user);

    void updateAsSpeaker(TalkDto talkDto, User user);

    List<Talk> findAll();

    List<Talk> findByUserId(Long id);

    Talk findTalkById(Long id);

    TalkDto findById(Long id);

    List<Submission> getTalksForSpeaker(String userEmail);

    String getFilePath(TalkDto talkDto);

	
}
