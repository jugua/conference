package ua.rd.cm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.rd.cm.domain.ContactType;

import java.util.List;

public interface ContactTypeRepository extends JpaRepository<ContactType,Long> {

    ContactType findById(Long id);

    List<ContactType> findByName(String name);

    default void update(ContactType contactType ) {

    };

}
