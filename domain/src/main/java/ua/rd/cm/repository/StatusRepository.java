package ua.rd.cm.repository;

import ua.rd.cm.domain.Status;
import ua.rd.cm.repository.specification.Specification;
import java.util.List;


public interface StatusRepository {

    void saveStatus(Status status);

    List<Status> findAll();

    List<Status> findBySpecification(Specification<Status> spec);

}
