package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Topic;

import java.util.List;

@Repository
@PreAuthorize("isAuthenticated()")
@RepositoryRestResource(path = "topic")
public interface TopicRepository extends CrudRepository<Topic, Long> {

    List<Topic> findAll();
    Topic findTopicByName(@Param("name")String name);

}