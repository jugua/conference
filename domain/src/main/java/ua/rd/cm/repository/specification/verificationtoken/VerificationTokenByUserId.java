package ua.rd.cm.repository.specification.verificationtoken;

import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.specification.Specification;

public class VerificationTokenByUserId implements Specification<VerificationToken>{

    private Long userId;

    public VerificationTokenByUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" v.user.id = '%s'", userId);
    }

    @Override
    public boolean test(VerificationToken verificationToken) {
        return verificationToken.getUser().equals(userId);
    }
}
