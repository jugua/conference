package ua.rd.cm.services;

import ua.rd.cm.domain.Level;
import ua.rd.cm.dto.LevelDto;

import java.util.List;

/**
 * @author Olha_Melnyk
 */
public interface LevelService {

    Level find(Long id);

    void save(Level level);

    Level getByName(String name);

    List<LevelDto> findAll();

}
