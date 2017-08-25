package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Talk;

import java.util.List;

@Repository
public interface TalkRepository extends CrudRepository<Talk, Long> {

    @Override
    List<Talk> findAll();

    Talk findById(Long id);

    List<Talk> findByUserId(Long userId);

}
