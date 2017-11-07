package ua.rd.cm.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class VerificationToken extends AbstractEntity {

    public static final int EXPIRATION_IN_MINUTES = 60;

    @NonNull
    @Column(nullable = false)
    private String token;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NonNull
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenType type;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenStatus status;

    @Builder
    public VerificationToken(Long id, String token,
                             User user, LocalDateTime expiryDate,
                             TokenType type, TokenStatus status) {
        super(id);
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
        this.type = type;
        this.status = status;
    }

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
