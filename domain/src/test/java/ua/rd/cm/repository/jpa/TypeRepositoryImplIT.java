package ua.rd.cm.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.Type;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.TypeRepository;

public class TypeRepositoryImplIT extends AbstractJpaCrudRepositoryIT<Type> {
    @Autowired
    private TypeRepository repository;

    @Override
    protected CrudRepository<Type> createRepository() {
        return repository;
    }

    @Override
    protected Type createEntity() {
        Type type = new Type();
        type.setName("some type");
        return type;
    }
}