package ua.rd.cm.repository.specification.verificationtoken;

import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.specification.Specification;

public class VerificationTokenByToken implements Specification<VerificationToken>{

    private String token;

    public VerificationTokenByToken(String token) {
        this.token = token;
    }

    @Override
    public String toSqlClauses() {
        return String .format("v.token = '%s'", token);
    }

    @Override
    public boolean test(VerificationToken verificationToken) {
        return verificationToken.getToken().equals(token);
    }
}