package ua.rd.cm.services;

import java.util.List;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;
import ua.rd.cm.dto.TalkDto;

public interface TalkService {

	void save(Talk talk);

	void save(Talk talk,User user);

	Talk save(TalkDto talkDto, User user, String multipartFilePath);

	void update(Talk talk);

    void updateAsOrganiser(TalkDto talkDto, User user);

    void updateAsSpeaker(TalkDto talkDto, User user);
	
	void remove(Talk talk);
	
	List<Talk> findAll();
	
	List<Talk> findByUserId(Long id);
	
	Talk findTalkById(Long id);

	TalkDto findById(Long id);

	List<TalkDto> getTalksForSpeaker(String userEmail);

	List<TalkDto> getTalksForOrganiser();
}
