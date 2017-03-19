package ua.rd.cm.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {
        "topics", "types", "languages", "levels", "talks", "organisers"
})
@ToString(exclude = {
        "topics", "types", "languages", "levels", "talks", "organisers"
})
@Entity
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "conf_seq")
@Table(name = "conference")
@AttributeOverride(name = "id", column = @Column(name = "conference_id"))
public class Conference extends AbstractEntity {

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
}
