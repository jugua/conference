package ua.rd.cm.repository;


import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

public class UserSpecificationById implements Specification<User>{

	private Long id;
	
	public UserSpecificationById(Long id) {
		this.id = id;
	}
	
	@Override
	public boolean isSatisfiedBy(User usr) {
		return usr.getId().equals(id);
	}

	@Override
	public String toSqlClauses() {
		return String.format(" u.id = %s ", id);
	}

}
 