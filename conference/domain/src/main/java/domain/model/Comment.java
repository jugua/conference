package domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class Comment extends AbstractEntity {

    @Column(nullable = false)
    @NonNull
    private String message;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private User user;

    @NonNull
    private long talkId;

    @Builder
    public Comment(Long id, String message, LocalDateTime time, User user, long talkId) {
        super(id);
        this.message = message;
        this.time = time;
        this.user = user;
        this.talkId = talkId;
    }

}
