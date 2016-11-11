package ua.rd.cm.repository;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

public class UserSpecificationByFirstName implements Specification<User>{

	private String firstName;
	
	public UserSpecificationByFirstName(String firstName) {
		super();
		this.firstName = firstName;
	}

	@Override
	public boolean isSatisfiedBy(User usr) {
		return usr.getFirstName().equals(firstName);
	}

	@Override
	public String toSqlClauses() {
		return String.format(" u.first_name = %s ", firstName);
	}

}
