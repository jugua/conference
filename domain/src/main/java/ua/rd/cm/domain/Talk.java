package ua.rd.cm.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "additional_info")
    private String additionalInfo;
}
