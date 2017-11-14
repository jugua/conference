package service.businesslogic.api;

import java.util.List;

import domain.model.Type;
import service.businesslogic.dto.CreateTypeDto;
import service.businesslogic.dto.TypeDto;

/**
 * @author Mariia Lapovska
 */
public interface TypeService {

    Type find(Long id);

    Long save(CreateTypeDto type);

    List<TypeDto> findAll();

    Type getByName(String name);
}
