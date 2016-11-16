package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "talk")
@SequenceGenerator(name = "seqTalkGen", allocationSize = 1)
public class Talk {

    @Id
    @Column(name = "talk_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTalkGen")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @NotNull
    @OneToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Size(max = 250)
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Size(max = 3000)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Size(max = 1500)
    @Column(name = "additional_info", nullable = false)
    private String additionalInfo;
}
