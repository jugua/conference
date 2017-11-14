package service.businesslogic.api;

import java.util.List;

import service.businesslogic.dto.LevelDto;

/**
 * @author Olha_Melnyk
 */
public interface LevelService {

    List<LevelDto> findAll();

}
