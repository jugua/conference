package ua.rd.cm.services;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.TalkRepository;
import ua.rd.cm.repository.specification.Specification;
import ua.rd.cm.repository.specification.talk.TalkByUserId;
import ua.rd.cm.services.exception.TalkNotFoundException;
import ua.rd.cm.services.impl.TalkServiceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleTalkServiceTest {
    @Mock
    private TalkRepository talkRepository;

    private TalkService talkService;

    @Before
    public void setUp() throws Exception {
        talkService = new TalkServiceimpl(talkRepository);
    }

    @Test
    public void testSave() throws Exception {
        Talk talk = new Talk();
        talkService.save(talk);
        verify(talkRepository).saveTalk(refEq(talk));
    }

    @Test
    public void testUpdate() throws Exception {
        Talk talk = new Talk();
        talkService.update(talk);
        verify(talkRepository).update(refEq(talk));
    }

    @Test
    public void testRemove() throws Exception {
        Talk talk = new Talk();
        talkService.remove(talk);
        verify(talkRepository).remove(refEq(talk));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Talk> expected = new ArrayList<Talk>() {{
            add(new Talk());
            add(new Talk());
        }};
        when(talkRepository.findAll()).thenReturn(expected);

        List<Talk> actual = talkService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void testFindByUserId() throws Exception {
        Long id = anyLong();
        List<Talk> expected = Arrays.asList(new Talk(), new Talk());

        Specification<Talk> findById = new TalkByUserId(id);
        when(talkRepository.findBySpecification(findById)).
                thenReturn(expected);

        List<Talk> actual = talkService.findByUserId(id);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindTalkById() throws Exception {
        Talk actual = new Talk();
        when(talkRepository.findBySpecification(any(Specification.class))).
                thenReturn(Collections.singletonList(actual));
        Talk expected = talkService.findTalkById(1L);
        assertEquals(actual, expected);
    }

    @Test(expected = TalkNotFoundException.class)
    public void testFindNonExistingTalkById() throws Exception {
        when(talkRepository.findBySpecification(any(Specification.class))).
                thenReturn(Collections.emptyList());
        talkService.findTalkById(1L);
    }
}
