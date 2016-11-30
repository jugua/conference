package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@SequenceGenerator(name = "seqTalkGen", allocationSize = 1,
        sequenceName = "talk_seq")
@Table(name = "talk", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "time"})
})
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
    @ManyToOne
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

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @NotNull
    @Size(max = 250)
    @Column(name = "title", nullable = false, length = 250)
    private String title;

    @NotNull
    @Size(max = 3000)
    @Column(name = "description", nullable = false, length = 3000)
    private String description;

    @NotNull
    @Size(max = 1500)
    @Column(name = "additional_info", nullable = false, length = 1500)
    private String additionalInfo;
}
