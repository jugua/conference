package ua.rd.cm.repository.specification.user;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

public class UserByFirstName implements Specification<User>{

	private String firstName;
	
	public UserByFirstName(String firstName) {
		super();
		this.firstName = firstName;
	}

	@Override
	public String toSqlClauses() {
		return String.format(" u.firstName = '%s' ", firstName);
	}

	@Override
	public boolean test(User usr) {
		return usr.getFirstName().equals(firstName);
	}

}
