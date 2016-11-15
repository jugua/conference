package ua.rd.cm.repository;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.user.UserByEmail;
import ua.rd.cm.repository.specification.user.UserByFirstName;

public class JpaUserRepositoryIT extends RepositoryTestConfig{

	@Autowired
	private UserRepository repository;
	
	@PersistenceContext
	EntityManager em; //added for explicit flushing form Hibernate cache.
	
	
	@Before
	public void setUp() {
		User user = new User(30L, "testName", "testSurname", "test@gmail.com", "tribel1234PASSWORD", 
				"testUrl", null, null);
		saveUser(user);
		
		user = new User(31L, "testName2", "testSurname2", "test2@gmail.com", "tribel1234PASSWORD2", 
				"testUrl2", null, null);
		
		saveUser(user);
	}
	
	@After
	public void clear() {
		jdbcTemplate.update("DELETE FROM user");
	}
	
	
	@Test
	public void testSaveUser() {
		User user = new User("testName3", "testSurname3", "test3@gmail.com", "tribel1234PASSWORD3", 
				"testUrl3", null, null);
		
		repository.saveUser(user);
		
		em.flush();
 		String queryUserName = jdbcTemplate.queryForObject("SELECT u.first_name FROM user u WHERE u.user_id = 1"
				, String.class);
		
		assertEquals("testName3", queryUserName);
	}

	@Test
	@Ignore
	public void testRemoveUser() {
		User user = new User(30L, "testName", "testSurname", "test@gmail.com", "tribel1234PASSWORD", 
				"testUrl", null, null);
		repository.removeUser(user);
	}

	@Test
	@Ignore
	public void testUpdateUser() {
		User user = new User("testName3", "testSurname3", "test3@gmail.com", "tribel1234PASSWORD3", 
				"testUrl3", null, null);
		
		repository.saveUser(user);
		user.setEmail("newemail@emai.com");
		repository.updateUser(user);
	}

	@Test
	public void testFindAll() {
		List<User> users = repository.findAll();
		assertEquals(2, users.size());

	}

	@Test
	public void testFindByEmail() {
		List<User> users = repository.findBySpecification(new UserByEmail("test2@gmail.com"));
		assertEquals(1, users.size());
		assertEquals("test2@gmail.com", users.get(0).getEmail());
	}
	
	@Test
	public void testByFirstName() {
		List<User> users = repository.findBySpecification(new UserByFirstName("testName"));
		assertEquals(1, users.size());
		assertEquals(new Integer(30), users.get(0).getId());
	}
	
	@Test
	public void findById() {
		
	}
	
	@Test
	public void findByLastName() {
		
	}
	
	protected void saveUser(User usr) {
		jdbcTemplate.update("INSERT INTO user (user_id, first_name, last_name, email, "
				+ " password, photo, user_info_id) "
				+ " values(?,?,?,?,?,?,?)",
				usr.getId(), usr.getFirstName(), usr.getLastName(), usr.getEmail(),
				usr.getPassword(), usr.getPhoto(), null);
	}

}
