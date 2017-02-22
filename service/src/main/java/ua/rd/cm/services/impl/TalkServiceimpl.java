package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.domain.User;
import ua.rd.cm.repository.TalkRepository;
import ua.rd.cm.repository.specification.talk.TalkById;
import ua.rd.cm.repository.specification.talk.TalkByUserId;
import ua.rd.cm.services.TalkService;
import ua.rd.cm.services.exception.TalkNotFoundException;
import ua.rd.cm.services.preparator.SubmitNewTalkOrganiserPreparator;
import ua.rd.cm.services.preparator.SubmitNewTalkSpeakerPreparator;

import java.util.List;

@Service
public class TalkServiceimpl implements TalkService {
    public static final String DEFAULT_TALK_STATUS = "New";
    private TalkRepository talkRepository;

    @Autowired
    public TalkServiceimpl(TalkRepository talkRepository) {
        this.talkRepository = talkRepository;
    }

    @Override
    @Transactional
    public void save(Talk talk) {
        talkRepository.saveTalk(talk);
    }

    @Override
    @Transactional
    public void save(Talk talk,User user) {
        talk.setStatus(TalkStatus.getStatusByName(DEFAULT_TALK_STATUS));
        talk.setUser(user);
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
        List<Talk> talks = talkRepository.findBySpecification(new TalkById(id));
        if (talks.isEmpty()) {
            throw new TalkNotFoundException();
        }
        return talks.get(0);
    }
}
