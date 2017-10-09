package ua.rd.cm.services;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.dto.RegistrationDto;
import ua.rd.cm.infrastructure.mail.MailService;
import ua.rd.cm.repository.RoleRepository;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.services.businesslogic.UserService;
import ua.rd.cm.services.businesslogic.impl.UserServiceImpl;
import ua.rd.cm.services.businesslogic.impl.VerificationTokenService;
import ua.rd.cm.services.exception.EmailAlreadyExistsException;
import ua.rd.cm.services.exception.NoSuchUserException;
import ua.rd.cm.services.exception.PasswordMismatchException;
import ua.rd.cm.services.exception.WrongRoleException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private MailService mailService;
    @Mock
    private VerificationTokenService tokenService;
    @Mock
    private ModelMapper mapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService testing;

    @Before
    public void initialize() {
        testing = new UserServiceImpl(userRepository, roleRepository, mailService,
                mapper, tokenService, passwordEncoder);
    }

    @Test
    public void testGetByRoleExceptCurrent() throws Exception {
        User user1 = createDefaultUser();
        user1.setFirstName("Test1");

        User user2 = createDefaultUser();
        user2.setFirstName("Test2");

        User user3 = createDefaultUser();
        user3.setFirstName("Test3");

        List<User> users = Arrays.asList(user1, user2, user3);
        Role role = new Role(Role.ORGANISER);

        when(roleRepository.findByName(Role.ORGANISER)).thenReturn(role);
        when(userRepository.findAllByRolesIsIn(role)).thenReturn(users);

        List<User> resultUsersList = testing.getByRoleExceptCurrent(user1, Role.ORGANISER);
        assertTrue(resultUsersList.contains(user2));
        assertTrue(resultUsersList.contains(user3));
        assertFalse(resultUsersList.contains(user1));

    }

    @Test
    public void testGetByRoleExceptCurrentWithRoleList() throws Exception {
        User user1 = createDefaultUser();
        user1.setFirstName("Test1");

        User user2 = createDefaultUser();
        user2.setFirstName("Test2");

        User user3 = createDefaultUser();
        user3.setFirstName("Test3");

        List<User> users = Arrays.asList(user1, user2, user3);

        Role roleOrganiser = new Role(Role.ORGANISER);
        Role roleSpeaker = new Role(Role.SPEAKER);

        when(roleRepository.findByName(Role.ORGANISER)).thenReturn(roleOrganiser);
        when(roleRepository.findByName(Role.SPEAKER)).thenReturn(roleSpeaker);

        when(userRepository.findAllByRolesIsIn(Arrays.asList(roleOrganiser, roleSpeaker)))
                .thenReturn(users);

        List<User> resultUsersList = testing.getByRolesExceptCurrent(user1, Role.ORGANISER, Role.SPEAKER);
        assertTrue(resultUsersList.contains(user2));
        assertTrue(resultUsersList.contains(user3));
        assertFalse(resultUsersList.contains(user1));
    }

    @Test
    public void testSave() {
        User user = mock(User.class);
        when(roleRepository.findByName("SPEAKER")).thenReturn(new Role("SPEAKER"));
        testing.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUserProfile() {
        User user = mock(User.class);
        testing.updateUserProfile(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFind() {
        User expected = createDefaultUser();

        when(userRepository.findOne(anyLong())).thenReturn(expected);

        User user = testing.find(30L);
        assertThat(user.getId(), is(30L));
        assertEquals("test", user.getFirstName());
    }

    @Test
    public void testFindAll() {
        List<User> list = spy(new ArrayList<>());
        when(list.size()).thenReturn(5);
        when(userRepository.findAll()).thenReturn(list);

        assertEquals(5, testing.findAll().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetByFirstName() {
        List<User> users = new ArrayList<>();
        User user = createDefaultUser();
        users.add(user);
        when(userRepository.findAllByFirstName((anyString()))).thenReturn(users);

        List<User> usersResult = testing.getByFirstName("test");
        assertEquals("test", usersResult.get(0).getFirstName());
        verify(userRepository, times(1)).findAllByFirstName(anyString());
    }

    @Test
    public void testGetByEmail() {
        User user = createDefaultUser();
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        user = testing.getByEmail("email");
        assertEquals("email", user.getEmail());
        assertThat(user.getId(), is(30L));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void testGetByLastName() {
        List<User> list = new ArrayList<>();
        list.add(createDefaultUser());
        list.add(createDefaultUser());
        when(userRepository.findAllByLastName(anyString())).thenReturn(list);

        List<User> serviceList = testing.getByLastName("testLas");
        assertEquals(2, serviceList.size());
    }

    @Test
    public void testIsEmailExit() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertFalse(testing.isEmailExist("email"));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test(expected = PasswordMismatchException.class)
    public void testCheckUserRegistrationWithDifferentPasswords() {
        RegistrationDto testDto = setupCorrectRegistrationDto();
        testDto.setPassword("12345");
        testDto.setConfirm("123456");
        testing.checkUserRegistration(testDto);
    }

    @Test(expected = EmailAlreadyExistsException.class)
    public void testCheckUserRegistrationWithExistingEmail() {
        RegistrationDto testDto = setupCorrectRegistrationDto();
        User testUser = createDefaultUser();
        testUser.setEmail(testDto.getEmail());

        when(userRepository.findByEmail(testDto.getEmail())).thenReturn(testUser);
        testing.checkUserRegistration(testDto);
    }

    @Test(expected = WrongRoleException.class)
    public void testCheckUserRegistrationByAdmin() {
        RegistrationDto testDto = setupCorrectRegistrationDto();
        testDto.setRoleName(Role.ADMIN);

        testing.checkUserRegistrationByAdmin(testDto);
    }

    @Test(expected = NoSuchUserException.class)
    public void testGetUserDtoByEmail() {
        String testEmail = "123@mail";
        when(userRepository.findByEmail(testEmail)).thenReturn(null);

        testing.getUserDtoByEmail(testEmail);
    }


    private User createDefaultUser() {
        User result = new User();
        result.setId(30L);
        result.setFirstName("test");
        result.setLastName("testLast");
        result.setEmail("email");
        result.setPassword("pass");
        result.setPhoto("url");
        result.setStatus(User.UserStatus.CONFIRMED);
        result.setUserInfo(new UserInfo());
        return result;
    }

    private RegistrationDto setupCorrectRegistrationDto() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("123456");
        registrationDto.setLastName("Ivanova");
        registrationDto.setFirstName("Olya");
        registrationDto.setConfirm("123456");
        registrationDto.setEmail("ivanova@gmail.com");
        return registrationDto;
    }
}