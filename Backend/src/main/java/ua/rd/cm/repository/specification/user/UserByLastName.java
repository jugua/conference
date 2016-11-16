package ua.rd.cm.repository.specification.user;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

public class UserByLastName implements Specification<User>{

	private String lastName;
	
	public UserByLastName(String lastName) {
		super();
		this.lastName = lastName;
	}

	@Override
	public boolean test(User usr) {
		return usr.getLastName().equals(lastName);
	}

	@Override
	public String toSqlClauses() {
		return String.format(" u.last_name = '%s' ", lastName);
	}

}
