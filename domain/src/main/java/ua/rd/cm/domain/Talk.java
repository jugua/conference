package ua.rd.cm.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "title", nullable = false, length = 250)
    private String title;

    @Column(name = "description", nullable = false, length = 3000)
    private String description;

    @Column(name = "additional_info", length = 1500)
    private String additionalInfo;

    @Column(name="organiser_comment", length=1000)
    private String organiserComment;

    public boolean setStatus(Status status){
        if(this.status.canChangeTo(status)){
            this.status=status;
            return true;
        }
        return false;
    }
}
