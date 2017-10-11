package ua.rd.cm.domain;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@EqualsAndHashCode(callSuper = true, exclude = {"topics", "types", "languages", "levels", "talks", "organisers"})
@ToString(exclude = {"topics", "types", "languages", "levels", "talks", "organisers"})
@Entity
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "conf_seq")
public class Conference extends AbstractEntity {

    @NonNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "call_for_paper_start_date")
    private LocalDate callForPaperStartDate;

    @Column(name = "call_for_paper_end_date")
    private LocalDate callForPaperEndDate;

    @Column(name = "path_to_logo")
    private String pathToLogo;

    @Transient
    private Boolean callForPaperActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "conference_topic",
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private Collection<Topic> topics;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "conference_type",
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private Collection<Type> types;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "conference_language",
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Collection<Language> languages;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "conference_level",
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
    private Collection<User> organisers;

    @Builder
    public Conference(Long id, String title, String description, String location, LocalDate startDate,
                      LocalDate endDate, LocalDate callForPaperStartDate, LocalDate callForPaperEndDate,
                      String pathToLogo, Boolean callForPaperActive, Collection<Topic> topics,
                      Collection<Type> types, Collection<Language> languages, Collection<Level> levels,
                      Collection<Talk> talks, Collection<User> organisers) {
        super(id);
        this.title = title;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
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
    }
}
