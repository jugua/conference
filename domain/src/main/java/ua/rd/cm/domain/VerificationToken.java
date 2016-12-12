package ua.rd.cm.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "seqTokenGen", allocationSize = 1,
        sequenceName = "token_seq")
public class VerificationToken {

    private static final int EXPIRATION = 1;

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTokenGen")
    private Long id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime expiryDate;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private LocalDateTime calculateExpiryDate(int expiryTimeInHours){
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());
        return currentTime.plusHours(expiryTimeInHours);
    }
}
