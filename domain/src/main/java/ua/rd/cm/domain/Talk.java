package ua.rd.cm.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "talk_seq")
@Table(name = "talk", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "time"})
})
@AttributeOverride(name = "id", column = @Column(name = "talk_id"))
public class Talk extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TalkStatus status;

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

    @ManyToOne
    private User organiser;

    @ManyToOne
    private Conference conference;

    @Column(name = "attached_file")
    private String pathToAttachedFile;

    public boolean setStatus(TalkStatus status){
        if(this.status==null){
            this.status=status;
        }
        if(this.status.canChangeTo(status)){
            this.status=status;
            return true;
        }
        return false;
    }

    public boolean isValidComment(){
        return organiserComment != null && organiserComment.length() > 0;
    }

}
