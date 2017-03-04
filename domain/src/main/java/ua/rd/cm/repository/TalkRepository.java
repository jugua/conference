package ua.rd.cm.repository;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.specification.Specification;

/**
 * Interface Talk repository declare
 * CRUD operations with {@link Talk} entity via repository pattern.
 *
 * @see Talk
 * @see Specification
 */
public interface TalkRepository extends CrudRepository<Talk> {

}
