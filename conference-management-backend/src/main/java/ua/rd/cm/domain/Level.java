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
public class Level {
    @TableGenerator(
            name = "levelGen",
            table = "level_id_gen",
            pkColumnName = "gen_key",
            valueColumnName = "gen_value",
            pkColumnValue = "level_id",
            allocationSize = 1
    )

    @Id
    @Column(name = "level_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "levelGen")
    private Long id;

    @NotNull
    @Column(name = "level_name", nullable = false, unique = true)
    private String name;
}
