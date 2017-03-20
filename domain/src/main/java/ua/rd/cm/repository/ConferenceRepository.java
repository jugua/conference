package ua.rd.cm.repository;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

public interface ConferenceRepository extends CrudRepository<Conference> {

    List<Conference> getAllWithTalks(Specification<Conference> spec);
}
