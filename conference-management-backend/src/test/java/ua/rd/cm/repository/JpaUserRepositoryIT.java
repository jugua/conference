package ua.rd.cm.repository;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.rd.cm.domain.User;

public class JpaUserRepositoryIT extends RepositoryTestConfig{

	@Autowired
	private UserRepository repository;
	
	@Before
	public void setUp() {
		
	}
	
	@After
	public void clear() {
		
	}
	
	
	@Test
	public void testSaveUser() {
		
	}

	@Test
	public void testRemoveUser() {
	}

	@Test
	public void testUpdateUser() {
	}

	@Test
	public void testFindAll() {
	}

	@Test
	public void testFindBySpecification() {
	}
	
	protected void saveUser(User usr) {
		jdbcTemplate.update("INSERT INTO user (user_id, first_name, last_name, email, "
				+ " password, photo, user_info_id, role_id) "
				+ " values(?,?,?,?,?,?,?,?)",
				usr.getId(), usr);
	}

}
