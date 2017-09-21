package ua.rd.cm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Talk;

@RepositoryRestResource(exported = false)
@Repository
public interface TalkRepository extends CrudRepository<Talk, Long> {

    @Override
    List<Talk> findAll();

    Talk findById(Long id);

    List<Talk> findByUserId(Long userId);

}
