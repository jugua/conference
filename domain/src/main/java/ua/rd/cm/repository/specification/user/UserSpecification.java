package ua.rd.cm.repository.specification.user;

import org.springframework.data.jpa.domain.Specification;
import ua.rd.cm.domain.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification {

//    public static Specification<User> isHasRole() {
//        return new Specification<User>() {
//
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                return criteriaQuery.where();
//            }
//        };
//    }


}
