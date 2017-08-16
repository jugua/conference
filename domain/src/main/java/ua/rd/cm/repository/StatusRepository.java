package ua.rd.cm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.repository.specification.Specification;
import java.util.List;


public interface StatusRepository {

    void saveStatus(TalkStatus status);

    List<TalkStatus> findAll();

    List<TalkStatus> findBySpecification(Specification<TalkStatus> spec);

}
