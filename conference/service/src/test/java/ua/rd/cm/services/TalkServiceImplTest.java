package ua.rd.cm.services;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import ua.rd.cm.domain.Language;
import ua.rd.cm.domain.Level;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.domain.Topic;
import ua.rd.cm.domain.Type;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.dto.TalkDto;
import ua.rd.cm.infrastructure.mail.MailService;
import ua.rd.cm.infrastructure.mail.preparator.ChangeTalkBySpeakerPreparator;
import ua.rd.cm.repository.ConferenceRepository;
import ua.rd.cm.repository.LanguageRepository;
import ua.rd.cm.repository.LevelRepository;
import ua.rd.cm.repository.RoleRepository;
import ua.rd.cm.repository.TalkRepository;
import ua.rd.cm.repository.TopicRepository;
import ua.rd.cm.repository.TypeRepository;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.services.businesslogic.TalkService;
import ua.rd.cm.services.businesslogic.impl.TalkServiceImpl;
import ua.rd.cm.services.exception.TalkValidationException;

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
    public void setUp() throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        talkService = new TalkServiceImpl(talkRepository, modelMapper, levelRepository, languageRepository,
                topicRepository, typeRepository, conferenceRepository, userRepository, roleRepository, mailService);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(ID);
        userInfo.setShortBio("bio");
        userInfo.setJobTitle("job");
        userInfo.setPastConference("pastConference");
        userInfo.setCompany("EPAM");
        userInfo.setAdditionalInfo("addInfo");

        Set<Role> speakerRole = new HashSet<>();
        speakerRole.add(new Role(Role.SPEAKER));
        speakerUser = new User();
        speakerUser.setId(ID);
        speakerUser.setFirstName("Olya");
        speakerUser.setLastName("Ivanova");
        speakerUser.setEmail("ivanova@gmail.com");
        speakerUser.setPassword("123456");
        speakerUser.setStatus(User.UserStatus.CONFIRMED);
        speakerUser.setUserInfo(userInfo);
        speakerUser.setRoles(speakerRole);

        Set<Role> organiserRole = new HashSet<>();
        organiserRole.add(new Role(Role.ORGANISER));
        organiserUser = new User();
        organiserUser.setId(ID);
        organiserUser.setFirstName("Artem");
        organiserUser.setLastName("Trybel");
        organiserUser.setEmail("trybel@gmail.com");
        organiserUser.setPassword("123456");
        organiserUser.setStatus(User.UserStatus.CONFIRMED);
        organiserUser.setUserInfo(userInfo);
        organiserUser.setRoles(organiserRole);

        language = new Language("English");
        level = new Level("Beginner");
        type = new Type("Regular Talk");
        topic = new Topic("JVM Languages and new programming paradigms");

        languages.add(language);

        setupCorrectTalkDto();

        talks.add(talk);
    }

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
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        talkService.addFile(talkDto, pathFile);
        verify(talkRepository, times(1)).save(talk);
    }

    @Test
    public void testAddFileSuccessfulNullArg() throws Exception {
        talk.setPathToAttachedFile(null);
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        talkService.addFile(talkDto, null);
        verify(talkRepository, times(1)).save(talk);
    }

    @Test
    public void testDeleteFile() throws Exception {
        talk.setPathToAttachedFile("Path");
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        talkService.deleteFile(talkDto, true);
        talk.setPathToAttachedFile(null);
        verify(talkRepository, times(1)).save(talk);
    }

    @Test
    public void testGetFilePath() throws Exception {
        String pathFile = "Path";
        talk.setPathToAttachedFile(pathFile);
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        assertEquals(talkService.getFilePath(talkDto), pathFile);
    }

    @Test
    public void testUpdateAsOrganiserSuccessful() throws Exception {
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, times(1)).save(talk);
        verify(mailService, times(1)).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserOrgCommentTooLong() throws Exception {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("comment_too_long");

        talkDto.setOrganiserComment(createStringWithLength(5000));
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).save(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserStatusIsEmpty() throws Exception {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("status_is_null");

        talkDto.setStatusName(null);
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).save(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserOrgCommentIsEmpty() throws Exception {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("empty_comment");

        talkDto.setStatusName(TalkStatus.REJECTED.getName());
        talkDto.setOrganiserComment(null);
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).save(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsOrganiserWrongStatusChange() throws Exception {
        expectedException.expect(TalkValidationException.class);
        expectedException.expectMessage("wrong_status");
        talk.setStatus(TalkStatus.REJECTED);
        when(talkRepository.findById(anyLong())).thenReturn(talk);
        talkDto.setStatusName(TalkStatus.NEW.getName());
        talkService.updateAsOrganiser(talkDto, organiserUser);

        verify(talkRepository, never()).save(talk);
        verify(mailService, never()).notifyUsers(anyList(), any());
    }

    @Test
    public void testUpdateAsSpeakerWithNotifySuccessful() throws Exception {
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
    public void testUpdateAsSpeakerWithOutNotifySuccessful() throws Exception {
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
    public void testFindById() throws Exception {
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
        talk.setStatus(TalkStatus.NEW);
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
        talkDto.setStatusName(TalkStatus.NEW.getName());
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