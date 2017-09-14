package ua.rd.cm.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "token_seq")
@AttributeOverride(name = "id", column = @Column(name = "token_id"))
public class VerificationToken extends AbstractEntity {
    public static final int EXPIRATION_IN_MINUTES = 60;

    @Column(nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenStatus status;

    public long calculateSecondsToExpiry() {
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), expiryDate);
    }

    public enum TokenType {
        CONFIRMATION, FORGOT_PASS, CHANGING_EMAIL
    }

    public enum TokenStatus {
        VALID, EXPIRED
    }
}
