package ua.rd.cm.repository.jpa;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.config.InMemoryRepositoryConfig;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InMemoryRepositoryConfig.class})
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
})
public abstract class AbstractJpaCrudRepositoryIT<T> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected CrudRepository<T> repository;

    protected abstract CrudRepository<T> createRepository();

    protected abstract T createEntity();

    @Before
    public void setUp() {
        this.repository = createRepository();
    }

    @Test
    public void saveShouldGenerateId() throws Exception {
    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    @DatabaseSetup("/ds/conf-mgmt.xml")
    public void findAll() throws Exception {
        List<T> all = repository.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    @DatabaseSetup("/ds/conf-mgmt.xml")
    public void findBySpecification() throws Exception {
        List<T> all = repository.findBySpecification(new Specification<T>() {
            @Override
            public String toSqlClauses() {
                return ":c is null";
            }

            @Override
            public boolean test(T t) {
                return true;
            }

            @Override
            public Query setParameters(Query query) {
                return query.setParameter("c", null);
            }
        });
        assertFalse(all.isEmpty());
    }

    @Test
    @DatabaseSetup("/ds/conf-mgmt.xml")
    public void findById() throws Exception {
        T byId = repository.findById(-1L);
        assertNotNull(byId);
    }

    @Test
    @DatabaseSetup("/ds/conf-mgmt.xml")
    public void remove() throws Exception {
        T byId = repository.findById(-1L);
        repository.remove(byId);

        assertNull(repository.findById(-1L));
    }
}