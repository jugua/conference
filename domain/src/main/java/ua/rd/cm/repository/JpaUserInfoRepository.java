package ua.rd.cm.repository;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.specification.Specification;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
@Repository
public class JpaUserInfoRepository implements UserInfoRepository {

    @PersistenceContext
    private EntityManager em;

    private Logger logger = Logger.getLogger(JpaUserInfoRepository.class);

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        em.persist(userInfo);
        logger.info(userInfo.toString() + " is saved");
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        em.merge(userInfo);
        logger.info(userInfo.toString() + " is updated");
    }

    @Override
    public void removeUserInfo(UserInfo userInfo) {
        em.remove(userInfo);
        logger.info(userInfo.toString() + " is removed");
    }

    @Override
    public List<UserInfo> findAll() {
        return em.createQuery("SELECT u FROM UserInfo u", UserInfo.class).getResultList();
    }

    @Override
    public List<UserInfo> findBySpecification(Specification<UserInfo> spec) {
        return em.createQuery("SELECT u FROM UserInfo u WHERE " + spec.toSqlClauses(), UserInfo.class).getResultList();
    }
}
