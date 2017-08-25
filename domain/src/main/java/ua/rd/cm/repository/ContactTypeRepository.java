package ua.rd.cm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.ContactType;

import java.util.List;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {

    ContactType findById(Long id);

    List<ContactType> findByName(String name);

}
