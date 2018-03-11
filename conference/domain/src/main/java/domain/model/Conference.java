package domain.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {
        "topics", "types", "languages", "levels", "talks", "organisers", "speakers"
})
@ToString(exclude = {"topics", "types", "languages", "levels", "talks", "organisers", "speakers"})
@Entity
public class Conference extends AbstractEntity {

    @NonNull
    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String location;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private LocalDate notificationDue;

    @Column
    private LocalDate callForPaperStartDate;

    @Column
    private LocalDate callForPaperEndDate;

    @Column
    private String pathToLogo;

    @Transient
    private Boolean callForPaperActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private Collection<Topic> topics;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private Collection<Type> types;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Collection<Language> languages;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "level_id")
    )
    private Collection<Level> levels;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conference")
    private Collection<Talk> talks;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "conference_organiser",
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "organiser_id")
    )
    private List<User> organisers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "conference_speaker",
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "speaker_id")
    )
    private List<User> speakers;

    @Builder
    public Conference(Long id, String title, String description, String location, LocalDate startDate,
                      LocalDate endDate, LocalDate notificationDue, LocalDate callForPaperStartDate,
                      LocalDate callForPaperEndDate, String pathToLogo, Boolean callForPaperActive,
                      Collection<Topic> topics, Collection<Type> types, Collection<Language> languages,
                      Collection<Level> levels, Collection<Talk> talks, List<User> organisers, List<User> speakers) {
        super(id);
        this.title = title;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notificationDue = notificationDue;
        this.callForPaperStartDate = callForPaperStartDate;
        this.callForPaperEndDate = callForPaperEndDate;
        this.pathToLogo = pathToLogo;
        this.callForPaperActive = callForPaperActive;
        this.topics = topics;
        this.types = types;
        this.languages = languages;
        this.levels = levels;
        this.talks = talks;
        this.organisers = organisers;
        this.speakers = speakers;
    }
}
