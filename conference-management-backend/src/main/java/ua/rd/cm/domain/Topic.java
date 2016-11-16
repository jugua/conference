package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Artem_Pryzhkov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "topic")
@SequenceGenerator(name = "seqTopicGen", allocationSize = 1)
public class Topic {

    @Id
    @Column(name = "topic_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTopicGen")
    private Long id;

    @NotNull
    @Column(name = "topic_name", nullable = false, unique = true)
    private String name;
}
