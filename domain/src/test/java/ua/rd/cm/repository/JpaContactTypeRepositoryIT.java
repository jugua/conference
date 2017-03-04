package ua.rd.cm.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.specification.contacttype.ContactTypeByName;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Olha_Melnyk
 */
public class JpaContactTypeRepositoryIT extends RepositoryTestConfig {

    @Autowired
    private ContactTypeRepository contactTypeRepository;

    @PersistenceContext
    private EntityManager em;

    private List<ContactType> contactTypes;

    private void insert(List<ContactType> contactTypes) {
        for (ContactType contactType: contactTypes) {
            jdbcTemplate.update("INSERT INTO contact_type (contact_type_id, contact_type_name) "
                    + "values(?,?)", contactType.getId(), contactType.getName());
        }
    }

    @Before
    public void setUp() {
        contactTypes = new ArrayList<ContactType>() {{
            add(new ContactType(100L, "VK"));
            add(new ContactType(101L, "Twitter"));
            add(new ContactType(102L, "LinkedIn"));
        }};
        insert(contactTypes);
    }

    @After
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM contact_type");
    }

    @Test
    public void testSaveContactType() {
        ContactType contactType = new ContactType();
        contactType.setName("YouTube");
        contactTypeRepository.save(contactType);
        em.flush();

        String actualName = jdbcTemplate.queryForObject("SELECT n.contact_type_name FROM contact_type n WHERE n.contact_type_id= 2", String.class);
        assertEquals(contactType.getName(), actualName);

    }

    @Test
    public void testUpdateContactType() {
        ContactType contactType = new ContactType();
        contactType.setName("YouTube");
        contactTypeRepository.save(contactType);
        contactType.setName("RuTube");
        contactTypeRepository.update(contactType);
        em.flush();
        String actualName = jdbcTemplate.queryForObject("SELECT n.contact_type_name FROM contact_type n WHERE n.contact_type_id= 1", String.class);
        assertEquals(contactType.getName(), actualName);
    }

    @Test
    public void testFindAllContactTypes() {
        List<ContactType> all = contactTypeRepository.findAll();
        assertEquals(contactTypes, all);
    }

    @Test
    public void testFindByNameContactTypes() {
        List<ContactType> list = contactTypeRepository.findBySpecification(new ContactTypeByName("VK"));
        assertEquals(1, list.size());
    }
}
