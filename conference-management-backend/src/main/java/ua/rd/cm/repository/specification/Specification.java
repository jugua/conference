package ua.rd.cm.repository.specification;

public interface Specification<T> {

	boolean isSatisfiedBy(T t);
	
	String toSqlClauses();
	
}
