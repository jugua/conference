package ua.rd.cm.repository.specification.talk;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.specification.Specification;

public class TalkByUserId implements Specification<Talk>{

	private Long userId; 
	
	public TalkByUserId(Long userId) {
		super();
		this.userId = userId;
	}

	@Override
	public boolean test(Talk t) {
		return t.getUser().getId().equals(userId);
	}

	@Override
	public String toSqlClauses() {
		return String.format(" t.user_id = '%s' ", userId);
	}

}
