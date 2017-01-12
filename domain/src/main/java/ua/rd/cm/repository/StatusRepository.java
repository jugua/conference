package ua.rd.cm.repository;

import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.repository.specification.Specification;
import java.util.List;


public interface StatusRepository {

    void saveStatus(TalkStatus status);

    List<TalkStatus> findAll();

    List<TalkStatus> findBySpecification(Specification<TalkStatus> spec);

}
