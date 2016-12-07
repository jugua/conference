package ua.rd.cm.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

@Repository
public class JpaUserRepository implements UserRepository{

	@PersistenceContext
	private EntityManager em;

	private Logger logger = Logger.getLogger(JpaUserRepository.class);

	@Override
	public void saveUser(User user) {
		em.persist(user);
		logger.info(user.toString() + " is saved");
	}

	@Override
	public void removeUser(User user) {
		em.remove(user);
		logger.info(user.toString() + " is removed");
	}

	@Override
	public void updateUser(User user) {
		em.merge(user);
		logger.info(user.toString() + " is updated");
	}

	@Override
	public List<User> findAll() {
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}
	
	@Override
	public List<User> findBySpecification(Specification<User> spec) {
		return em.createQuery("SELECT u FROM User u WHERE " + spec.toSqlClauses(), User.class).getResultList();
	}


}
