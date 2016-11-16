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
@Table(name = "type")
public class Type {
    @TableGenerator(
            name = "typeGen",
            table = "type_id_gen",
            pkColumnName = "gen_key",
            valueColumnName = "gen_value",
            pkColumnValue = "type_id",
            allocationSize = 1
    )

    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "typeGen")
    private Long id;

    @NotNull
    @Column(name = "type_name", nullable = false, unique = true)
    private String name;
}
