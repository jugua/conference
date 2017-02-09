package ua.rd.cm.repository.specification;

import javax.persistence.Query;

public class AndSpecification<T> extends CompositeSpecification<T> {

    private Specification<T> left;
    private Specification<T> right;

    public AndSpecification(Specification<T> left, Specification<T> right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    public String toSqlClauses() {
        return left.toSqlClauses() + " AND " + right.toSqlClauses();
    }

    @Override
    public Query setParameters(Query query) {
        return right.setParameters(left.setParameters(query));
    }

    @Override
    public boolean test(T t) {
        return left.test(t) && right.test(t);
    }
}
