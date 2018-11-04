package service.businesslogic.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import domain.model.Language;
import domain.model.Level;
import domain.model.Role;
import domain.model.Talk;
import domain.model.TalkStatus;
import domain.model.Topic;
import domain.model.Type;
import domain.model.User;
import domain.model.UserInfo;
import domain.repository.ConferenceRepository;
import domain.repository.LanguageRepository;
import domain.repository.LevelRepository;
import domain.repository.RoleRepository;
import domain.repository.TalkRepository;
import domain.repository.TopicRepository;
import domain.repository.TypeRepository;
import domain.repository.UserRepository;
import service.businesslogic.api.TalkService;
import service.businesslogic.dto.TalkDto;
import service.businesslogic.dto.converter.TalksConverter;
import service.businesslogic.exception.TalkValidationException;
import service.infrastructure.mail.MailService;
import service.infrastructure.mail.preparator.ChangeTalkBySpeakerPreparator;

@RunWith(MockitoJUnitRunner.class)
public class TalkServiceImplTest {

    public static final long ID = 1L;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private TalkRepository talkRepository;
    @Mock
    private LevelRepository levelRepository;
    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private TopicRepository topicRepository;
    @Mock
    private TypeRepository typeRepository;
    @Mock
    private ConferenceRepository conferenceRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TalksConverter talksConverter;
    @Mock
    private MailService mailService;
    @Mock
    private RoleRepository roleRepository;
    private TalkService talkService;
    private TalkDto talkDto;
    private Talk talk;
    private User speakerUser;
    private User organiserUser;
    private Language language;
    private Topic topic;
    private Type type;
    private Level level;
    private List<Language> languages = new ArrayList<>();
    private List<Talk> talks = new ArrayList<>();

    @Before
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();

        talkService = new TalkServiceImpl(talkRepository, modelMapper, levelRepository, languageRepository,
                topicRepository, typeRepository, conferenceRepository, userRepository, roleRepository,
                talksConverter, mailService);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(ID);
        userInfo.setShortBio("bio");
        userInfo.setJobTitle("job");
        userInfo.setPastConference("pastConference");
        userInfo.setCompany("EPAM");
        userInfo.setAdditionalInfo("addInfo");

        speakerUser = new User();
        speakerUser.setId(ID);
        speakerUser.setFirstName("Olya");
        speakerUser.setLastName("Ivanova");
        speakerUser.setEmail("ivanova@gmail.com");
        speakerUser.setPassword("123456");
        speakerUser.setStatus(User.UserStatus.CONFIRMED);
        speakerUser.setUserInfo(userInfo);
        speakerUser.setRoles(Collections.singleton(new Role(Role.ROLE_SPEAKER)));

        organiserUser = new User();
        organiserUser.setId(ID);
        organiserUser.setFirstName("Artem");
        organiserUser.setLastName("Trybel");
        organiserUser.setEmail("trybel@gmail.com");
        organiserUser.setPassword("123456");
        organiserUser.setStatus(User.UserStatus.CONFIRMED);
        organiserUser.setUserInfo(userInfo);
        organiserUser.setRoles(Collections.singleton(new Role(Role.ROLE_ORGANISER)));

        language = new Language("English");
        level = new Level("Beginner");
        type = new Type("Regular Talk");
        topic = new Topic("JVM Languages and new programming paradigms");

        languages.add(language);

        setupCorrectTalkDto();

