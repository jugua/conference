package ua.rd.cm.services;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import ua.rd.cm.domain.*;
import ua.rd.cm.dto.TalkDto;
import ua.rd.cm.repository.*;
import ua.rd.cm.repository.specification.talk.TalkById;
import ua.rd.cm.services.exception.*;
import ua.rd.cm.services.impl.TalkServiceImpl;
import ua.rd.cm.services.preparator.ChangeTalkBySpeakerPreparator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TalkServiceImplTest {

    @Mock
    private TalkRepository talkRepository;

    private ModelMapper modelMapper;
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
    private MailService mailService;
    @Mock
    private RoleService roleService;

    private TalkService talkService;

    private TalkDto talkDto;
    private Talk talk;

    private User speakerUser;
    private User organiserUser;

    private UserInfo userInfo;

    private Language language;
    private Topic topic;
    private Type type;
    private Level level;
    private List<Language> languages = new ArrayList<>();
    private List<Talk> talks = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        modelMapper = new ModelMapper();
        talkService = new TalkServiceImpl(talkRepository, modelMapper, levelRepository, languageRepository, topicRepository, typeRepository, conferenceRepository, userRepository, mailService, roleService);
        userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setShortBio("bio");
        userInfo.setJobTitle("job");
        userInfo.setPastConference("pastConference");
        userInfo.setCompany("EPAM");
        userInfo.setAdditionalInfo("addInfo");

        Set<Role> speakerRole = new HashSet<>();
        speakerRole.add(new Role(2L, Role.SPEAKER));
        speakerUser = new User();
        speakerUser.setId(1L);
        speakerUser.setFirstName("Olya");
        speakerUser.setLastName("Ivanova");
        speakerUser.setEmail("ivanova@gmail.com");
        speakerUser.setPassword("123456");
        speakerUser.setStatus(User.UserStatus.CONFIRMED);
        speakerUser.setUserInfo(userInfo);
        speakerUser.setUserRoles(speakerRole);

        Set<Role> organiserRole = new HashSet<>();
        organiserRole.add(new Role(1L, Role.ORGANISER));
        organiserUser = new User();
        organiserUser.setId(1L);
        organiserUser.setFirstName("Artem");
        organiserUser.setLastName("Trybel");
        organiserUser.setEmail("trybel@gmail.com");
        organiserUser.setPassword("123456");
        organiserUser.setStatus(User.UserStatus.CONFIRMED);
        organiserUser.setUserInfo(userInfo);
        organiserUser.setUserRoles(organiserRole);

        language = new Language(1L, "English");
        level = new Level(1L, "Beginner");
        type = new Type("Regular Talk");
        topic = new Topic("JVM Languages and new programming paradigms");

        languages.add(language);

        setupCorrectTalkDto();

        talks.add(talk);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSuccessSaveAsDto() throws Exception {
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
    public void testAddFileSuccessful() throws Exception {
        String pathFile = "Path";
        talk.setPathToAttachedFile(pathFile);
        when(talkRepository.findBySpecification(any(TalkById.class))).thenReturn(talks);
        talkService.addFile(talkDto, pathFile);
        verify(talkRepository, times(1)).update(talk);
    }

    @Test
    public void testAddFileSuccessfulNullArg() throws Exception {
        talk.setPathToAttachedFile(null);
        when(talkRepository.findBySpecification(any(TalkById.class))).thenReturn(talks);
        talkService.addFile(talkDto, null);
        verify(talkRepository, times(1)).update(talk);
    }

    @Test
    public void testDeleteFile() throws Exception {
        talk.setPathToAttachedFile("Path");
        when(talkRepository.findBySpecification(any(TalkById.class))).thenReturn(talks);
        talkService.deleteFile(talkDto, true);
        talk.setPathToAttachedFile(null);
        verify(talkRepository, times(1)).update(talk);
    }

    @Test
    public void testGetFilePath() throws Exception {
        String pathFile = "Path";
        talk.setPathToAttachedFile(pathFile);
        when(talkRepository.findBySpecification(any(TalkById.class))).thenReturn(talks);
        assertEquals(talkService.getFilePath(talkDto), pathFile);
    }

    @Test
    public void testUpdateAsOrganiserSuccessful() throws Exception {
        when(talkRepository.findBySpecification(any(TalkById.class))).thenReturn(talks);
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, times(1)).update(talk);
        verify(mailService, times(1)).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserOrgCommentTooLong() throws Exception {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("comment_too_long");

        talkDto.setOrganiserComment(createStringWithLength(5000));
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).update(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserStatusIsEmpty() throws Exception {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("status_is_null");

        talkDto.setStatusName(null);
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).update(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserOrgCommentIsEmpty() throws Exception {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("empty_comment");

        talkDto.setStatusName(TalkStatus.REJECTED.getName());
        talkDto.setOrganiserComment(null);
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).update(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserWrongStatusChange() throws Exception {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("wrong_status");
        talk.setStatus(TalkStatus.REJECTED);
        when(talkRepository.findBySpecification(any(TalkById.class))).thenReturn(talks);
        talkDto.setStatusName(TalkStatus.NEW.getName());
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).update(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsSpeakerWithNotifySuccessful() throws Exception {
        when(talkRepository.findBySpecification(any(TalkById.class))).thenReturn(talks);
        when(languageRepository.findByName("English")).thenReturn(language);
        when(levelRepository.findByName("Beginner")).thenReturn(level);
        when(topicRepository.findTopicByName("JVM Languages and new programming paradigms")).thenReturn(topic);
        when(typeRepository.findByName("Regular Talk")).thenReturn(type);
        talkService.updateAsSpeaker(talkDto, speakerUser);

        verify(talkRepository, times(1)).update(talk);
        verify(mailService, times(1)).sendEmail(eq(organiserUser), any(ChangeTalkBySpeakerPreparator.class));
    }

    @Test
    public void testUpdateAsSpeakerWithOutNotifySuccessful() throws Exception {
        talk.setOrganiser(null);
        when(talkRepository.findBySpecification(any(TalkById.class))).thenReturn(talks);
        when(languageRepository.findByName("English")).thenReturn(language);
        when(levelRepository.findByName("Beginner")).thenReturn(level);
        when(topicRepository.findTopicByName("JVM Languages and new programming paradigms")).thenReturn(topic);
        when(typeRepository.findByName("Regular Talk")).thenReturn(type);
        talkService.updateAsSpeaker(talkDto, speakerUser);

        verify(talkRepository, times(1)).update(talk);
        verify(mailService, never()).sendEmail(eq(organiserUser), any(ChangeTalkBySpeakerPreparator.class));
    }

    @Test
    public void testFindById() throws Exception {
        when(talkRepository.findBySpecification(any(TalkById.class))).thenReturn(talks);

        TalkDto talkDtoResult = talkService.findById(1L);
        talkDto.setSpeakerFullName(talk.getUser().getFullName());
        talkDto.setAssignee(talk.getOrganiser().getFullName());
        assertEquals(talkDto, talkDtoResult);
    }

    private void setupCorrectTalkDto() {
        LocalDateTime dateTime = LocalDateTime.now();

        talk = new Talk();
        talk.setId(1L);
        talk.setDescription("Description");
        talk.setTitle("Title");
        talk.setLanguage(language);
        talk.setLevel(level);
        talk.setType(type);
        talk.setTopic(topic);
        talk.setStatus(TalkStatus.NEW);
        talk.setTime(dateTime);
        talk.setAdditionalInfo("Info");
        talk.setOrganiserComment("Org comment");
        talk.setUser(speakerUser);
        talk.setOrganiser(organiserUser);

        talkDto = new TalkDto();
        talkDto.setId(1L);
        talkDto.setDescription("Description");
        talkDto.setTitle("Title");
        talkDto.setLanguageName("English");
        talkDto.setLevelName("Beginner");
        talkDto.setStatusName(TalkStatus.NEW.getName());
        talkDto.setTypeName("Regular Talk");
        talkDto.setTopicName("JVM Languages and new programming paradigms");
        talkDto.setDate(dateTime.toString());
        talkDto.setAdditionalInfo("Info");
        talkDto.setOrganiserComment("Org comment");
        talkDto.setUserId(1L);
    }

    private String createStringWithLength(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append('x');
        }
        return stringBuilder.toString();
    }
}