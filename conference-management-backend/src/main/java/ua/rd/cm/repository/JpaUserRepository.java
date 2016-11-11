package ua.rd.cm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

@Repository
public class JpaUserRepository implements UserRepository{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void saveUser(User user) {
		em.persist(user);
	}

	@Override
	public void removeUser(User user) {
		em.remove(user);
	}

	@Override
	public void updateUser(User user) {
		em.merge(user);
	}

	@Override
	public List<User> findBySpecification(Specification<User> spec) {
		return em.createQuery("SELECT u FROM User u WHERE " + spec.toSqlClauses(), User.class).getResultList();
	
	}

}
