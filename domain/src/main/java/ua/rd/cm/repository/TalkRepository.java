package ua.rd.cm.repository;

import java.util.List;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.specification.Specification;

/**
 * Interface Talk repository declare 
 * CRUD operations with {@link Talk} entity via repository pattern.
 *
 *@see Talk
 *@see Specification
 */
public interface TalkRepository {

	void saveTalk(Talk talk);
	
	void updateTalk(Talk talk);
	
	void removeTalk(Talk talk);
	
	List<Talk> findAll();
	
	List<Talk> findBySpecification(Specification<Talk> spec);
	
}
