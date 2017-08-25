package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Level;

import java.util.List;

@Repository
public interface LevelRepository extends CrudRepository<Level, Long> {
    @Override
    List<Level> findAll();

    Level findByName(String name);
}
