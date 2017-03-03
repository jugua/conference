package ua.rd.cm.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.WhereSpecification;
import ua.rd.cm.repository.specification.user.UserByEmail;
import ua.rd.cm.repository.specification.user.UserByFirstName;
import ua.rd.cm.repository.specification.user.UserById;
import ua.rd.cm.repository.specification.user.UserByLastName;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JpaUserRepositoryIT extends RepositoryTestConfig {

    @Autowired
    private UserRepository repository;

    @PersistenceContext
    private EntityManager em; //added for explicit flushing from Hibernate cache.

    @Before
    public void setUp() {
        User user = new User();
        user.setId(30L);
        user.setFirstName("testName");
        user.setLastName("testSurname");
        user.setEmail("test@gmail.com");
        user.setPassword("tribel1234PASSWORD");
        user.setPhoto("testUrl");
        user.setStatus(User.UserStatus.CONFIRMED);
        saveUser(user);

        user.setId(31L);
        user.setFirstName("testName2");
        user.setLastName("testSurname2");
        user.setEmail("test2@gmail.com");
        user.setPassword("tribel1234PASSWORD2");
        user.setPhoto("testUrl2");
        user.setStatus(User.UserStatus.CONFIRMED);
        saveUser(user);
    }

    @After
    public void clear() {
        jdbcTemplate.update("DELETE FROM user");
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setFirstName("testName3");
        user.setLastName("testSurname3");
        user.setEmail("test3@gmail.com");
        user.setPassword("tribel1234PASSWORD3");
        user.setPhoto("testUrl3");
        user.setStatus(User.UserStatus.CONFIRMED);

        repository.saveUser(user);

        em.flush();
        String queryUserName = jdbcTemplate.queryForObject("SELECT u.first_name FROM user u WHERE u.user_id = 1"
                , String.class);

        assertEquals("testName3", queryUserName);
    }


    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setFirstName("testName3");
        user.setLastName("testSurname3");
        user.setEmail("test3@gmail.com");
        user.setPassword("tribel1234PASSWORD3");
        user.setPhoto("testUrl3");
        user.setStatus(User.UserStatus.CONFIRMED);

        repository.saveUser(user);
        user.setId(2L);
        user.setEmail("newemail@emai.com");
        repository.updateUser(user);
        em.flush();
        String email = jdbcTemplate.queryForObject("SELECT u.email FROM user u WHERE u.user_id = 2", String.class);
        assertEquals("newemail@emai.com", email);

    }

    @Test
    public void testFindAll() {
        List<User> users = repository.findAll();
        assertEquals(2, users.size());
    }

    @Test
    public void testFindByEmail() {
        List<User> users = repository.findBySpecification(new WhereSpecification<>(new UserByEmail("test2@gmail.com")));
        assertEquals(1, users.size());
        assertEquals("test2@gmail.com", users.get(0).getEmail());
    }

    @Test
    public void testByFirstName() {
        List<User> users = repository.findBySpecification(new WhereSpecification<>(new UserByFirstName("testName")));
        assertEquals(1, users.size());
        assertEquals(new Long(30), users.get(0).getId());
    }

    @Test
    public void findById() {
        List<User> users = repository.findBySpecification(new WhereSpecification<>(new UserById(30L)));
        assertEquals(1, users.size());
        assertEquals(new Long(30), users.get(0).getId());
    }

    @Test
    public void findByLastName() {
        List<User> users = repository.findBySpecification(new WhereSpecification<>(new UserByLastName("testSurname2")));
        assertEquals(1, users.size());
        assertEquals(new Long(31), users.get(0).getId());
    }

    private void saveUser(User usr) {
        jdbcTemplate.update("INSERT INTO user (user_id, first_name, last_name, email, "
                        + " password, photo, user_info_id, status) "
                        + " values(?,?,?,?,?,?,?,?)",
                usr.getId(), usr.getFirstName(), usr.getLastName(), usr.getEmail(),
                usr.getPassword(), usr.getPhoto(), null, usr.getStatus().toString());
    }
}
