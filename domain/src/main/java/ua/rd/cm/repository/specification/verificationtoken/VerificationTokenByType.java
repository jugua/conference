package ua.rd.cm.repository.specification.verificationtoken;

import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Mariia Lapovska
 */
public class VerificationTokenByType implements Specification<VerificationToken> {

    private VerificationToken.TokenType tokenType;

    public VerificationTokenByType(VerificationToken.TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" v.type = '%s'", tokenType);
    }

    @Override
    public boolean test(VerificationToken verificationToken) {
        return verificationToken.getType().equals(tokenType);
    }
}
