package ua.rd.cm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.ContactType;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {

    ContactType findById(Long id);

    ContactType findFirstByName(String name);

}
