package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@SequenceGenerator(name = "seqTokenGen", allocationSize = 1,
        sequenceName = "token_seq")
@Table(name = "verification_token")
public class VerificationToken {

    public static final int EXPIRATION_IN_MINUTES = 60;

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTokenGen")
    private Long id;

    @Column(nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenStatus status;

    public enum TokenType {
        CONFIRMATION, FORGOT_PASS, CHANGING_EMAIL
    }

    public enum TokenStatus {
        VALID, EXPIRED
    }

    public long calculateSecondsToExpiry() {
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), expiryDate);
    }
}
