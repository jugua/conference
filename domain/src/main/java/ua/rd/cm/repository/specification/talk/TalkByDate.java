package ua.rd.cm.repository.specification.talk;

import java.time.LocalDateTime;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.specification.Specification;

public class TalkByDate implements Specification<Talk>{

	private LocalDateTime startDate;
	private LocalDateTime endDate;

	public TalkByDate(LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public boolean test(Talk t) {
		return t.getTime().isAfter(startDate) && t.getTime().isBefore(endDate);
	}

	@Override
	public String toSqlClauses() {
		return String.format(" t.time BETWEEN '%s' AND '%s' ", startDate, endDate);
	}

}
