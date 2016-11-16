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
@Table(name = "language")
public class Language {
    @TableGenerator(
            name = "languageGen",
            table = "language_id_gen",
            pkColumnName = "gen_key",
            valueColumnName = "gen_value",
            pkColumnValue = "language_id",
            allocationSize = 1
    )

    @Id
    @Column(name = "language_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "languageGen")
    private Long id;

    @NotNull
    @Column(name = "language_name", nullable = false)
    private String name;
}
