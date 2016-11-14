package ua.rd.cm.repository.specification.user;


import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

public class UserById implements Specification<User>{

	private Long id;
	
	public UserById(Long id) {
		this.id = id;
	}

	@Override
	public String toSqlClauses() {
		return String.format(" u.id = %s ", id);
	}

	@Override
	public boolean test(User usr) {
		return usr.getId().equals(id);
	}

}
 