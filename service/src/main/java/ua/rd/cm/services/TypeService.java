package ua.rd.cm.services;

import ua.rd.cm.domain.Type;

import java.util.List;

/**
 * @author Mariia Lapovska
 */
public interface TypeService {

    Type find(Long id);

    void save(Type type);

    List<Type> findAll();

    Type getByName(String name);
}
