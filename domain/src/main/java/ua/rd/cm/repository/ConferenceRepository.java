package ua.rd.cm.repository;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

public interface ConferenceRepository {

    Conference findById(Long id);

    void save(Conference conference);

    void update(Conference conference);

    void remove(Conference conference);

    List<Conference> findAll();

    List<Conference> findBySpecification(Specification<Conference> spec);
}
