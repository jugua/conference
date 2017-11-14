package domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.model.Talk;

@Repository
public interface TalkRepository extends CrudRepository<Talk, Long> {

    @Override
    List<Talk> findAll();

    Talk findById(Long id);

    List<Talk> findByUserId(Long userId);

    Long countByUserId(Long userId);

}
