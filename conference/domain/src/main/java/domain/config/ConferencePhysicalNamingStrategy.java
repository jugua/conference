package domain.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class ConferencePhysicalNamingStrategy implements PhysicalNamingStrategy {

    private static final String SEPARATOR = "_";

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        final List<String> parts = splitAndReplace(name.getText());
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(join(parts), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        final List<String> parts = splitAndReplace(name.getText());
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(join(parts), name.isQuoted());
    }

    private LinkedList<String> splitAndReplace(String name) {
        LinkedList<String> result = new LinkedList<>();
        for (String part : StringUtils.splitByCharacterTypeCamelCase(name)) {
            if (part == null || part.trim().isEmpty() || part.trim().equals(SEPARATOR)) {
                // skip null and space
                continue;
            }
            result.add(part.toLowerCase(Locale.ROOT));
        }
        return result;
    }

    private String join(List<String> parts) {
        boolean firstPass = true;
        String separator = StringUtils.EMPTY;
        StringBuilder joined = new StringBuilder();
        for (String part : parts) {
            joined.append(separator).append(part);
            if (firstPass) {
                firstPass = false;
                separator = SEPARATOR;
            }
        }
        return joined.toString();
    }
}
