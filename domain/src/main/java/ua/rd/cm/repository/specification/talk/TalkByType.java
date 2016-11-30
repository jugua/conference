package ua.rd.cm.repository.specification.talk;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.specification.Specification;

public class TalkByType implements Specification<Talk>{

	private Long typeId;
	
	public TalkByType(Long typeId) {
		super();
		this.typeId = typeId;
	}

	@Override
	public boolean test(Talk t) {
		return t.getType().getId().equals(typeId);
	}

	@Override
	public String toSqlClauses() {
		return String.format(" t.type_id = '%s' ", typeId);
	}
}
