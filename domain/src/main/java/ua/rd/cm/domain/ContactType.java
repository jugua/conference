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
@SequenceGenerator(name = "seqContactTypeGen", allocationSize = 1,
        sequenceName = "contact_type_seq")
public class ContactType {

    @Id
    @Column(name = "contact_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqContactTypeGen")
    private Long id;

    @Column(name = "contact_type_name", nullable = false, unique = true)
    private String name;
}