package ua.rd.cm.services;

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
import ua.rd.cm.repository.RoleRepository;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.services.exception.EmailAlreadyExistsException;
import ua.rd.cm.services.exception.EmptyPasswordException;
import ua.rd.cm.services.exception.NoSuchUserException;
import ua.rd.cm.services.exception.WrongRoleException;
import ua.rd.cm.services.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

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

    @Mock
    private ContactTypeService contactTypeService;

    private UserService userService;

    @Before
    public void initialize() {
        userService = new UserServiceImpl(userRepository, roleRepository, mailService, mapper, tokenService, passwordEncoder, contactTypeService);
    }

    @Test
    public void testGetByRoleExceptCurrent() throws Exception {
        User user1 = createDefaultUser();
        user1.setFirstName("Test1");

        User user2 = createDefaultUser();
        user2.setFirstName("Test2");

        User user3 = createDefaultUser();
        user3.setFirstName("Test3");

        List<User> users = new ArrayList<User>() {{
            add(user1);
            add(user2);
            add(user3);
        }};
        Role role = new Role(Role.ORGANISER);

        when(roleRepository.findByName(Role.ORGANISER)).thenReturn(role);
        when(userRepository.findAllByUserRolesIsIn(role)).thenReturn(users);

        List<User> resultUsersList = userService.getByRoleExceptCurrent(user1, Role.ORGANISER);
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

        List<User> users = new ArrayList<User>() {{
            add(user1);
            add(user2);
            add(user3);
        }};

        Role roleOrganiser = new Role(Role.ORGANISER);
        Role roleSpeaker = new Role(Role.SPEAKER);

        when(roleRepository.findByName(Role.ORGANISER)).thenReturn(roleOrganiser);
        when(roleRepository.findByName(Role.SPEAKER)).thenReturn(roleSpeaker);

        when(userRepository.findAllByUserRolesIsIn(new ArrayList<Role>() {{
            add(roleOrganiser);
            add(roleSpeaker);
        }})).thenReturn(users);

        List<User> resultUsersList = userService.getByRolesExceptCurrent(user1, Role.ORGANISER, Role.SPEAKER);
        assertTrue(resultUsersList.contains(user2));
        assertTrue(resultUsersList.contains(user3));
        assertFalse(resultUsersList.contains(user1));

    }

    @Test
    public void testSave() {
        User user = mock(User.class);
        when(roleRepository.findByName("SPEAKER")).thenReturn(new Role(1L, "SPEAKER"));
        userService.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUserProfile() {
        User user = mock(User.class);
        userService.updateUserProfile(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFind() {
        User expected = createDefaultUser();

        when(userRepository.findOne(anyLong())).thenReturn(expected);

        User user = userService.find(30L);
        assertEquals(new Long(30), user.getId());
        assertEquals("test", user.getFirstName());
    }

    @Test
    public void testFindAll() {
        List<User> list = spy(new ArrayList<>());
        when(list.size()).thenReturn(5);
        when(userRepository.findAll()).thenReturn(list);

        assertEquals(5, userService.findAll().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetByFirstName() {
        List<User> users = new ArrayList<>();
        User user = createDefaultUser();
        users.add(user);
        when(userRepository.findAllByFirstName((anyString()))).thenReturn(users);

        List<User> usersResult = userService.getByFirstName("test");
        assertEquals("test", usersResult.get(0).getFirstName());
        verify(userRepository, times(1)).findAllByFirstName(anyString());
    }

    @Test
    public void testGetByEmail() {
        User user = createDefaultUser();
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        user = userService.getByEmail("email");
        assertEquals("email", user.getEmail());
        assertEquals(new Long(30), user.getId());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void testGetByLastName() {
        List<User> list = new ArrayList<>();
        list.add(createDefaultUser());
        list.add(createDefaultUser());
        when(userRepository.findAllByLastName(anyString())).thenReturn(list);

        List<User> serviceList = userService.getByLastName("testLas");
        assertEquals(2, serviceList.size());
    }

    @Test
    public void testIsEmailExit() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertFalse(userService.isEmailExist("email"));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test(expected = EmptyPasswordException.class)
    public void testCheckUserRegistrationWithDifferentPasswords() {
        RegistrationDto testDto = setupCorrectRegistrationDto();
        testDto.setPassword("12345");
        testDto.setConfirm("123456");
        userService.checkUserRegistration(testDto);
    }

    @Test(expected = EmailAlreadyExistsException.class)
    public void testCheckUserRegistrationWithExistingEmail() {
        RegistrationDto testDto = setupCorrectRegistrationDto();
        User testUser = createDefaultUser();
        testUser.setEmail(testDto.getEmail());

        when(userRepository.findByEmail(testDto.getEmail())).thenReturn(testUser);
        userService.checkUserRegistration(testDto);
    }

    @Test(expected = WrongRoleException.class)
    public void testCheckUserRegistrationByAdmin() {
        RegistrationDto testDto = setupCorrectRegistrationDto();
        testDto.setRoleName(Role.ADMIN);

        userService.checkUserRegistrationByAdmin(testDto);
    }

    @Test(expected = NoSuchUserException.class)
    public void testGetUserDtoByEmail() {
        String testEmail = "123@mail";
        when(userRepository.findByEmail(testEmail)).thenReturn(null);

        userService.getUserDtoByEmail(testEmail);
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

    private RegistrationDto setupCorrectRegistrationDto(){
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("123456");
        registrationDto.setLastName("Ivanova");
        registrationDto.setFirstName("Olya");
        registrationDto.setConfirm("123456");
        registrationDto.setEmail("ivanova@gmail.com");
        return  registrationDto;
    }
}