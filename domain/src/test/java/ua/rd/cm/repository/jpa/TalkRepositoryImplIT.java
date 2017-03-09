package ua.rd.cm.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.TalkRepository;

import java.time.LocalDateTime;

public class TalkRepositoryImplIT extends AbstractJpaCrudRepositoryIT<Talk> {
    @Autowired
    private TalkRepository repository;

    @Override
    protected CrudRepository<Talk> createRepository() {
        return repository;
    }

    @Override
    protected Talk createEntity() {
        Talk talk = new Talk();
        talk.setTitle("JUG ua");
        talk.setTime(LocalDateTime.MIN);
        return talk;
    }
}