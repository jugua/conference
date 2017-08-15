//package ua.rd.cm.repository.jpa;
//
//import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
//import com.github.springtestdbunit.annotation.DatabaseSetup;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.transaction.annotation.Transactional;
//import ua.rd.cm.config.InMemoryRepositoryConfig;
//import ua.rd.cm.domain.Role;
//import ua.rd.cm.domain.User;
//import ua.rd.cm.repository.UserRepository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//import static org.junit.Assert.*;
//
//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {InMemoryRepositoryConfig.class})
//@TestExecutionListeners(listeners = {
//        DependencyInjectionTestExecutionListener.class,
//        TransactionDbUnitTestExecutionListener.class
//})
//public class UserRepositoryTest{
//
//    @Autowired
//    private UserRepository repository;
//
//    @PersistenceContext
//    protected EntityManager entityManager;
//
//    private User user1;
//    private User user2;
//    private Role role;
//
//    @Before
//    public void setUp() throws Exception {
//        user1 = new User();
//        user1.setFirstName("John");
//        user1.setLastName("Wick");
//        user1.setStatus(User.UserStatus.CONFIRMED);
//        user1.setEmail("John_Wick@");
//        user1.setPassword("ybivashka");
//
//        user2 = new User();
//        user2.setFirstName("John2");
//        user2.setLastName("Wick2");
//        user2.setStatus(User.UserStatus.CONFIRMED);
//        user2.setEmail("John_Wick2@");
//        user2.setPassword("ybivashka");
//
//        role = new Role();
//        role.setName("user");
//    }
//
////    @Test
////    @DatabaseSetup("/ds/conf-mgmt.xml")
////    public void testFindByUserRoles() throws Exception {
////        entityManager.persist(role);
////        List<User> list = repository.findAllByUserRoles(role);
////        assertTrue(list.contains(user2));
////        assertFalse(list.contains(user1));
////    }
//
//    protected User createEntity() {
//        User user = new User();
//        user.setFirstName("John");
//        user.setLastName("Wick");
//        user.setStatus(User.UserStatus.CONFIRMED);
//        user.setEmail("John_Wick@");
//        user.setPassword("ybivashka");
//        return user;
//    }
//}
