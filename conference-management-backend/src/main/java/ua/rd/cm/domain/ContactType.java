package ua.rd.cm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Mariia_Lapovska
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "contact_type")
public class ContactType {
    @TableGenerator(
            name = "contactTypeGen",
            table = "contact_type_id_gen",
            pkColumnName = "gen_key",
            valueColumnName = "gen_value",
            pkColumnValue = "contact_type_id",
            allocationSize = 1
    )

    @Id
    @Column(name = "contact_type_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "contactTypeGen")
    private Long id;

    @NotNull
    @Column(name = "contact_type_name", nullable = false, unique = true)
    private String name;
}