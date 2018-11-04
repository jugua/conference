package service.businesslogic.api;

import java.util.List;

import domain.model.Type;
import service.businesslogic.dto.CreateTypeDto;
import service.businesslogic.dto.TypeDto;

/**
 * @author Mariia Lapovska
 */
public interface TypeService {

    List<TypeDto> getAll();

    Type getById(Long id);

    Type getByName(String name);

    Long save(CreateTypeDto type);
}
