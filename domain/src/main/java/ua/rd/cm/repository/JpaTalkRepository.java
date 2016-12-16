package ua.rd.cm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.specification.Specification;

@Repository
public class JpaTalkRepository implements TalkRepository{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void saveTalk(Talk talk) {
		em.persist(talk);
	}

	@Override
	public void updateTalk(Talk talk) {
		em.merge(talk);
	}

	@Override
	public void removeTalk(Talk talk) {
		em.remove(talk);
	}

	@Override
	public List<Talk> findAll() {
		return em.createQuery("SELECT t FROM Talk t ", Talk.class).getResultList();
	}

	@Override
	public List<Talk> findBySpecification(Specification<Talk> spec) {
		return em.createQuery("SELECT t FROM Talk t WHERE " + spec.toSqlClauses(), Talk.class).getResultList();
	}
}
