package ua.rd.cm.repository.specification;

public class OrSpecification<T> extends CompositeSpecification<T>{
	
	private Specification<T> left;
	private Specification<T> right;
	
	public OrSpecification(Specification<T> left, Specification<T> right) {
		super();
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean isSatisfiedBy(T t) {
		return left.isSatisfiedBy(t) || right.isSatisfiedBy(t);
	}

	@Override
	public String toSqlClauses() {
		return left.toSqlClauses() + " OR " + right.toSqlClauses();
	}

	
}
