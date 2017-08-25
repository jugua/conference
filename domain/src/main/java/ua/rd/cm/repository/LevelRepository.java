package ua.rd.cm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Level;

@Repository
public interface LevelRepository extends CrudRepository<Level, Long> {
    @Override
    List<Level> findAll();

    Level findByName(String name);
}
