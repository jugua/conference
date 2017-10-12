package ua.rd.cm.services.businesslogic.impl;

import static ua.rd.cm.services.exception.TalkValidationException.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.cm.domain.*;
import ua.rd.cm.dto.TalkDto;
import ua.rd.cm.repository.*;
import ua.rd.cm.infrastructure.mail.MailService;
import ua.rd.cm.services.businesslogic.TalkService;
import ua.rd.cm.services.exception.TalkNotFoundException;
import ua.rd.cm.services.exception.TalkValidationException;
import ua.rd.cm.infrastructure.mail.preparator.*;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TalkServiceImpl implements TalkService {
    private static final int MAX_ORG_COMMENT_LENGTH = 1000;
    private static final int MAX_ADDITIONAL_INFO_LENGTH = 1500;
    private TalkRepository talkRepository;
    private ModelMapper modelMapper;
    private LevelRepository levelRepository;
    private LanguageRepository languageRepository;
    private TopicRepository topicRepository;
    private TypeRepository typeRepository;
    private ConferenceRepository conferenceRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private MailService mailService;

    @Override
    @Transactional
    public Talk save(TalkDto talkDto, User user, String multipartFilePath) {
        Talk talk = modelMapper.map(talkDto, Talk.class);

        if (talkDto.getStatusName() != null) {
            talk.setStatus(TalkStatus.getStatusByName(talkDto.getStatusName()));
        }
        if (talkDto.getDate() != null) {
            talk.setTime(LocalDateTime.parse(talkDto.getDate()));
        } else {
            talk.setTime(LocalDateTime.now());
        }

        Long conferenceId = talkDto.getId();
        if (conferenceId != null) {
            talk.setConference(conferenceRepository.findById(conferenceId));
        }

        talk.setUser(user);
        if (multipartFilePath != null && !multipartFilePath.equals("")) {
            talk.setPathToAttachedFile(multipartFilePath);
        }
        talk.setLanguage(languageRepository.findByName(talkDto.getLanguageName()));
        talk.setLevel(levelRepository.findByName(talkDto.getLevelName()));
        talk.setTopic(topicRepository.findTopicByName(talkDto.getTopicName()));
        talk.setType(typeRepository.findByName(talkDto.getTypeName()));

        talkRepository.save(talk);

        List<User> organisersUsers = userRepository.findAllByRolesIsIn(roleRepository.findByName(Role.ORGANISER));

        mailService.notifyUsers(organisersUsers, new SubmitNewTalkOrganiserPreparator(talk, mailService.getUrl()));
        mailService.sendEmail(user, new SubmitNewTalkSpeakerPreparator());

        return talk;
    }


    @Override
    public void addFile(TalkDto talkDto, String multipartFilePath) {
        Talk talk = findTalkById(talkDto.getId());
        if (multipartFilePath != null) {
            talk.setPathToAttachedFile(multipartFilePath);
        }
        talkRepository.save(talk);
    }

    @Override
    public void deleteFile(TalkDto talkDto, boolean deleteFile) {
        Talk talk = findTalkById(talkDto.getId());
        talk.setPathToAttachedFile(null);
        talkRepository.save(talk);
    }

    @Override
    public String getFilePath(TalkDto talkDto) {
        Talk talk = findTalkById(talkDto.getId());
        return talk.getPathToAttachedFile();
    }

    @Override
    @Transactional
    public void updateAsOrganiser(TalkDto talkDto, User user) {
        checkDtoBeforeUpdateAsOrganiser(talkDto);
        Talk talk = findTalkById(talkDto.getId());
        checkIfAllowedToChangeStatus(talk, talkDto.getStatusName());
        talk.setOrganiser(user);
        talk.setOrganiserComment(talkDto.getOrganiserComment());
        talkRepository.save(talk);
        List<User> receivers = userRepository.findAllByRolesIsIn(roleRepository.findByName(Role.ORGANISER)).stream().filter(u -> u != user).collect(Collectors.toList());
        mailService.notifyUsers(receivers, new ChangeTalkStatusOrganiserPreparator(user, talk));
        if (!(talk.getStatus() == TalkStatus.IN_PROGRESS && talk.isValidComment())) {
            mailService.sendEmail(talk.getUser(), new ChangeTalkStatusSpeakerPreparator(talk));
        }
    }

    @Override
    @Transactional
    public void updateAsSpeaker(TalkDto talkDto, User user) {
        checkDtoBeforeUpdateAsSpeaker(talkDto);
        Talk talk = findTalkById(talkDto.getId());
        if (isForbiddenToChangeTalk(user, talk)) {
            throw new TalkValidationException(NOT_ALLOWED_TO_UPDATE);
        }
        talk.setLanguage(languageRepository.findByName(talkDto.getLanguageName()));
        talk.setLevel(levelRepository.findByName(talkDto.getLevelName()));
        talk.setTopic(topicRepository.findTopicByName(talkDto.getTopicName()));
        talk.setType(typeRepository.findByName(talkDto.getTypeName()));

        if (talkDto.getTitle() != null) {
            talk.setTitle(talkDto.getTitle());
        }
        if (talkDto.getDescription() != null) {
            talk.setDescription(talkDto.getDescription());
        }
        if (talkDto.getAdditionalInfo() != null) {
            talk.setAdditionalInfo(talkDto.getAdditionalInfo());
        }

        talkRepository.save(talk);
        if (talk.getOrganiser() != null) {
            mailService.sendEmail(talk.getOrganiser(), new ChangeTalkBySpeakerPreparator(talk, mailService.getUrl()));
        }
    }

    @Override
    public List<Talk> findAll() {
        return talkRepository.findAll();
    }

    @Override
    public List<Talk> findByUserId(Long id) {
        List<Talk> talks = talkRepository.findByUserId(id);
        if (talks.isEmpty()) {
            throw new TalkNotFoundException();
        }
        return talks;
    }

    @Override
    public Talk findTalkById(Long id) {
        Talk talk = talkRepository.findById(id);
        if (talk == null) {
            throw new TalkNotFoundException();
        }
        return talk;
    }

    @Override
    public TalkDto findById(Long id) {
        return entityToDto(findTalkById(id));
    }

    @Override
    public List<TalkDto> getTalksForSpeaker(String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail);
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
     *
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

    private void checkDtoBeforeUpdateAsSpeaker(TalkDto talkDto) {
        if (!validateStringMaxLength(talkDto.getAdditionalInfo(), MAX_ADDITIONAL_INFO_LENGTH)) {
            throw new TalkValidationException(ADDITIONAL_COMMENT_TOO_LONG);
        }
    }

    private void checkIfAllowedToChangeStatus(Talk talk, String status) {
        TalkStatus talkStatus = TalkStatus.getStatusByName(status);
        if (talk.getStatus() != talkStatus && !talk.setStatus(talkStatus)) {
            throw new TalkValidationException(STATUS_IS_WRONG);
        }
    }

    private boolean isForbiddenToChangeTalk(User user, Talk talk) {
        boolean isUsersTalk = talk.getUser().getId() != user.getId();
        return isUsersTalk || talk.getStatus() == TalkStatus.REJECTED || talk.getStatus() == TalkStatus.APPROVED;
    }

}
