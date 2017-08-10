package ua.rd.cm.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.*;
import ua.rd.cm.dto.TalkDto;
import ua.rd.cm.repository.*;
import ua.rd.cm.repository.specification.AndSpecification;
import ua.rd.cm.repository.specification.language.LanguageByName;
import ua.rd.cm.repository.specification.level.LevelByName;
import ua.rd.cm.repository.specification.talk.TalkById;
import ua.rd.cm.repository.specification.talk.TalkByUserId;
import ua.rd.cm.repository.specification.topic.TopicByName;
import ua.rd.cm.repository.specification.type.TypeByName;
import ua.rd.cm.repository.specification.user.UserByEmail;
import ua.rd.cm.repository.specification.user.UserByRole;
import ua.rd.cm.repository.specification.user.UserExceptThisById;
import ua.rd.cm.services.MailService;
import ua.rd.cm.services.TalkService;
import ua.rd.cm.services.exception.*;
import ua.rd.cm.services.preparator.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ua.rd.cm.services.exception.TalkValidationException.*;

@Service
public class TalkServiceImpl implements TalkService {
    public static final String DEFAULT_TALK_STATUS = "New";
    private TalkRepository talkRepository;
    private ModelMapper modelMapper;
    private LevelRepository levelRepository;
    private LanguageRepository languageRepository;
    private TopicRepository topicRepository;
    private TypeRepository typeRepository;
    private ConferenceRepository conferenceRepository;
    private UserRepository userRepository;
    private MailService mailService;

    private static final int MAX_ORG_COMMENT_LENGTH = 1000;
    public static final int MAX_ADDITIONAL_INFO_LENGTH = 1500;

