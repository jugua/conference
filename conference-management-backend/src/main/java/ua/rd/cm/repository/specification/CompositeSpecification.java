package ua.rd.cm.repository.specification;

public abstract class CompositeSpecification<T> implements Specification<T>{

	public Specification<T> and(Specification<T> spec) {
		return null;
	}
	
	public Specification<T> or(Specification<T> spec) {
		return null;
	}
	
	@Override
	public abstract boolean isSatisfiedBy(T t);

	@Override
	public abstract String toSqlClauses();
	
}
