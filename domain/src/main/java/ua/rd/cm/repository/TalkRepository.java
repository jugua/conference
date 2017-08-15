package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

/**
 * Interface Talk repository declare
 * CRUD operations with {@link Talk} entity via repository pattern.
 *
 * @see Talk
 * @see Specification
 */
public interface TalkRepository extends CrudRepository<Talk,Long> {


    @Override
    List<Talk> findAll();

    Talk findById(Long id);

    List<Talk> findByUserId(Long userId);


}
