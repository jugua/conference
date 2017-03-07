package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "topic")
@SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "topic_seq")
@AttributeOverride(name = "id", column = @Column(name = "topic_id"))
public class Topic extends AbstractEntity {

    @Column(name = "topic_name", nullable = false, unique = true)
    private String name;

    public Topic(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