        talks.add(talk);
    }

    @Test
    public void testSuccessSaveAsDto() {
        when(languageRepository.findByName("English")).thenReturn(language);
        when(levelRepository.findByName("Beginner")).thenReturn(level);
        when(topicRepository.findTopicByName(anyString())).thenReturn(topic);
        when(typeRepository.findByName("Regular Talk")).thenReturn(type);
        talk.setOrganiser(null);
        talkService.save(talkDto, speakerUser, null);
        verify(talkRepository, times(1)).save(talk);
        verify(mailService, times(1)).notifyUsers(anyList(), any());
        verify(mailService, times(1)).sendEmail(any(), any());
    }

    @Test
    public void testAddFileSuccessful() {
        String pathFile = "Path";
        talk.setPathToAttachedFile(pathFile);
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        talkService.addFile(talkDto, pathFile);
        verify(talkRepository, times(1)).save(talk);
    }

    @Test
    public void testAddFileSuccessfulNullArg() {
        talk.setPathToAttachedFile(null);
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        talkService.addFile(talkDto, null);
        verify(talkRepository, times(1)).save(talk);
    }

    @Test
    public void testDeleteFile() {
        talk.setPathToAttachedFile("Path");
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        talkService.deleteFile(talkDto, true);
        talk.setPathToAttachedFile(null);
        verify(talkRepository, times(1)).save(talk);
    }

    @Test
    public void testGetFilePath() {
        String pathFile = "Path";
        talk.setPathToAttachedFile(pathFile);
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        assertEquals(talkService.getFilePath(talkDto), pathFile);
    }

    @Test
    public void testUpdateAsOrganiserSuccessful() {
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, times(1)).save(talk);
        verify(mailService, times(1)).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserOrgCommentTooLong() {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("comment_too_long");

        talkDto.setOrganiserComment(createStringWithLength(5000));
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).save(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserStatusIsEmpty() {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("status_is_null");

        talkDto.setStatusName(null);
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).save(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserOrgCommentIsEmpty() {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("empty_comment");

        talkDto.setStatusName(TalkStatus.NOT_ACCEPTED.getName());
        talkDto.setOrganiserComment(null);
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).save(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserWrongStatusChange() {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("wrong_status");
        talk.setStatus(TalkStatus.NOT_ACCEPTED);
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        talkDto.setStatusName(TalkStatus.DRAFT.getName());
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).save(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsSpeakerWithNotifySuccessful() {
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        when(languageRepository.findByName("English")).thenReturn(language);
        when(levelRepository.findByName("Beginner")).thenReturn(level);
        when(topicRepository.findTopicByName("JVM Languages and new programming paradigms")).thenReturn(topic);
        when(typeRepository.findByName("Regular Talk")).thenReturn(type);
        talkService.updateAsSpeaker(talkDto, speakerUser);

        verify(talkRepository, times(1)).save(talk);
        verify(mailService, times(1)).sendEmail(eq(organiserUser), any(ChangeTalkBySpeakerPreparator.class));
    }

    @Test
    public void testUpdateAsSpeakerWithOutNotifySuccessful() {
        talk.setOrganiser(null);
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        when(languageRepository.findByName("English")).thenReturn(language);
        when(levelRepository.findByName("Beginner")).thenReturn(level);
        when(topicRepository.findTopicByName("JVM Languages and new programming paradigms")).thenReturn(topic);
        when(typeRepository.findByName("Regular Talk")).thenReturn(type);
        talkService.updateAsSpeaker(talkDto, speakerUser);

        verify(talkRepository, times(1)).save(talk);
        verify(mailService, never()).sendEmail(eq(organiserUser), any(ChangeTalkBySpeakerPreparator.class));
    }

    @Test
    public void testFindById() {
        when(talkRepository.findById(anyLong())).thenReturn(talk);

        TalkDto talkDtoResult = talkService.findById(ID);
        talkDto.setSpeakerFullName(talk.getUser().getFullName());
        talkDto.setAssignee(talk.getOrganiser().getFullName());
        assertEquals(talkDto, talkDtoResult);
    }

    private void setupCorrectTalkDto() {
        LocalDateTime dateTime = LocalDateTime.now();

        talk = new Talk();
        talk.setId(ID);
        talk.setDescription("Description");
        talk.setTitle("Title");
        talk.setLanguage(language);
        talk.setLevel(level);
        talk.setType(type);
        talk.setTopic(topic);
        talk.setStatus(TalkStatus.DRAFT);
        talk.setTime(dateTime);
        talk.setAdditionalInfo("Info");
        talk.setOrganiserComment("Org comment");
        talk.setUser(speakerUser);
        talk.setOrganiser(organiserUser);

        talkDto = new TalkDto();
        talkDto.setId(ID);
        talkDto.setDescription("Description");
        talkDto.setTitle("Title");
        talkDto.setLanguageName("English");
        talkDto.setLevelName("Beginner");
        talkDto.setStatusName(TalkStatus.DRAFT.getName());
        talkDto.setTypeName("Regular Talk");
        talkDto.setTopicName("JVM Languages and new programming paradigms");
        talkDto.setDate(dateTime.toString());
        talkDto.setAdditionalInfo("Info");
        talkDto.setOrganiserComment("Org comment");
        talkDto.setUserId(ID);
    }

    private String createStringWithLength(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append('x');
        }
        return stringBuilder.toString();
    }
}