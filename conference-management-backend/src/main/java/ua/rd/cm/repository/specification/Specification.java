package ua.rd.cm.repository.specification;

import java.util.function.Predicate;

public interface Specification<T> extends Predicate<T>{
	
	String toSqlClauses();
	
}
