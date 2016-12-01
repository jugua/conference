package ua.rd.cm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.TalkRepository;
import ua.rd.cm.repository.specification.talk.TalkById;
import ua.rd.cm.repository.specification.talk.TalkByUserId;
import ua.rd.cm.services.exception.EmptyInformationException;

@Service
public class SimpleTalkService implements TalkService{

	private TalkRepository talkRepository;
	
	@Autowired
	public SimpleTalkService(TalkRepository talkRepository) {
		this.talkRepository = talkRepository;
	}

	@Override
	@Transactional
	public void save(Talk talk) {
		if(talk.getUser().getUserInfo().getShortBio().isEmpty())
			throw new EmptyInformationException();
		
		talkRepository.saveTalk(talk);
	}

	@Override
	@Transactional
	public void update(Talk talk) {
		talkRepository.updateTalk(talk);
	}

	@Override
	@Transactional
	public void remove(Talk talk) {
		talkRepository.removeTalk(talk);
	}

	@Override
	public List<Talk> findAll() {
		return talkRepository.findAll();
	}

	@Override
	public List<Talk> findByUserId(Long id) {
		return talkRepository.findBySpecification(new TalkByUserId(id));
	}

	@Override
	public Talk findTalkById(Long id) {
		return talkRepository.findBySpecification(new TalkById(id)).get(0);
	}

}
