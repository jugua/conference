package service.businesslogic.impl;

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

import domain.model.Role;
import domain.model.User;
import domain.model.UserInfo;
import domain.repository.RoleRepository;
import domain.repository.UserInfoRepository;
import domain.repository.UserRepository;
import service.businesslogic.dto.RegistrationDto;
import service.businesslogic.exception.EmailAlreadyExistsException;
import service.businesslogic.exception.NoSuchUserException;
import service.businesslogic.exception.PasswordMismatchException;
import service.businesslogic.exception.WrongRoleException;
import service.infrastructure.mail.MailService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserInfoRepository userInfoRepository;
    @Mock
    private MailService mailService;
    @Mock
    private VerificationTokenService tokenService;
    @Mock
    private ModelMapper mapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl testing;

    @Before
    public void initialize() {
        testing = new UserServiceImpl(userRepository, roleRepository, userInfoRepository, mailService,
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
        Role role = new Role(Role.ROLE_ORGANISER);

        when(roleRepository.findByName(Role.ROLE_ORGANISER)).thenReturn(role);
        when(userRepository.findAllByRolesIsIn(role)).thenReturn(users);

        List<User> resultUsersList = testing.findOtherUsersWithSameRole(user1, Role.ROLE_ORGANISER);
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

        Role roleOrganiser = new Role(Role.ROLE_ORGANISER);
        Role roleSpeaker = new Role(Role.ROLE_SPEAKER);

        when(roleRepository.findByName(Role.ROLE_ORGANISER)).thenReturn(roleOrganiser);
        when(roleRepository.findByName(Role.ROLE_SPEAKER)).thenReturn(roleSpeaker);

        when(userRepository.findAllByRolesIsIn(Arrays.asList(roleOrganiser, roleSpeaker)))
                .thenReturn(users);

        List<User> resultUsersList = testing.findOtherUsersByRoles(user1, Role.ROLE_ORGANISER, Role.ROLE_SPEAKER);
        assertTrue(resultUsersList.contains(user2));
        assertTrue(resultUsersList.contains(user3));
        assertFalse(resultUsersList.contains(user1));
    }

    @Test
    public void testSave() {
        User user = mock(User.class);
        when(roleRepository.findByName("ROLE_SPEAKER")).thenReturn(new Role("ROLE_SPEAKER"));
        testing.createSpeaker(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUserProfile() {
        User user = mock(User.class);
        testing.updateUser(user);
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
        testDto.setRoleName(Role.ROLE_ADMIN);

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