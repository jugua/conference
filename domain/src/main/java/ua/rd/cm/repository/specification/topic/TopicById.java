package ua.rd.cm.repository.specification.topic;

import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Mariia Lapovska
 */
public class TopicById implements Specification<Topic> {

    private Long id;

    public TopicById(Long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" t.id = '%s' ", id);
    }

    @Override
    public boolean test(Topic topic) {
        return topic.getId().equals(id);
    }
}
