package ua.rd.cm.repository.specification;

import javax.persistence.Query;
import java.util.function.Predicate;

public interface Specification<T> extends Predicate<T> {

    String toSqlClauses();

    default Query setParameters(Query query) {
        return query;
    }
}
