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
public class Topic {

    @TableGenerator(
            name = "topicGen",
            table = "topic_id_gen",
            pkColumnName = "gen_key",
            valueColumnName = "gen_value",
            pkColumnValue = "topic_id",
            allocationSize = 1
    )

    @Id
    @Column(name = "topic_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "topicGen")
    private Long id;

    @NotNull
    @Column(name = "topic_name", nullable = false, unique = true)
    private String name;
}
