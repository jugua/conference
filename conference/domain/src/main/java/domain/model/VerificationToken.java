package domain.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

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

    public static final int DEFAULT_EXPIRATION_TIME_IN_MINUTES = 60;

    @NonNull
    @Column(nullable = false)
    private String token;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NonNull
    @Column(nullable = false)
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

    public static VerificationToken of(User user, VerificationToken.TokenType tokenType) {
        return VerificationToken.builder()
                .user(user)
                .expiryDate(generateDefaultExpiryDate())
                .token(UUID.randomUUID().toString())
                .type(tokenType)
                .status(VerificationToken.TokenStatus.VALID)
                .build();
    }

    public static VerificationToken createChangeEmailToken(
            User user, VerificationToken.TokenType tokenType, String newEmail) {
        VerificationToken token = VerificationToken.of(user, tokenType);
        token.setToken(token.getToken() + "|" + newEmail);
        return token;
    }

    public boolean isInvalid(TokenType tokenType) {
        return !(type.equals(tokenType) && TokenStatus.VALID.equals(status));
    }

    public boolean isExpired() {
        return TokenStatus.EXPIRED.equals(status) || isExpiredByTime();
    }

    public boolean isExpiredByTime() {
        return secondsToExpiry() <= 0;
    }

    public long secondsToExpiry() {
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), expiryDate);
    }

    public static LocalDateTime generateDefaultExpiryDate() {
        return VerificationToken.generateExpiryDateFor(VerificationToken.DEFAULT_EXPIRATION_TIME_IN_MINUTES);
    }

    public static LocalDateTime generateExpiryDateFor(int minutes) {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.plusMinutes(minutes);
    }

    public void expire() {
        setStatus(TokenStatus.EXPIRED);
    }

    public enum TokenType {
        CONFIRMATION, FORGOT_PASS, CHANGING_EMAIL
    }

    public enum TokenStatus {
        VALID, EXPIRED
    }
}
