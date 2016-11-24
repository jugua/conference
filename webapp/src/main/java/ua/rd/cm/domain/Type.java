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
@SequenceGenerator(name = "seqTypeGen", allocationSize = 1)
public class Type {

    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTypeGen")
    private Long id;

    @NotNull
    @Column(name = "type_name", nullable = false, unique = true)
    private String name;
}
