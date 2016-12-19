package ua.rd.cm.repository.specification.verificationtoken;

import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Mariia Lapovska
 */
public class VerificationTokenByStatus implements Specification<VerificationToken> {

    private VerificationToken.TokenStatus tokenStatus;

    public VerificationTokenByStatus(VerificationToken.TokenStatus tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" v.status = '%s'", tokenStatus);
    }

    @Override
    public boolean test(VerificationToken verificationToken) {
        return verificationToken.getStatus().equals(tokenStatus);
    }
}
