package ua.rd.cm.repository.specification;

public abstract class CompositeSpecification<T> implements Specification<T>{

	public Specification<T> and(Specification<T> spec) {
		return new AndSpecification<>(this, spec);
	}
	
	public Specification<T> or(Specification<T> spec) {
		return new OrSpecification<>(this, spec);
	}
	
	@Override
	public abstract String toSqlClauses();
	
}
