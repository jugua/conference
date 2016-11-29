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
@Table(name = "level")
@SequenceGenerator(name = "seqLevelGen", allocationSize = 1,
        sequenceName = "level_seq")
public class Level {

    @Id
    @Column(name = "level_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLevelGen")
    private Long id;

    @Column(name = "level_name", nullable = false, unique = true)
    private String name;
}
