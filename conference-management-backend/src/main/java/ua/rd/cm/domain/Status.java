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
@Table(name = "status")
public class Status {
    @TableGenerator(
            name = "statusGen",
            table = "status_id_gen",
            pkColumnName = "gen_key",
            valueColumnName = "gen_value",
            pkColumnValue = "status_id",
            allocationSize = 1
    )

    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "statusGen")
    private Long id;

    @NotNull
    @Column(name = "status_name", nullable = false, unique = true)
    private String name;
}
