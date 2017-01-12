package ua.rd.cm.repository.specification.talk;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.specification.Specification;

public class TalkById implements Specification<Talk>{

	private Long id;
	
	public TalkById(Long id) {
		this.id = id;
	}

	@Override
	public boolean test(Talk talk) {
		return talk.getId().equals(id);
	}

	@Override
	public String toSqlClauses() {
		return String.format(" t.id = '%s' ", id);
	}
	
}
