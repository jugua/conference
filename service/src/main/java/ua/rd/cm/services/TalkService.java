package ua.rd.cm.services;

import java.util.List;

import ua.rd.cm.domain.Talk;

public interface TalkService {

	void save(Talk talk);

	void update(Talk talk);
	
	void remove(Talk talk);
	
	List<Talk> findAll();
	
	List<Talk> findByUserId(Long id);
	
	Talk findTalkById(Long id);
	
	//List<Talk> findByStatusTopicDate();
}
