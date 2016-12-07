package ua.rd.cm.repository.specification.topic;

import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Mariia Lapovska
 */
public class TopicByName implements Specification<Topic> {

    private String name;

    public TopicByName(String name) {
        this.name = name;
    }

    @Override
    public boolean test(Topic topic) {
        return topic.getName().equals(name);
    }

    @Override
    public String toSqlClauses() {
        return String.format(" t.name = '%s'", name);
    }
}
