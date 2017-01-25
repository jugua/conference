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

import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.repository.specification.WhereSpecification;
import ua.rd.cm.repository.specification.user.UserByEmail;
import ua.rd.cm.repository.specification.user.UserByFirstName;
import ua.rd.cm.repository.specification.user.UserById;
import ua.rd.cm.repository.specification.user.UserByLastName;

@RunWith(MockitoJUnitRunner.class)
public class SimpleUserServiceTest {

	@Mock
	private UserRepository repository;
	@Mock
	private RoleService roleService;
	@Mock
	private MailService mailService;
	@Mock
	private VerificationTokenService tokenService;
	private UserService service;
	
	@Before
	public void initialize() {
		service = new SimpleUserService(repository, roleService, mailService, tokenService);
	}

	@Test
	public void testSave() {
		User user = mock(User.class);
		when(roleService.getByName("SPEAKER")).thenReturn(new Role(1L, "SPEAKER"));
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
		List<User> list = new ArrayList<>();
		list.add(new User(1L, "test", "testLast", "email", "pass", "url", User.UserStatus.CONFIRMED, new UserInfo(), null, null, null));
		when(repository.findBySpecification(new WhereSpecification<>(new UserById(anyLong())))).thenReturn(list);

		User user = service.find(1L);
		assertEquals(new Long(1), user.getId());
		assertEquals("test", user.getFirstName());
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
		List<User> list = new ArrayList<>();
		list.add(new User(1L, "test", "testLast", "email", "pass", "url", User.UserStatus.CONFIRMED, new UserInfo(), null, null, null));
		when(repository.findBySpecification(new WhereSpecification<>(new UserByFirstName(anyString())))).thenReturn(list);
		
		List<User> user = service.getByFirstName("test");
		assertEquals("test", user.get(0).getFirstName());
		verify(repository,times(1)).findBySpecification(new UserByFirstName(anyString()));
	}

	@Test
	public void testGetByEmail() {
		User user = null;
		List<User> list = new ArrayList<>();
		list.add(new User(1L, "test", "testLast", "email", "pass", "url", User.UserStatus.CONFIRMED, new UserInfo(), null, null, null));
		when(repository.findBySpecification(new WhereSpecification<>(new UserByEmail(anyString())))).thenReturn(list);
		
		user = service.getByEmail("email");
		assertEquals("email", user.getEmail());
		assertEquals(new Long(1), user.getId());
		verify(repository, times(1)).findBySpecification(new UserByEmail(anyString()));
	}

	@Test
	public void testGetByLastName() {
		List<User> list = new ArrayList<>();
		list.add(new User(1L, "test", "testLast", "email", "pass", "url", User.UserStatus.CONFIRMED, new UserInfo(),null, null, null));
		list.add(new User(1L, "test2", "testLas2t", "email2", "pass2", "url2", User.UserStatus.CONFIRMED, new UserInfo(), null, null, null));
		when(repository.findBySpecification(new WhereSpecification<>(new UserByLastName(anyString())))).thenReturn(list);
		
		List<User> serviceList = service.getByLastName("testLas");
		assertEquals(2, serviceList.size());
	}

	@Test
	public void testIsEmailExit() {
		List<User> list = spy(new ArrayList<>());
		when(list.isEmpty()).thenReturn(false).thenReturn(true);
		when(repository.findBySpecification(new WhereSpecification<>(new UserByEmail(anyString())))).thenReturn(list);
		
		assertTrue(service.isEmailExist("email"));
		assertFalse(service.isEmailExist("email"));
		verify(repository,times(2)).findBySpecification(new UserByEmail(anyString()));
	}
}