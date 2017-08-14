package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Type;
import ua.rd.cm.repository.TypeRepository;

@Repository
public class TypeRepositoryImpl
        extends AbstractJpaCrudRepository<Type> {

    public TypeRepositoryImpl() {
        super("t", Type.class);
    }
}