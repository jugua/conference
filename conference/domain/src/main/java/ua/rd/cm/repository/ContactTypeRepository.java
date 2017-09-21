package ua.rd.cm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.ContactType;

@RepositoryRestResource(exported = false)
@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {

    ContactType findById(Long id);

    List<ContactType> findByName(String name);

}
