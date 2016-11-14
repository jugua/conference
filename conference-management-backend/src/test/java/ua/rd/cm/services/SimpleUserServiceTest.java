package ua.rd.cm.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.repository.specification.user.IsEmailExist;
import ua.rd.cm.repository.specification.user.UserByEmail;
import ua.rd.cm.repository.specification.user.UserByFirstName;
import ua.rd.cm.repository.specification.user.UserById;
import ua.rd.cm.repository.specification.user.UserByLastName;

@RunWith(MockitoJUnitRunner.class)
public class SimpleUserServiceTest {

	@Mock
	private UserRepository repository;
	private UserService service;
	
	@Before
	public void initialize() {
		service = new SimpleUserService(repository);
	}

	@Test
	public void testSave() {
		User user = mock(User.class);
		service.save(user);
		verify(repository, times(1)).saveUser(user);
	}
	
	@Test
	public void testUpdateUserProfile() {
		User user = mock(User.class);
		service.updateUserProfile(user);
		verify(repository, times(1)).updateUser(user);
	}
	
	@Test
	public void testFind() {
		List<User> retList = new ArrayList<>();
		retList.add(new User(1L, "test", "testLas", "email", new char[]{'p','a','s'}, "url//test", "admin"));
		when(repository.findBySpecification(new UserById(anyLong()))).thenReturn(retList);

		User retUser = service.find(1L);
		assertEquals(new Long(1), retUser.getId());
		assertEquals("test", retUser.getFirstName());
	}

	@Test
	public void testFindAll() {
		List<User> list = spy(new ArrayList<>());
		when(list.size()).thenReturn(5);
		when(repository.findAll()).thenReturn(list);
		
		assertEquals(5, service.findAll().size());
		verify(repository, times(1)).findAll();
	}

	@Test
	public void testGetByFirstName() {
		List<User> retList = new ArrayList<>();
		retList.add(new User(1L, "test", "testLas", "email", new char[]{'p','a','s'}, "url//test", "admin"));
		when(repository.findBySpecification(new UserByFirstName(anyString()))).thenReturn(retList);
		
		List<User> user = service.getByFirstName("test");
		assertEquals("test", user.get(0).getFirstName());
		verify(repository,times(1)).findBySpecification(new UserByFirstName(anyString()));
	}

	@Test
	public void testGetByEmail() {
		User retUser = null;
		List<User> retList = new ArrayList<>();
		retList.add(new User(1L, "test", "testLas", "email", new char[]{'p','a','s'}, "url//test", "admin"));
		when(repository.findBySpecification(new UserByEmail(anyString()))).thenReturn(retList);
		
		retUser = service.getByEmail("email");
		assertEquals("email", retUser.getEmail());
		assertEquals(new Long(1), retUser.getId());
		verify(repository, times(1)).findBySpecification(new UserByEmail(anyString()));
	}

	@Test
	public void testGetByLastName() {
		List<User> retList = new ArrayList<>();
		retList.add(new User(1L, "test", "testLas", "email", new char[]{'p','a','s'}, "url//test", "admin"));
		retList.add(new User(2L, "test2", "testLas2", "email2", new char[]{'p','a','2'}, "url//test2", "admin"));
		when(repository.findBySpecification(new UserByLastName(anyString()))).thenReturn(retList);
		
		List<User> serviceList = service.getByLastName("testLas");
		assertEquals(2, serviceList.size());
	}

	@Test
	public void testIsEmailExit() {
		List<User> list = spy(new ArrayList<>());
		when(list.isEmpty()).thenReturn(false).thenReturn(true);
		when(repository.findBySpecification(new IsEmailExist(anyString()))).thenReturn(list);
		
		assertTrue(service.isEmailExist("email"));
		assertFalse(service.isEmailExist("email"));
		verify(repository,times(2)).findBySpecification(new IsEmailExist(anyString()));
	}

}
