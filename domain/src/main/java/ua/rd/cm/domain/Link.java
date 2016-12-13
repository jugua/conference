package ua.rd.cm.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@SequenceGenerator(name = "seqLinkGen", allocationSize = 1,
        sequenceName = "link_seq")
public class Link {

    @Id
    @Column(name = "link_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLinkGen")
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LinkStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime time;

    public enum LinkStatus {
        VALID, EXPIRED
    }
}
