package ua.rd.cm.services;

import ua.rd.cm.domain.Topic;

import java.util.List;

/**
 * @author Mariia Lapovska
 */
public interface TopicService {

    Topic find(Long id);

    void save(Topic topic);

    List<Topic> findAll();

    Topic getByName(String name);
}
