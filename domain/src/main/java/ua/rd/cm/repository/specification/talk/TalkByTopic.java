package ua.rd.cm.repository.specification.talk;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.specification.Specification;

public class TalkByTopic implements Specification<Talk>{

	private Long topic;
	
	public TalkByTopic(Long topic) {
		super();
		this.topic = topic;
	}

	@Override
	public boolean test(Talk t) {
		return t.getTopic().equals(topic);
	}

	@Override
	public String toSqlClauses() {
		return String.format(" t.topic_id = '%s' ", topic);
	}

}
