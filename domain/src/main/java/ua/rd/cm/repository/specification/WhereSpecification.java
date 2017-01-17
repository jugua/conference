package ua.rd.cm.repository.specification;

public class WhereSpecification<T> extends CompositeSpecification<T>{

    private Specification<T> expression;

    public WhereSpecification(Specification<T> expression) {
        this.expression = expression;
    }

    @Override
    public String toSqlClauses() {
        return " WHERE " + expression.toSqlClauses();
    }

    @Override
    public boolean test(T t) {
        return true;
    }
}
