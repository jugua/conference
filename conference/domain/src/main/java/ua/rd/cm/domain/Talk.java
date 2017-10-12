package ua.rd.cm.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "time"})})
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "talk_seq")
public class Talk extends AbstractEntity {

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TalkStatus status;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @NonNull
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @NonNull
    @Column(name = "title", nullable = false, length = 250)
    private String title;

    @NonNull
    @Column(name = "description", nullable = false, length = 3000)
    private String description;

    @Column(name = "additional_info", length = 1500)
    private String additionalInfo;

    @Column(name = "organiser_comment", length = 1000)
    private String organiserComment;

    @ManyToOne
    @JoinColumn(name = "organiser_id")
    private User organiser;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @Column(name = "attached_file")
    private String pathToAttachedFile;

    @Builder
    public Talk(Long id, User user, TalkStatus status, Topic topic, Type type, Language language, Level level,
                LocalDateTime time, String title, String description, String additionalInfo, String organiserComment,
                User organiser, Conference conference, String pathToAttachedFile) {
        super(id);
        this.user = user;
        this.status = status;
        this.topic = topic;
        this.type = type;
        this.language = language;
        this.level = level;
        this.time = time;
        this.title = title;
        this.description = description;
        this.additionalInfo = additionalInfo;
        this.organiserComment = organiserComment;
        this.organiser = organiser;
        this.conference = conference;
        this.pathToAttachedFile = pathToAttachedFile;
    }

    public boolean setStatus(TalkStatus status) {
        if (this.status == null) {
            this.status = status;
        }
        if (this.status.canChangeTo(status)) {
            this.status = status;
            return true;
        }
        return false;
    }

    public boolean isValidComment() {
        return organiserComment != null && organiserComment.length() > 0;
    }

}