    public TalkServiceImpl(TalkRepository talkRepository, ModelMapper modelMapper, LevelRepository levelRepository, LanguageRepository languageRepository, TopicRepository topicRepository, TypeRepository typeRepository, ConferenceRepository conferenceRepository, UserRepository userRepository, MailService mailService) {
        this.talkRepository = talkRepository;
        this.modelMapper = modelMapper;
        this.levelRepository = levelRepository;
        this.languageRepository = languageRepository;
        this.topicRepository = topicRepository;
        this.typeRepository = typeRepository;
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public void save(Talk talk) {
        talkRepository.save(talk);
    }

    @Override
    @Transactional
    public void save(Talk talk, User user) {
        talk.setStatus(TalkStatus.getStatusByName(DEFAULT_TALK_STATUS));
        talk.setUser(user);
        talkRepository.save(talk);
    }

    @Override
    @Transactional
    public Talk save(TalkDto talkDto, User user, String multipartFilePath) {
        Talk talk = modelMapper.map(talkDto, Talk.class);

        Long conferenceId = talkDto.getId();
        if (conferenceId != null) {
            talk.setConference(conferenceRepository.findById(conferenceId));
        }
        talk.setTime(LocalDateTime.now());

        talk.setUser(user);
        if (multipartFilePath != null) {
            talk.setPathToAttachedFile(multipartFilePath);
        }
        talk.setLanguage(getByNameLanguage(talkDto.getLanguageName()));
        talk.setLevel(getByNameLevel(talkDto.getLevelName()));
        talk.setTopic(getByNameTopic(talkDto.getTopicName()));
        talk.setType(getByNameType(talkDto.getTypeName()));

        talkRepository.save(talk);

        List<User> organisersUsers = userRepository.findAllWithRoles(new UserByRole(Role.ORGANISER));

        mailService.notifyUsers(organisersUsers, new SubmitNewTalkOrganiserPreparator(talk, mailService.getUrl()));
        mailService.sendEmail(user, new SubmitNewTalkSpeakerPreparator());

        return talk;
    }

    @Override
    @Transactional
    public void update(Talk talk) {
        talkRepository.update(talk);
    }

    @Override
    @Transactional
    public void updateAsOrganiser(TalkDto talkDto, User user) {
        checkDtoBeforeUpdateAsOrganiser(talkDto);
        Talk talk = findTalkById(talkDto.getId());
        checkIfAllowedToChangeStatus(talk, talkDto.getStatusName());
        talk.setOrganiser(user);
        talk.setOrganiserComment(talkDto.getOrganiserComment());
        talkRepository.update(talk);
        List<User> receivers = getByRoleExceptCurrent(user, Role.ORGANISER);
        mailService.notifyUsers(receivers, new ChangeTalkStatusOrganiserPreparator(user, talk));
        if(!(talk.getStatus()==TalkStatus.IN_PROGRESS && talk.isValidComment())){
            mailService.sendEmail(talk.getUser(), new ChangeTalkStatusSpeakerPreparator(talk));
        }
    }

    @Override
    @Transactional
    public void updateAsSpeaker(TalkDto talkDto, User user) {
        checkDtoBeforeUpdateAsSpeaker(talkDto);
        Talk talk = findTalkById(talkDto.getId());
        if(isForbiddenToChangeTalk(user, talk)){
            throw new TalkValidationException(NOT_ALLOWED_TO_UPDATE);
        }
        talk.setLanguage(getByNameLanguage(talkDto.getLanguageName()));
        talk.setLevel(getByNameLevel(talkDto.getLevelName()));
        talk.setTopic(getByNameTopic(talkDto.getTopicName()));
        talk.setType(getByNameType(talkDto.getTypeName()));

        if (talkDto.getTitle() != null) {
            talk.setTitle(talkDto.getTitle());
        }
        if (talkDto.getDescription() != null) {
            talk.setDescription(talkDto.getDescription());
        }
        if (talkDto.getAdditionalInfo() != null) {
            talk.setAdditionalInfo(talkDto.getAdditionalInfo());
        }

        talkRepository.update(talk);
        if(talk.getOrganiser() != null){
            mailService.sendEmail(talk.getOrganiser(), new ChangeTalkBySpeakerPreparator(talk, mailService.getUrl()));
        }
    }

    @Override
    @Transactional
    public void remove(Talk talk) {
        talkRepository.remove(talk);
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

    @Override
    public TalkDto findById(Long id) {
        return entityToDto(findTalkById(id));
    }

    @Override
    public List<TalkDto> getTalksForSpeaker(String userEmail) {
        User currentUser = getByEmail(userEmail);
        return findByUserId(currentUser.getId())
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TalkDto> getTalksForOrganiser() {
        return findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Moved method from LanguageService
     * @param name
     * @return
     */
    private Language getByNameLanguage(String name) {
        List<Language> languages = languageRepository.findBySpecification(new LanguageByName(name));
        if (languages.isEmpty()) {
            throw new LanguageNotFoundException();
        }
        return languages.get(0);
    }

    /**
     * Moved method from LevelService
     * @param name
     * @return
     */
    private Level getByNameLevel(String name) {
        List<Level> levels = levelRepository.findBySpecification(new LevelByName(name));
        if (levels.isEmpty()) {
            throw new LevelNotFoundException();
        }
        return levels.get(0);
    }

    /**
     * Moved method from TopicService
     * @param name
     * @return
     */
    private Topic getByNameTopic(String name) {
        List<Topic> list = topicRepository.findBySpecification(new TopicByName(name));
        if (list.isEmpty()) {
            throw new TopicNotFoundException();
        }
        return list.get(0);
    }

    /**
     * Moved method from TypeService
     * @param name
     * @return
     */
    private Type getByNameType(String name) {
        List<Type> list = typeRepository.findBySpecification(new TypeByName(name));
        if (list.isEmpty()) {
            throw new TypeNotFoundException();
        }
        return list.get(0);
    }

    /**
     * Moved method from UserService to find User by email
     * @param email
     * @return
     */
    private User getByEmail(String email) {
        List<User> users = userRepository.findBySpecification(new UserByEmail(email));
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    /**
     * Moved method
     * @param currentUser
     * @param roleName
     * @return
     */
    private List<User> getByRoleExceptCurrent(User currentUser, String roleName) {
        return userRepository.findAllWithRoles(
                new AndSpecification<>(
                        new UserByRole(roleName),
                        new UserExceptThisById(currentUser.getId())
                )
        );
    }

    /**
     * TODO:
     * @param talk
     * @return
     */
    private TalkDto entityToDto(Talk talk) {
        TalkDto dto = modelMapper.map(talk, TalkDto.class);
        dto.setSpeakerFullName(talk.getUser().getFullName());
        dto.setStatusName(talk.getStatus().getName());
        dto.setDate(talk.getTime().toString());
        if (talk.getConference() != null) {
            Conference conference = talk.getConference();
            dto.setConferenceId(conference.getId());
            dto.setConferenceName(conference.getTitle());
        }

        User organiser = talk.getOrganiser();
        if (organiser != null) {
            dto.setAssignee(organiser.getFullName());
        }
        return dto;
    }

    /**
     * Validate method TODO: move to new Validation class
     * @param message
     * @param maxSize
     * @return
     */
    private boolean validateStringMaxLength(String message, int maxSize) {
        return message == null || message.length() <= maxSize;
    }

    private void checkDtoBeforeUpdateAsOrganiser(TalkDto talkDto) {
        if (!validateStringMaxLength(talkDto.getOrganiserComment(), MAX_ORG_COMMENT_LENGTH)) {
            throw new TalkValidationException(ORG_COMMENT_TOO_LONG);
        } else if (talkDto.getStatusName() == null) {
            throw new TalkValidationException(STATUS_IS_NULL);
        } else if (TalkStatus.getStatusByName(talkDto.getStatusName()) == null) {
            throw new TalkValidationException(STATUS_IS_WRONG);
        } else if (talkDto.getStatusName().equals(TalkStatus.REJECTED.getName()) && (talkDto.getOrganiserComment() == null || talkDto.getOrganiserComment().isEmpty())) {
            throw new TalkValidationException(ORG_COMMENT_IS_EMPTY);
        }

    }

    private void checkDtoBeforeUpdateAsSpeaker(TalkDto talkDto){
        if (!validateStringMaxLength(talkDto.getAdditionalInfo(), MAX_ADDITIONAL_INFO_LENGTH)){
            throw new TalkValidationException(ADDITIONAL_COMMENT_TOO_LONG);
        }
    }

    private void checkIfAllowedToChangeStatus(Talk talk, String status) {
        if (!talk.setStatus(TalkStatus.getStatusByName(status))) {
            throw new TalkValidationException(STATUS_IS_WRONG);
        }
    }

    private boolean isForbiddenToChangeTalk(User user, Talk talk) {
        return talk.getUser().getId() != user.getId() || talk.getStatus() == TalkStatus.REJECTED || talk.getStatus()== TalkStatus.APPROVED;
    }

}
