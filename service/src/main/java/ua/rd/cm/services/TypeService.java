package ua.rd.cm.services;

import ua.rd.cm.domain.Type;
import ua.rd.cm.dto.TypeDto;

import java.util.List;

/**
 * @author Mariia Lapovska
 */
public interface TypeService {

    Type find(Long id);

    void save(Type type);

    List<TypeDto> findAll();

    Type getByName(String name);
}
