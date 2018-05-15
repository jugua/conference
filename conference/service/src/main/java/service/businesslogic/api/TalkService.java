package service.businesslogic.api;

import java.util.Collection;
import java.util.List;

import domain.model.Talk;
import domain.model.User;
import service.businesslogic.dto.Submission;
import service.businesslogic.dto.TalkDto;
import service.businesslogic.dto.TalkStatusDto;

public interface TalkService {

    List<Talk> getAll();

    Talk getById(Long id);

    List<Talk> getByUserId(Long id);

    Collection<TalkDto> getByConferenceId(long id);

    Talk save(TalkDto talkDto, User user, String multipartFilePath);

    //TODO: this method should be probably part of FileService
    void addFile(TalkDto talkDto, String multipartFilePath);

    //TODO: this method should be probably part of FileService
    void deleteFile(TalkDto talkDto, boolean deleteFile);

    void updateStatus(TalkStatusDto dto);

    void updateAsOrganiser(TalkDto talkDto, User user);

    void updateAsSpeaker(TalkDto talkDto, User user);

    //TODO: this method return projection of Talk object and should not be part of service, but some view logic instead
    @Deprecated
    List<Submission> getSubmissions(String userEmail);

    //TODO: this method should be probably part of FileService or should not be exposed like this
    String getFilePath(TalkDto talkDto);

    /**
     * Consider using {@link #getById(Long)} instead.
     */
    @Deprecated
    //TODO: this method return projection of Talk object and should not be part of service, but some view logic instead
    TalkDto findById(Long id);

}
